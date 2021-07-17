package com.sjsu.travelflare;

import com.sjsu.travelflare.aws.AWSConfigurations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class TravelFlareApplication {

    @Autowired
    private ServletWebServerApplicationContext applicationContext;

    @Autowired
    private AWSConfigurations awsConfigurations;

    public static void main(String[] args) {
        SpringApplication.run(TravelFlareApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationStarted() {
        System.out.println("Travel Flare application has started at PORT::: " + applicationContext.getWebServer().getPort());
    }
}
