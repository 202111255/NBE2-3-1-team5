package com.example.coffee.controller.user;

import com.example.coffee.model.user.CreateMemberDTO;
import com.example.coffee.model.user.LoginRequestDTO;
import com.example.coffee.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.example.coffee.common.Result;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public Result signup(@Valid @RequestBody CreateMemberDTO createMemberDTO) {
        return authService.signup(createMemberDTO);
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        return authService.login(loginRequestDTO, response);
    }

    @PutMapping("/logout")
    public Result logout(@AuthenticationPrincipal Long userId, HttpServletResponse response) {
        return authService.logout(userId, response);
    }

    @GetMapping("/profile")
    public Result userInfo(@AuthenticationPrincipal Long userId) {
        return authService.getUserProfile(userId);
    }

    @PostMapping("/recreatetoken")
    public Result reCreateToken(HttpServletRequest request) {
        return authService.reCreateToken(request);
    }
}
