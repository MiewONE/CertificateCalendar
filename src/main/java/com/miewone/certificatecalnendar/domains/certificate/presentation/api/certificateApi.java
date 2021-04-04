package com.miewone.certificatecalnendar.domains.certificate.presentation.api;

import com.miewone.certificatecalnendar.domains.certificate.domain.Qnet.QnetCertificateEntity;
import com.miewone.certificatecalnendar.domains.certificate.service.CertificateCRUD;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class certificateApi {
    private final CertificateCRUD crud;

    @GetMapping("/api/certificate")
    public List<QnetCertificateEntity> AllCertificates()
    {
        return crud.certificates();
    }

}
