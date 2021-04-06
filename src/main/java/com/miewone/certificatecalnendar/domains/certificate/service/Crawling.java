package com.miewone.certificatecalnendar.domains.certificate.service;

import com.miewone.certificatecalnendar.domains.certificate.domain.CertificateCrawlingData;
import com.miewone.certificatecalnendar.domains.certificate.domain.KoreaHistory.KoreaHistoryEntity;
import com.miewone.certificatecalnendar.domains.certificate.domain.KoreaHistory.KoreaHistoryRepository;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Crawling {
    private final ToeicCertificateRepository toeicCertificateRepository;
    private final KoreaHistoryRepository koreaHistoryRepository;
    List<CertificateCrawlingData> tdInnerText = new ArrayList<>();
    List<String> tdText = new ArrayList<>();

    String implYy; // 시행년도
    Long implSeq; // 시행회차
    String description; // 설명
    String ExamStartDt; // 시험 일자
    String RegStartDt; // 시험접수 시작일자
    String RegEndDt; // 시험접수 종료일자
    String SpcialRegStartDt; // 특별추가 접수 시작일자
    String SpcialRegEndDt; // 특별추가 접수 종료 일자.
    String PassDt; // 시험 합격자 발표일자
    private List<CertificateCrawlingData> Toeicget(URL url,String baseUrl,String docSeletor)
    {
        try
        {
            Document doc = Jsoup.parse(url.openStream(),"utf-8",baseUrl);

            Elements periodExam = doc.select(docSeletor);

            for(Element postPeriod : periodExam)
            {
                System.out.println(postPeriod);
                Elements tds = postPeriod.select("td");
                for(Element td : tds)
                {
                    tdText.add(td.text());
                }
                if(!tdText.isEmpty())
                {
                    if(url.toString().contains("toeic"))
                    {
                        tdInnerText.add(new CertificateCrawlingData(tdText.get(0),tdText.get(1),tdText.get(2),tdText.get(3),null));
                        tdText.clear();
                    }else
                    {
                        tdInnerText.add(new CertificateCrawlingData(tdText.get(0),tdText.get(1),tdText.get(2),tdText.get(3),tdText.get(4)));
                        tdText.clear();
                    }

                }
            }
        }catch(IOException e)
        {
            System.out.println(url+"\t정보 수집 불가.");
        }
        return tdInnerText;

    }


    public void saveKoreaHistoryInfo() throws MalformedURLException
    {
        List<CertificateCrawlingData> koreaHistoryInfo = Toeicget(new URL("http://www.historyexam.go.kr/pageLink.do?link=examSchedule")," www.historyexam.go.kr","#sub_content > div.right_sider > div.right_contents > table:nth-child(4) > tbody > tr");

        for(var data : koreaHistoryInfo)
        {
            String temp = "";
            implSeq = Long.parseLong(data.getNum().substring(data.getNum().indexOf("제")+1,data.getNum().indexOf("회")));
            implYy = data.getApplyExam().substring(0,data.getApplyExam().indexOf("년"));
            String[] dateSplit = data.getApplyExam().split(" ");
            ExamStartDt=DateSubstring(dateSplit);
            dateSplit = data.getDate().split("~");
            RegStartDt = DateSubstring(dateSplit[0].split(" "));
            RegEndDt = DateSubstring(dateSplit[1].split(" "));
            dateSplit = data.getReportPoint().split("~");
            SpcialRegStartDt = DateSubstring(dateSplit[0].split(" "));
            SpcialRegEndDt =DateSubstring(dateSplit[1].split(" "));
            PassDt = DateSubstring(data.getApplyExam().split(" "));
            description = "한국사 "+implSeq+"회";

            KoreaHistoryEntity entity = KoreaHistoryEntity.builder()
                    .implSeq(implSeq)
                    .implYy(implYy)
                    .ExamStartDt(ExamStartDt)
                    .RegStartDt(RegStartDt)
                    .RegEndDt(RegEndDt)
                    .SpcialRegStartDt(SpcialRegStartDt)
                    .SpcialRegEndDt(SpcialRegEndDt)
                    .PassDt(PassDt)
                    .description(description)
                    .build();
            koreaHistoryRepository.save(entity);
//            ExamStartDt =data.getApplyExam().replaceAll("\\p{Z}","").substring(data.getApplyExam().indexOf("일")+1)
        }
    }
    public void saveToeicInfo() throws MalformedURLException {
        List<CertificateCrawlingData> toeicInfo = Toeicget(new URL("https://exam.toeic.co.kr/receipt/examSchList.php"),"https://exam.toeic.co.kr","tbody tr");



        for(var data : toeicInfo)
        {

            if(data.getNum().contains("★ ")) {
                implSeq = Long.parseLong(data.getNum().replaceAll("\\p{Z}","").substring(2, 5));
            }else
            {
                implSeq = Long.parseLong(data.getNum().substring(1,4));
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
                    .implSeq(implSeq)
                    .implYy(implYy)
                    .description("토익 "+implSeq+"회")
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
    private String DateSubstring(String[] dateSplit)
    {
        String temp= "";
        for(var date : dateSplit) //년 월 일 담겨져있는 문자열에서 yyyyMMdd형식으로 뽑아내기
        {
            if(date.contains("년"))
                temp+=date.substring(0,date.indexOf("년"));
            else if(date.contains("월"))
            {
                date = date.substring(0, date.indexOf("월"));
                date = date.length()<2?"0"+date:date;
                temp += date;
            }
            else if(date.contains("일"))
            {
                date = date.substring(0, date.indexOf("일"));
                date = date.length()<2?"0"+date:date;
                temp += date;
            }
        }
        return temp;
    }
}
