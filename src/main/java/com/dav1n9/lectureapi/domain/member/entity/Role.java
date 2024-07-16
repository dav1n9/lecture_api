package com.dav1n9.lectureapi.domain.member.entity;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN(Authority.ADMIN),
    USER(Authority.USER);

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    public static class Authority {
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String USER = "ROLE_USER";
    }
}
