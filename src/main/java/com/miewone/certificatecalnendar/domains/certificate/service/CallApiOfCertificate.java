package com.miewone.certificatecalnendar.domains.certificate.service;

import com.google.gson.Gson;
import com.miewone.certificatecalnendar.domains.certificate.domain.qnetCertificateEntity;
import com.miewone.certificatecalnendar.domains.certificate.domain.qnetCertificateRepository;
import com.miewone.certificatecalnendar.domains.certificate.dto.CertificateDto;
import lombok.*;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@Service
public class CallApiOfCertificate {
    // 공공데이터포털에 한국산업인력공단_국가자격 시험일정 조회 Api에 요청한다.
    // 신청가능한 트래픽 100,000 , 일일 트래픽 1000
    // 요청주소 : http://apis.data.go.kr/B490007/qualExamSchd/getQualExamSchdList
    // 서비스 URL : http://apis.data.go.kr/B490007/qualExamSchd
    private final qnetCertificateRepository qnetcertificaterepository;
    @Value("${serviceKey}")
    private String serviceKey;
    private final static Logger LOGGER = Logger.getGlobal();

    @Scheduled(cron = "0 0 18 * * ?")
    public void callApi_qnet() throws IOException, ParseException, IllegalAccessException {
        for(int i=1;i<5;i++) {
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B490007/qualExamSchd/getQualExamSchdList"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + URLEncoder.encode(serviceKey, "UTF-8")); /*공공데이터포털에서 받은 인증키*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("50", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(i+"", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("dataFormat", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*응답 데이터 표준 형식 - xml / json (대소문자 구분 없음)*/
            urlBuilder.append("&" + URLEncoder.encode("implYy", "UTF-8") + "=" + URLEncoder.encode("2021", "UTF-8")); /*시행년도*/
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            JSONParser jsonParser = new JSONParser();
            JSONObject obj = (JSONObject) jsonParser.parse(sb + "");

            if (!((JSONObject) obj.get("header")).get("resultCode").toString().equals("00")) {
                LOGGER.info(((JSONObject) obj.get("header")).get("resultMsg").toString());
                return;
            } else
                LOGGER.info(((JSONObject) obj.get("header")).get("resultMsg").toString());
            List<JSONObject> ls = (JSONArray) ((JSONObject) obj.get("body")).get("items");

            noneName(ls);
        }
    }

    private void noneName(List<JSONObject> ls)
    {


        ArrayList<String> st = new ArrayList<>();

        int i=0;
        for(JSONObject item : ls)
        {
            Iterator<Map.Entry<String,Object>> s = item.entrySet().iterator();
            while(s.hasNext())
            {
                Map.Entry<String,Object> entry = s.next();
                String setString = entry.getValue()+"";
                st.add(setString);

            }
            CertificateDto dto = CertificateDto.builder()
                    .implYy(st.get(i++))
                    .pracPassDt(st.get(i++))
                    .docExamEndDt(st.get(i++))
                    .pracRegStartDt(st.get(i++))
                    .pracRegEndDt(st.get(i++))
                    .description(st.get(i++))
                    .qualgbCd(st.get(i++))
                    .docPassDt(st.get(i++))
                    .qualgbNm(st.get(i++))
                    .pracExamEndDt(st.get(i++))
                    .docRegEndDt(st.get(i++))
                    .pracExamStartDt(st.get(i++))
                    .docExamStartDt(st.get(i++))
                    .implSeq(Integer.parseInt(st.get(i++)))
                    .docRegStartDt(st.get(i++))
                    .build();

            qnetCertificateEntity certificateApply = saveCertificate(dto);
            qnetcertificaterepository.save(certificateApply);

            i=0;
            st.clear();
        }
    }

    private qnetCertificateEntity saveCertificate(CertificateDto dto)
    {

        return qnetCertificateEntity.builder()
                .implYy(dto.getImplYy())
                .implSeq(dto.getImplSeq())
                .qualgbCd(dto.getQualgbCd())
                .qualgbNm(dto.getQualgbNm())
                .description(dto.getDescription())
                .docRegStartDt(dto.getDocRegStartDt())
                .docRegEndDt(dto.getDocRegEndDt())
                .docExamStartDt(dto.getDocExamStartDt())
                .docExamEndDt(dto.getDocExamEndDt())
                .docPassDt(dto.getDocPassDt())
                .pracRegStartDt(dto.getPracRegStartDt())
                .pracRegEndDt(dto.getPracRegEndDt())
                .pracExamStartDt(dto.getPracExamStartDt())
                .pracExamEndDt(dto.getPracExamEndDt())
                .pracPassDt(dto.getPracPassDt())
                .build();
    }

}
