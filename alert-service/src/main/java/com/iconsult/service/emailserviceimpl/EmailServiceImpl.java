package com.iconsult.service.emailserviceimpl;


import com.iconsult.service.EmailService;
import com.iconsult.utility.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.Instant;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;



    @Override
    public void sendSimpleMessage(String to, String subject, String text) {

        text = text.isEmpty()?Util.generateOtp():text;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ahmedkalwar38@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }


}
