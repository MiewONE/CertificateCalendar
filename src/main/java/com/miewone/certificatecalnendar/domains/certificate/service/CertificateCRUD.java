package com.miewone.certificatecalnendar.domains.certificate.service;

import com.miewone.certificatecalnendar.domains.certificate.domain.Qnet.QnetCertificateEntity;
import com.miewone.certificatecalnendar.domains.certificate.domain.Qnet.QnetCertificateRepository;
import com.miewone.certificatecalnendar.domains.certificate.domain.Toeic.ToeicCertificateEntity;
import com.miewone.certificatecalnendar.domains.certificate.domain.Toeic.ToeicCertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateCRUD {
    private final QnetCertificateRepository qnetCertificateRepository;
    private final ToeicCertificateRepository toeicCertificateRepository;
    public List<QnetCertificateEntity> QnetCertificates()
    {
        return qnetCertificateRepository.findAll(Sort.by(Sort.Direction.ASC,"description"));
    }
    public List<ToeicCertificateEntity> ToeicCertificate()
    {
        return toeicCertificateRepository.findAll();
    }
}
