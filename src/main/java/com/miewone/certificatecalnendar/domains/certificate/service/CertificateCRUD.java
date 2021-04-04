package com.miewone.certificatecalnendar.domains.certificate.service;

import com.miewone.certificatecalnendar.domains.certificate.domain.Qnet.QnetCertificateEntity;
import com.miewone.certificatecalnendar.domains.certificate.domain.Qnet.QnetCertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateCRUD {
    private final QnetCertificateRepository qnetCertificateRepository;

    public List<QnetCertificateEntity> certificates()
    {
        return qnetCertificateRepository.findAll(Sort.by(Sort.Direction.ASC,"description"));
    }

//    private Sort sortByDescriptionAsc()
//    {
//        return new Sort(Sort.Direction.ASC,"description");
//    }
}
