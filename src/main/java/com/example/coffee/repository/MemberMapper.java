package com.example.coffee.repository;

import com.example.coffee.model.user.CreateMemberDTO;
import com.example.coffee.model.user.UpdateMemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    void signup(CreateMemberDTO createMemberDTO);

    int isEmailExists(String email);

    CreateMemberDTO findEmail(String email);

    CreateMemberDTO userInfo(Long memberId);

    void userUpdate(UpdateMemberDTO updateMemberDTO);

    void userDelete(Long memberId);
}
