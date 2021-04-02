package com.miewone.certificatecalnendar.domains.certificate.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class Calendar {

    @GetMapping("/calendar")
    public String calnedar(Model model)
    {
        return "index";
    }

}
