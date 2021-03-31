# CertificateCalendar

## 목적
    q-net , 대한상공회의소, 토익 등 여러 자격증들의 일정을 모아서 보여주는 사이트를 개발

## 개발도구
    1. 프론트 : javascript
    2. 서버 : spring framework
    3. 빌드 및 배포 도구 : gradle
    4. 템플릿 엔진 : thymeleaf
    5. 테스트 도구 : Junit
    6. 필요한 라이브러리 
        Jsoup - 크롤링을 한 후 Html을 파싱하기 위함.
        Spring JPA - DB 사용
        Spring Web - http 메서드를 사용하기 위해
        Lombok - 데이터를 손쉽게 다루기 위해서.
        (추가 예정) Spring Security - 로그인 및 보안

## 일정을 가져올 Api 또는 사이트 크롤링 이용
* Q-net : 공공데이터 포털의 Api를 이용
      ![image](https://user-images.githubusercontent.com/51110811/113085228-666a9c80-921a-11eb-90aa-0f18cc5850d0.png)
* 대한상공회의소 : 따로 Api가 존재하지않아 크롤링을 진행.[대한상공회의소 시험일정 URL](https://license.korcham.net/co/examschedule.do?cate=&sdate=20210331&edate=20211231&pg=1)

      마침 대한상공회의소에서 일정을 모아둔 페이지가 있어 해당 에서 table에 존재하는 데이터를 긁어서 보여줄 예정 
* 한국사능력검정시험 : 상공회의소 와 동 [한국사능력검정시험 일정 URL](http://www.historyexam.go.kr/pageLink.do?link=examSchedule&netfunnel_key=E3F6E920AEF0F72B5FC31EAFDE34542CA2466536624D776F8B997A108DC2E300C8E83DA2516FA2F81F5B13F753C1328764B9521C51BD7E1AD136B18732FF1F58EED1594C22F2A2F4F858FAC3814C3D451CDCA07A9B01911625F47363C1274FCEEE4301E896CCCBABBF1C7F32CA7A9D942C312C302C30)

      시험 일정 Url : 
      
* 토익 : 상공회의소와 동 [토익 시험일정 URL](https://exam.toeic.co.kr/receipt/examSchList.php)


## 설계
    1. 엔티티
    2. 클래스
    3. DB
