package com.miewone.certificatecalnendar.domains.certificate.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "qnetCertificate")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class qnetCertificateEntity {

    @Id @Column(length = 50)
    private String description;

    private String implYy;
    private int implSeq;
    private String qualgbCd;
    private String qualgbNm;

    private String docRegStartDt;
    private String docRegEndDt;
    private String docExamStartDt;
    private String docExamEndDt;
    private String docPassDt;
    private String pracRegStartDt;
    private String pracRegEndDt;
    private String pracExamStartDt;
    private String pracExamEndDt;
    private String pracPassDt;

    @Builder
    private qnetCertificateEntity(String implYy,int implSeq,String qualgbCd,String qualgbNm,String description,String docRegStartDt,String docRegEndDt,String docExamStartDt,String docExamEndDt,String docPassDt,String pracRegStartDt,String pracRegEndDt,String pracExamStartDt,String pracExamEndDt,String pracPassDt)
    {
        this.implYy = implYy;
        this.implSeq = implSeq;
        this.qualgbCd = qualgbCd;
        this.qualgbNm = qualgbNm;
        this.description = description;
        this.docRegStartDt = docRegStartDt;
        this.docRegEndDt = docRegEndDt;
        this.docExamStartDt = docExamStartDt;
        this.docExamEndDt = docExamEndDt;
        this.docPassDt = docPassDt;
        this.pracRegStartDt = pracRegStartDt;
        this.pracRegEndDt = pracRegEndDt;
        this.pracExamStartDt = pracExamStartDt;
        this.pracExamEndDt = pracExamEndDt;
        this.pracPassDt = pracPassDt;
    }
}
