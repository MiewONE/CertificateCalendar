package com.miewone.certificatecalnendar.domains.certificate.domain.Qnet;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnetCertificateRepository extends JpaRepository<QnetCertificateEntity,String> {
    List<QnetCertificateEntity> findAll(Sort sort);
}
