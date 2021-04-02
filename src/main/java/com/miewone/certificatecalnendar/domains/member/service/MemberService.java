package com.miewone.certificatecalnendar.domains.member.service;

import com.miewone.certificatecalnendar.domains.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public Long signUp(SignupRequest req)
    {
        return null;
    }
}
