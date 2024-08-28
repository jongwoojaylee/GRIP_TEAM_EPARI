package org.example.grip_demo.user.interfaces;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class EmailService {

    private final Map<String, String> verificationCodes = new HashMap<>();

    public boolean sendVerificationCode(String email) {
        String code = generateVerificationCode();
        verificationCodes.put(email, code);
        System.out.println("인증번호는"+verificationCodes+"입니다.");
        return true;
    }

    public boolean verifyCode(String email, String code) {
        String savedCode = verificationCodes.get(email);
        boolean verified = savedCode != null && savedCode.equals(code);
        return true;
    }

    private String generateVerificationCode() {
        return String.valueOf(new Random().nextInt(999999));
    }
}