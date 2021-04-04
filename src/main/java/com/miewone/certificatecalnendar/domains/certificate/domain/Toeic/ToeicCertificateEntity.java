package com.miewone.certificatecalnendar.domains.certificate.domain.Toeic;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="toeic_certificate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ToeicCertificateEntity {
    @Id
    Long impl_seq; // 시행회차
    String implYy; // 시행년도
    String description; // 설명
    String ExamStartDt; // 시험 일자
    String RegStartDt; // 시험접수 시작일자
    String RegEndDt; // 시험접수 종료일자
    String SpcialRegStartDt; // 특별추가 접수 시작일자
    String SpcialRegEndDt; // 특별추가 접수 종료 일자.
    String PassDt; // 시험 합격자 발표일자

    @Builder
    public ToeicCertificateEntity(Long impl_seq,String implYy,String description,String ExamStartDt,String RegStartDt,String RegEndDt,String SpcialRegStartDt,String SpcialRegEndDt,String PassDt)
    {
        this.impl_seq = impl_seq;
        this.implYy = implYy;
        this.description = description;
        this.ExamStartDt = ExamStartDt;
        this.RegStartDt = RegStartDt;
        this.RegEndDt = RegEndDt;
        this.SpcialRegStartDt = SpcialRegStartDt;
        this.SpcialRegEndDt = SpcialRegEndDt;
        this.PassDt = PassDt;
    }
}
