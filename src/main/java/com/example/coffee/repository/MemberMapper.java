package com.example.coffee.repository;

import com.example.coffee.model.user.CreateMemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    void signup(CreateMemberDTO createMemberDTO);

    int isEmailExists(String email);

    CreateMemberDTO findEmail(String email);

    CreateMemberDTO userInfo(long memberId);
}
