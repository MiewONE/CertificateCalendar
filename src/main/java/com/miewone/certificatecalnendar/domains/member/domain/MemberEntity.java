package com.miewone.certificatecalnendar.domains.member.domain;

import com.miewone.certificatecalnendar.common.config.logging.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class MemberEntity extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memeberId;
    private String email;
    private String authPw;

//    private Role role; // Spring Security 추가할때 추가할 필드

    @Builder
    private MemberEntity(String email,String authPw)
    {
        this.email = email;
        this.authPw = authPw;
    }
}
