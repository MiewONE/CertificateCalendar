1. 엔티티의 필드

    1.1 Member

        id : DB의 식별자
        email : 회원이메일을 나타냄
        pwd : 회원의 비밀번호
        role : 회원의 권한

    1.2 Apply

        applyid : DB의 식별자
        * applier : 신청자
        * applyCertificateList : 신청한 자격증의 리스트를 나타냅니다.
        * process : 신청 과정을 나타냅니다.
        applyDate : 주문한 시간을 나타냅니다.
        applyCount : 신청한 자격증의 갯수를 나타냅니다.

    1.3 Process

        processId : DB의 식별자
        applyResult : 신청 결과를 나타냅니다.

    1.4 ApplyCertificate

        id : DB의 식별자
        certificate : 자격증의 정보를 나타냅니다.
        applyCount : 신청한 자격증의 수를 나타냅니다.

    1.5 Certificate
        id : DB의 식별자
        name : 자격증의 이름을 나타냅니다.
        grade : 등급
        examApplyPeriod : 자격증 신청 기간을 나타냅니다.
