package com.manav.JotDown;

import com.manav.JotDown.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    void testMail() {
        emailService.sendEmail("manav.guptamg14@gmail.com", "test-subject", "HI, test body");
    }

}
