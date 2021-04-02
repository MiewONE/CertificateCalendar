package com.miewone.certificatecalnendar.domains.certificate.presentation.api;

import com.miewone.certificatecalnendar.domains.certificate.domain.qnetCertificateEntity;
import com.miewone.certificatecalnendar.domains.certificate.service.certificateCRUD;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class certificateApi {
    private final certificateCRUD crud;

    @GetMapping("/api/certificate")
    public List<qnetCertificateEntity> AllCertificates()
    {
        return crud.certificates();
    }

}
