package com.miewone.certificatecalnendar.domains.member.service;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class SignupRequest {
//    @NotBlank
    private String email;
    private String password;
}
