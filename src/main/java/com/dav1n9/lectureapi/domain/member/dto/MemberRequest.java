package com.dav1n9.lectureapi.domain.member.dto;

import com.dav1n9.lectureapi.domain.member.entity.Role;
import lombok.Getter;

@Getter
public class MemberRequest {
    private String email;
    private String password;
    private String gender;
    private String phoneNumber;
    private String address;
    private Role role;
}
