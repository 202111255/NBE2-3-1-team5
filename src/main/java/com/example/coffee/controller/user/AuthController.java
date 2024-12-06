package com.example.coffee.controller.user;

import com.example.coffee.model.user.RequestMemberDTO;
import com.example.coffee.model.user.LoginRequestDTO;
import com.example.coffee.model.user.UpdateMemberDTO;
import com.example.coffee.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.example.coffee.common.Result;

@RestController
@Tag(name = "Auth API", description = "인증 관련 API")
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "회원가입 Api입니다. 이메일형태, 패스워드는 " )
    public Result signup(@Valid @RequestBody RequestMemberDTO createMemberDTO) {
        return authService.signup(createMemberDTO);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인 API입니다 잠금표시에 토큰을 넣어주세요")
    public Result login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        return authService.login(loginRequestDTO, response);
    }

    @PutMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃 API입니다 쿠키사라짐.")
    public Result logout(@AuthenticationPrincipal Long userId, HttpServletResponse response) {
        return authService.logout(userId, response);
    }

    @GetMapping("/profile")
    @Operation(summary = "사용자 정보", description = "사용자 정보 Api입니다.")
    public Result userInfo(@AuthenticationPrincipal Long userId) {
        return authService.getUserProfile(userId);
    }

    @PutMapping
    @Operation(summary = "사용자 정보 수정", description = "사용자를 수정해주세요")
    public Result updateUser(@AuthenticationPrincipal Long userId, @RequestBody UpdateMemberDTO updateMemberDTO){
        return authService.updateUser(userId, updateMemberDTO);
    }

    @DeleteMapping
    @Operation(summary = "사용자삭제 ", description = "사용자 삭제 Api입니다.")
    public Result deleteUser(@AuthenticationPrincipal Long userId) {
        return authService.deleteUser(userId);
    }

    @PostMapping("/new-token")
    @Operation(summary = "토큰 재발급", description = "access토큰 재발급 API입니다.")
    public Result reCreateToken(HttpServletRequest request) {
        return authService.reCreateToken(request);
    }
}
