package com.example.springusermybatis.repository;

import com.example.springusermybatis.model.CreateMemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    void signup(CreateMemberDTO createMemberDTO);

    int isEmailExists(String email);

    CreateMemberDTO findEmail(String email);

    CreateMemberDTO userInfo(long memberId);
}
