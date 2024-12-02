package com.example.springusermybatis.controller;


import com.example.springusermybatis.model.CreateMemberDTO;
import com.example.springusermybatis.model.LoginRequestDTO;
import com.example.springusermybatis.service.MemberService;
import jakarta.validation.Valid;
import com.example.springusermybatis.common.Result;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/member")
    public Result signup(@Valid @RequestBody CreateMemberDTO createMemberDTO) {
        return memberService.signupMember(createMemberDTO);
    }

    @PostMapping("/member/login")
    public Result login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return memberService.login(loginRequestDTO);
    }

    @GetMapping("/profile")
    public Result userInfo(@RequestHeader("Authorization") String token) {
        return memberService.userInfo(token);
    }

}


