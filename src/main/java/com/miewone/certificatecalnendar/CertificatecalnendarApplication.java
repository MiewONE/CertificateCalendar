package com.miewone.certificatecalnendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.persistence.EntityListeners;

@EnableScheduling
@EntityListeners(AuditingEntityListener.class)
@SpringBootApplication
public class CertificatecalnendarApplication {

    public static void main(String[] args) {
        SpringApplication.run(CertificatecalnendarApplication.class, args);
    }

}
