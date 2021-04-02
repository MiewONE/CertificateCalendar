package com.miewone.certificatecalnendar.common.config.logging;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass // 이 엔티티를 상속받는 클래스에게 단순히 매핑정보만을 상속
@EntityListeners(AuditingEntityListener.class) // db의 특정 동작을 하기 전 또는 후에 커스텀 콜백을 요청할 수 있도록 하는 어노테이션
@Getter
public class BaseEntity {
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
