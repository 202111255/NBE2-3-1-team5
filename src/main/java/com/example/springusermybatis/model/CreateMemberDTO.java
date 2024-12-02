package com.example.springusermybatis.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Alias(value = "member")
@Getter
public class CreateMemberDTO {
    @Schema(description = "사용자 ID", example = "1")
    private Long memberId; // 데이터베이스의 memberId와 매핑

    @Schema(description = "사용자 이름", example = "username")
    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;

    @Schema(description = "사용자 이메일", example = "abcd@google.com")
    @Email(message = "유효한 이메일 형식이어야 합니다.")
    private String email;

    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "비밀번호는 최소 하나의 문자, 숫자, 특수 문자를 포함해야 합니다."
    )
    private String password;

    @Schema(description = "주소", example = "서울시 강남구")
    private String address;

    @Schema(description = "우편번호", example = "12345")
    private String zipcode;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



    public void setPassword(String password) {
        this.password = password;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


}
