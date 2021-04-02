package com.miewone.certificatecalnendar.domains.certificate.service;

import com.miewone.certificatecalnendar.domains.certificate.domain.qnetCertificateEntity;
import com.miewone.certificatecalnendar.domains.certificate.domain.qnetCertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class certificateCRUD {
    private final qnetCertificateRepository qnetCertificateRepository;

    public List<qnetCertificateEntity> certificates()
    {
        return qnetCertificateRepository.findAll();
    }

}
