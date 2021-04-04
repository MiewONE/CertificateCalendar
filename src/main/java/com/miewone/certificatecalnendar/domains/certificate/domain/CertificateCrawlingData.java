package com.miewone.certificatecalnendar.domains.certificate.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CertificateCrawlingData {
    private String num;
    private String date;
    private String reportPoint;
    private String applyExam;
}
