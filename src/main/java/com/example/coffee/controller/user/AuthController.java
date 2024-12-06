package com.example.coffee.controller.user;

import com.example.coffee.model.user.RequestMemberDTO;
import com.example.coffee.model.user.LoginRequestDTO;
import com.example.coffee.model.user.UpdateMemberDTO;
import com.example.coffee.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.example.coffee.common.Result;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public Result signup(@Valid @RequestBody RequestMemberDTO createMemberDTO) {
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

    @PutMapping
    public Result updateUser(@AuthenticationPrincipal Long userId, @RequestBody UpdateMemberDTO updateMemberDTO){
        return authService.updateUser(userId, updateMemberDTO);
    }

    @DeleteMapping
    public Result deleteUser(@AuthenticationPrincipal Long userId) {
        return authService.deleteUser(userId);
    }

    @PostMapping("/new-token")
    public Result reCreateToken(HttpServletRequest request) {
        return authService.reCreateToken(request);
    }
}
