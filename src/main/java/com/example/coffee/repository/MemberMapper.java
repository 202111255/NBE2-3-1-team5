package com.example.coffee.repository;

import com.example.coffee.model.user.RequestMemberDTO;
import com.example.coffee.model.user.UpdateMemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    void signup(RequestMemberDTO createMemberDTO);

    int isEmailExists(String email);

    RequestMemberDTO findEmail(String email);

    RequestMemberDTO userInfo(Long memberId);

    void userUpdate(UpdateMemberDTO updateMemberDTO);

    void userDelete(Long memberId);

    void modifyRefreshToken(RequestMemberDTO createMemberDTO);

    String findRefreshToken(Long memberId);

}
