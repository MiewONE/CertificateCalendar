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

        String ClientName = Inet4Address.getLocalHost().getHostName();
        String ClientIP = Inet4Address.getLocalHost().getHostAddress();
        LOGGER.info("Access Name : "+ClientName);
        LOGGER.info("Access IP : "+ClientIP);
        return "index";
    }

}
