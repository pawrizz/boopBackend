package boop.auth.service;

import org.springframework.stereotype.Service;

@Service
public class OtpService {

    public boolean verify(String phone, String otp) {
        // STUB logic for now
        // Accept "1234" as valid OTP
        return "1234".equals(otp);
    }
}
