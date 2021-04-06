package com.miewone.certificatecalnendar;

import com.miewone.certificatecalnendar.domains.certificate.domain.Toeic.ToeicCertificateEntity;
import com.miewone.certificatecalnendar.domains.certificate.domain.Toeic.ToeicCertificateRepository;
import com.miewone.certificatecalnendar.domains.certificate.service.CallApiOfCertificate;
import com.miewone.certificatecalnendar.domains.certificate.service.Crawling;
import org.apache.coyote.Response;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CertificatecalnendarApplicationTests {
    @Autowired
    private CallApiOfCertificate callApiOfCertificate;
    @Autowired
    private ToeicCertificateRepository toeicCertificateRepository;
    @Autowired
    private Crawling crawlingGet;
    @LocalServerPort
    private int port;

    RestTemplate client = new RestTemplate();
    @Test
    void contextLoads() throws IOException, ParseException, IllegalAccessException {

        callApiOfCertificate.callApi_qnet();
    }
    @DisplayName("1.자격증 정보 API로 호출하기")
    @Test
    void  apiGetCall()
    {
        String url = "http://localhost:"+port+"/api/certificate/qnet";

        ResponseEntity<String> entity = client.getForEntity(url,String.class);
//        List<qnetCertificateEntity> t = entity.getBody();
    }

    @DisplayName("2. 크롤링 테스트")
    @Test
    void crawlingText() throws MalformedURLException {
        crawlingGet.saveToeicInfo();
    }

    @DisplayName("3. 토익 api 테스트")
    @Test
    void apiGetToeicCall()
    {
        String url = "http://localhost:"+port+"/api/certificate/toeic";
//        ResponseEntity<String> entity = client.getForEntity(url,String.class);
        var ts = client.getForEntity(url,Object.class);

    }

    @DisplayName("3.1. 토익 repository select 테스트")
    @Test
    void listGet()
    {
        List<ToeicCertificateEntity> list = toeicCertificateRepository.findAll();
    }
    @DisplayName("4. 한국사 크롤링 테슽")
    @Test
    void  crawlingKoreaHistory() throws MalformedURLException
    {
        crawlingGet.saveKoreaHistoryInfo();
    }
}
