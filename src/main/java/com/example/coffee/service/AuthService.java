package com.example.coffee.service;

import com.example.coffee.common.ResultCode;
import com.example.coffee.common.jwt.JwtTokenProvider;
import com.example.coffee.model.user.CreateMemberDTO;
import com.example.coffee.model.user.LoginRequestDTO;
import com.example.coffee.repository.MemberMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.coffee.common.Result;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class AuthService {
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public Result signup(CreateMemberDTO createMemberDTO) {
        try {
            if (memberMapper.isEmailExists(createMemberDTO.getEmail()) > 0) {
                return new Result(ResultCode.EMAIL_ALREADY_EXISTS);
            }
            // 현재 시간으로 설정
            LocalDateTime now = LocalDateTime.now();
            createMemberDTO.setCreatedAt(now);
            createMemberDTO.setUpdatedAt(now);
            String aPassword = passwordEncoder.encode(createMemberDTO.getPassword());
            createMemberDTO.setPassword(aPassword);
            memberMapper.signup(createMemberDTO);

            return new Result(ResultCode.SUCCESS, createMemberDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Result(ResultCode.FAIL_TO_SAVE_USER);
        }
    }

    public Result login(LoginRequestDTO loginRequestDTO) {
        try {
            CreateMemberDTO member = memberMapper.findEmail(loginRequestDTO.getEmail());
            if (member == null) {
                return new Result(ResultCode.NOT_EXISTS_USER);
            }

            if (!passwordEncoder.matches(loginRequestDTO.getPassword(), member.getPassword())) {
                return new Result(ResultCode.INVALID_USER_PASSWORD);
            }

            // 토큰 생성
            String token = jwtTokenProvider.createToken(member.getMemberId());
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("user", Map.of(
                    "email", member.getEmail(),
                    "name", member.getName() != null ? member.getName() : "N/A",
                    "address", member.getAddress() != null ? member.getAddress() : "N/A",
                    "zipcode", member.getZipcode() != null ? member.getZipcode() : "N/A",
                    "createdAt", member.getCreatedAt() != null ? member.getCreatedAt().toString() : "N/A",
                    "updatedAt", member.getUpdatedAt() != null ? member.getUpdatedAt().toString() : "N/A"
            ));
            responseData.put("token", token);
            return new Result(ResultCode.SUCCESS, responseData);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(ResultCode.DB_ERROR);
        }
    }

    public Result getUserProfile(Long userId) {
        try {
            CreateMemberDTO member = memberMapper.userInfo(userId);
            if (member == null) {
                return new Result(ResultCode.NOT_EXISTS_USER);
            }
            return new Result(ResultCode.SUCCESS, member);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Result(ResultCode.DB_ERROR);
        }
    }
}
