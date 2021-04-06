package com.miewone.certificatecalnendar.domains.certificate.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
public class Calendar {

    private final static Logger LOGGER = Logger.getGlobal();
    @GetMapping("/calendar")
    public String calnedar(Model model) throws UnknownHostException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = request.getHeader("X-FORWARDED-FOR");
        String ips = request.getLocalAddr();
        String ipp = Inet4Address.getLocalHost().getHostAddress();
        if (ip == null) {
            ip = request.getHeader("PROXY-CLIENT-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-PROXY-CLIENT-IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        LOGGER.info("Access IP : "+ip);
        LOGGER.info("Access IP : "+ipp);
        return "index";
    }

}
