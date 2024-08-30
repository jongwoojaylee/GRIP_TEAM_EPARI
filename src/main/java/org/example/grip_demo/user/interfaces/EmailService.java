package org.example.grip_demo.user.interfaces;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final Map<String, String> verificationCodes = new HashMap<>();
    private final JavaMailSender javaMailSender;
    private static final String senderEmail= "jonggrip@gmail.com";

    public boolean sendVerificationCode(String email) {
        String code = generateVerificationCode();
        verificationCodes.put(email, code);

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject("이메일 인증");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + code + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body,"UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);

        System.out.println("인증번호는"+verificationCodes+"입니다.");
        return true;
    }

    public boolean verifyCode(String email, String code) {
        String savedCode = verificationCodes.get(email);
        boolean verified = savedCode != null && savedCode.equals(code);
        return verified;
    }

    private String generateVerificationCode() {
        return String.valueOf(new Random().nextInt(999999));
    }
}