package com.miewone.certificatecalnendar.domains.certificate.domain.Toeic;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToeicCertificateRepository extends JpaRepository<ToeicCertificateEntity,Long> {
    List<ToeicCertificateEntity> findAll();
}
