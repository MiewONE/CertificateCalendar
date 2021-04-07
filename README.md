# CertificateCalendar

[사이트 구동 확인](http://182.225.141.20:8080/calendar)
## 목적
    q-net , 한국사검정능력시험, 토익 등 여러 자격증들의 일정을 모아서 보여주는 사이트를 개발

## 1. 개발도구
* 프론트 : javascript
* 서버 : spring framework
* 빌드 및 배포 도구 : gradle
* 템플릿 엔진 : thymeleaf
* 테스트 도구 : Junit*
* 필요한 라이브러리 

        Jsoup - 크롤링을 한 후 Html을 파싱하기 위함.
        Spring JPA - DB 사용
        Spring Web - http 메서드를 사용하기 위해
        Lombok - 데이터를 손쉽게 다루기 위해서.
        (추가 예정) Spring Security - 로그인 및 보안
## 2. 설계
1. 엔티티
    * 도메인 모델
    요구사항을 토대로 도메인 모델을 도출
    
        ![image](https://user-images.githubusercontent.com/51110811/113099266-a6d71400-9234-11eb-88cb-f8d2fd3fdc5b.png)

    - 회원은 여러번의 신청이 가능하므로 1:N 관계를 가진다.
    - 각각의 신청에는 진행과정이 필요하기때문에 신청과 진행과정은 1:1 관계를 가진다.
    - 각 신청은 여러개의 신청자격증을 포함할 수 있고, 각 자격증역시 여러 신청에 포함될 수 있다. 때문에 N :  N 관계
    - DB에서는 N : N 관계를 표현할 수 없음. 1:N N:1관계로 풀어서 표현해야함. 

2. 클래스
    
    * ![image](https://user-images.githubusercontent.com/51110811/113098697-c9b4f880-9233-11eb-9f2d-d6cf9f4de012.png)

    - 화살표선은 *단방향 연관관계* 를 나타낸다.
    - 빨간색 테두리는 *연관관계*를 나타내는 필드
    - 
4. DB


## 일정을 가져올 Api 또는 사이트 크롤링 이용
* Q-net : 공공데이터 포털의 Api를 이용
  
  <img src="https://user-images.githubusercontent.com/51110811/113085228-666a9c80-921a-11eb-90aa-0f18cc5850d0.png" width="500" height="300">
      
* 한국사능력검정시험 : 상공회의소 와 동 [한국사능력검정시험 일정 URL](http://www.historyexam.go.kr/pageLink.do?link=examSchedule&netfunnel_key=E3F6E920AEF0F72B5FC31EAFDE34542CA2466536624D776F8B997A108DC2E300C8E83DA2516FA2F81F5B13F753C1328764B9521C51BD7E1AD136B18732FF1F58EED1594C22F2A2F4F858FAC3814C3D451CDCA07A9B01911625F47363C1274FCEEE4301E896CCCBABBF1C7F32CA7A9D942C312C302C30)
    
    <img src="https://user-images.githubusercontent.com/51110811/113808653-6d0c8d00-97a1-11eb-884c-ce911bfd3cbb.png" width="500" height="300">
    
    시험일정은 html의 tbody형식으로 되어있어 Jsoup의 css selector로 골라났으며 회차, 시험일, 성적발표일, 온라인 접수기간을 가지고 회차,시행년도,온라인접수기간시작일,온라인접수기간종료일,특별추가접수기간시작일,특별추가접수기간종료일,성적발표일,시험일로 구분하여 JPA를 이용하여 DB에 저장을 하였습니다.

      
* 토익 : 상공회의소와 동 [토익 시험일정 URL](https://exam.toeic.co.kr/receipt/examSchList.php)

    <img src="https://user-images.githubusercontent.com/51110811/113808384-db048480-97a0-11eb-848d-625f9dfeb682.png" width="500" height="300">
    
    시험일정은 html의 tbody형식으로 되어있어 Jsoup의 css selector로 골라났으며 회차, 시험일, 성적발표일, 온라인 접수기간을 가지고 회차,시행년도,온라인접수기간시작일,온라인접수기간종료일,특별추가접수기간시작일,특별추가접수기간종료일,성적발표일,시험일로 구분하여 JPA를 이용하여 DB에 저장을 하였습니다.
    
## 최종화면
    
<img src="https://user-images.githubusercontent.com/51110811/113808716-92010000-97a1-11eb-9bba-8047839f852a.png" width="800" height="400">

    기능
    
        * q-net 자격증, 토익, 한국사 자격증들에서 필기 실기 원서접수기간, 필기 실기 시험일자, 합격자 발표등을 해당하는 날짜에 표현
        * 자격증 별 보고싶은 자격증만 보여지게 가능
        * 검색을 통하여 보고싶은 항목을 골라볼수 있음(정규표현식 가능)
        * 원서접수,시험일자,합격자 발표를 선택적으로 볼수있음.
        * 하나의 셀에 마우스를 가져가면 해당하는 일에 자격증들의 상세내역을 보여줌.
        * 셀을 클릭하면 1.5초동안 다른 셀에 마우스를 가져가도 상세내역이 바귀지 않음

<img src="https://user-images.githubusercontent.com/51110811/113809901-12286500-97a4-11eb-9b07-b91d16f23fe2.png" width="300" height="600">
        