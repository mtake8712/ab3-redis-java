package com.example.ab3.demo.aws.elasticache.app.web;


import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ab3.demo.aws.elasticache.app.web.model.SampleSession;

@Controller
public class SampleController {
	
    @Value("${app.host}")
    private String host;
    
    @Value("${spring.session.redis.namespace}")
    private String hatena;
    
    @Autowired
    private SampleSession sampleSession;
    
    
    @RequestMapping(method=RequestMethod.POST, value="/{instanceGroup:[0-9]+}")
    public String sharedSession(
    		@RequestParam (name = "name", required = false) String name,
    		@RequestParam (name = "product", required = false) String product,
    		HttpSession session,
    		Model model) {
    	
    	sampleSession.setHost(host);
        sampleSession.setLastUpdatedAt(new Date());
        sampleSession.setName(name);
        sampleSession.setProduct(product);
        
        if (sampleSession.getSessionId() == null) {
        	sampleSession.setSessionId(session.getId());
        }
        
        model.addAttribute("sampleSession", sampleSession);
        
        return "sharedSession";
    }
    
    @RequestMapping(method=RequestMethod.GET, value="/invalidateSession")
    public String invalidateSession(HttpSession session){
        session.invalidate();
        return "redirect:/index.html";
    }
    
    @Autowired
    private StringRedisTemplate redisTemplate;
    
    @RequestMapping(method = RequestMethod.POST , value="/getSessionInfo")
    public String getSession(Model model, HttpSession session){
      String sessionId = session.getId();
      String redisKey = "spring:session:sessions:" + sessionId;
      HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
      
      Object sessionInfo = ops.get(redisKey, "sessionAttr:scopedTarget.sampleSession");
      String[] infos = sessionInfo.toString().split(",");
      String host_ = infos[1].split(":")[1].replace("\"", "");
      String name_ = infos[4].split(":")[1].replace("\"", "");
      String product_ = infos[5].split(":")[1].replace("\"", "");
      
      sampleSession.setHost(host_);
      sampleSession.setName(name_);
      sampleSession.setProduct(product_);
      model.addAttribute("sampleSession", sampleSession);
      
      return "getSessionInfo";
    }
    
}
