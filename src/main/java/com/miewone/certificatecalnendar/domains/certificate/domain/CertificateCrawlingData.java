package com.miewone.certificatecalnendar.domains.certificate.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CertificateCrawlingData {
    private String num; // 회차
    private String date; // 시험일
    private String reportPoint; //  성적발표일
    private String applyExam; // 접수기간
    private String addDt; // 토익 크롤링 할때는 사용 하지 않음
}
