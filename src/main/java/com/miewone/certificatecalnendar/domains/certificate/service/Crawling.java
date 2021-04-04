package com.miewone.certificatecalnendar.domains.certificate.service;

import com.miewone.certificatecalnendar.domains.certificate.domain.CertificateCrawlingData;
import com.miewone.certificatecalnendar.domains.certificate.domain.Qnet.QnetCertificateEntity;
import com.miewone.certificatecalnendar.domains.certificate.domain.Qnet.QnetCertificateRepository;
import com.miewone.certificatecalnendar.domains.certificate.domain.Toeic.ToeicCertificateEntity;
import com.miewone.certificatecalnendar.domains.certificate.domain.Toeic.ToeicCertificateRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Crawling {
    private final ToeicCertificateRepository toeicCertificateRepository;
    List<CertificateCrawlingData> tdInnerText = new ArrayList<>();
    List<String> tdText = new ArrayList<>();

    private List<CertificateCrawlingData> Toeicget()
    {
        try
        {
            Document doc = Jsoup.parse(new URL("https://exam.toeic.co.kr/receipt/examSchList.php").openStream(),"utf-8","https://exam.toeic.co.kr");
            Elements periodExam = doc.select("tbody tr");

            for(Element postPeriod : periodExam)
            {
                System.out.println(postPeriod);
                Elements tds = postPeriod.select("td");
                for(Element td : tds)
                {
                    tdText.add(td.text());
                }
                tdInnerText.add(new CertificateCrawlingData(
                        tdText.get(0),
                        tdText.get(1),
                        tdText.get(2),
                        tdText.get(3)));
                tdText.clear();
            }
            System.out.println("123");
        }catch(IOException e)
        {

        }
        return tdInnerText;

    }

    public void saveToeicInfo()
    {
        List<CertificateCrawlingData> toeicInfo = Toeicget();

        String implYy; // 시행년도
        Long impl_seq; // 시행회차
        String description; // 설명
        String ExamStartDt; // 시험 일자
        String RegStartDt; // 시험접수 시작일자
        String RegEndDt; // 시험접수 종료일자
        String SpcialRegStartDt; // 특별추가 접수 시작일자
        String SpcialRegEndDt; // 특별추가 접수 종료 일자.
        String PassDt; // 시험 합격자 발표일자

        for(var data : toeicInfo)
        {

            if(data.getNum().contains("★ ")) {
                impl_seq = Long.parseLong(data.getNum().replaceAll("\\p{Z}","").substring(2, 5));
            }else
            {
                impl_seq = Long.parseLong(data.getNum().substring(1,4));
            }
            ExamStartDt = data.getDate().substring(0,10).replaceAll("\\.","");
            PassDt = data.getReportPoint().substring(0,10).replaceAll("\\.","");
            RegStartDt = data.getApplyExam().replaceAll("\\p{Z}","").replaceAll("\\.","");;
            String[] test = RegStartDt.split(":");
            RegStartDt = test[1].substring(0,8);
            RegEndDt = test[1].split("~")[1].substring(0,8);
            SpcialRegStartDt = test[2].substring(0,8);
            SpcialRegEndDt = test[2].split("~")[1].substring(0,8);
            implYy = ExamStartDt.substring(0,4);

            ToeicCertificateEntity entity = ToeicCertificateEntity.builder()
                    .impl_seq(impl_seq)
                    .implYy(implYy)
                    .description("토익 "+impl_seq)
                    .ExamStartDt(ExamStartDt)
                    .RegStartDt(RegStartDt)
                    .RegEndDt(RegEndDt)
                    .SpcialRegStartDt(SpcialRegStartDt)
                    .SpcialRegEndDt(SpcialRegEndDt)
                    .PassDt(PassDt)
                    .build();
            toeicCertificateRepository.save(entity);
            System.out.println();
        }

    }
}
