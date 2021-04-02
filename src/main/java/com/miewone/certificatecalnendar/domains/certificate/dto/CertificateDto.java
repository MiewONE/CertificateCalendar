package com.miewone.certificatecalnendar.domains.certificate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@Builder
public class CertificateDto {
    private String implYy;
    private String pracPassDt;
    private String docExamEndDt;
    private String pracRegStartDt;
    private String pracRegEndDt;
    private String description;
    private String qualgbCd;
    private String docPassDt;
    private String qualgbNm;
    private String pracExamEndDt;
    private String docRegEndDt;
    private String pracExamStartDt;
    private String docExamStartDt;
    private int implSeq;
    private String docRegStartDt;

}
