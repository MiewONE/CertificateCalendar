package com.miewone.certificatecalnendar.domains.certificate.presentation.api;

import com.miewone.certificatecalnendar.domains.certificate.domain.KoreaHistory.KoreaHistoryEntity;
import com.miewone.certificatecalnendar.domains.certificate.domain.Qnet.QnetCertificateEntity;
import com.miewone.certificatecalnendar.domains.certificate.domain.Toeic.ToeicCertificateEntity;
import com.miewone.certificatecalnendar.domains.certificate.service.CertificateCRUD;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class certificateApi {
    private final CertificateCRUD crud;

    @GetMapping("/api/certificate/qnet")
    public List<QnetCertificateEntity> AllQnetCertificates()
    {
        return crud.QnetCertificates();
    }

    @GetMapping("/api/certificate/toeic")
    public List<ToeicCertificateEntity> ToeicCertificates() {return crud.ToeicCertificate();}

    @GetMapping("/api/certificate/koreaHistory")
    public List<KoreaHistoryEntity> KoreaHistoryCertificates() {return crud.KoreaHistoryCertificate();}
}
