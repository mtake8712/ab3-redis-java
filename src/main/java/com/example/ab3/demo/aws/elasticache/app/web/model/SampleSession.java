package com.example.ab3.demo.aws.elasticache.app.web.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SessionScope
@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SampleSession implements Serializable {

    private String host;
    private Date lastUpdatedAt;

}
