package com.example.ab3.demo.aws.elasticache.app.web;

import javax.servlet.http.HttpSession;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.ab3.demo.aws.elasticache.app.web.model.SampleSession;

@Controller
public class SampleController {

    @Value("${app.host}")
    private String host;
    @Autowired
    private SampleSession sampleSession;

    @RequestMapping(method=RequestMethod.GET, value="/{containerGroup:[0-9]+}")
    public String sharedSession(Model model){
        sampleSession.setHost(host);
        sampleSession.setLastUpdatedAt(new Date());
        model.addAttribute("sampleSession", sampleSession);
        return "sharedSession";
    }

    @RequestMapping(method=RequestMethod.GET, value="/invalidateSession")
    public String invalidateSession(HttpSession session){
        session.invalidate();
        return "redirect:/index.html";
    }

}
