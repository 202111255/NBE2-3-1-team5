package com.example.coffee.model.user;

import lombok.Getter;

@Getter
public class UpdateMemberDTO {
    private Long memberId;
    private String name;
    private String address;
    private String zipcode;

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
