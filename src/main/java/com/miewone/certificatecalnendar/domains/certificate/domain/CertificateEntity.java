package com.miewone.certificatecalnendar.domains.certificate.domain;

import com.miewone.certificatecalnendar.common.config.logging.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "certificate")
@Getter
public class CertificateEntity extends BaseEntity
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificateId;
    private String name;
    private String grade;
    private LocalDateTime examApplyStartDate;
    private LocalDateTime examApplyEndDate;

    @Builder
    private CertificateEntity(String name,String grade,LocalDateTime examApplyEndDate,LocalDateTime examApplyStartDate)
    {
        this.name = name;
        this.grade = grade;
        this.examApplyStartDate = examApplyStartDate;
        this.examApplyEndDate = examApplyEndDate;
    }
}
