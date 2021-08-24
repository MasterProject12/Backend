package com.sjsu.travelflare;

import com.sjsu.travelflare.aws.AWSConfigurations;
import com.sjsu.travelflare.models.geocode.ReverseGeocode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        TravelFlareApplication.class, ServletWebServerApplicationContext.class, AWSConfigurations.class
})
public class TravelFlareModelsTest {

    @Test
    public void testReverseGeocode() {
        ReverseGeocode reverseGeocode = new ReverseGeocode("USA", "Ca", "Fremont");
        Assert.assertEquals("USA-Ca-Fremont", reverseGeocode.toString());
    }
}
