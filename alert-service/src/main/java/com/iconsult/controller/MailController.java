package com.iconsult.controller;
import com.iconsult.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.*;

@RestController
public class MailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-mail")
    public void sendEmailMessage(@RequestParam(name = "to") String to,
                                 @RequestParam(name = "text",required = false,defaultValue = "")  String text){
        this.emailService.sendSimpleMessage(to,"Login-Alert",text);
    }
}
