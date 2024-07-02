package com.bangvan.EMwebapp.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String email, String otp) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Verify OTP");
        mimeMessageHelper.setText("""
                Xin chào, cám ơn bạn đã thực hiện đăng kí trên trang web của chúng tôi!
                Đây là mã OTP của bạn: \n"""
                + otp +
                """
                \n---------------------------------------
                Trang web này được làm bởi nhóm 2 môn an toàn bảo mật thông tin
                Thành viên của nhóm chúng tôi bao gồm:
                - Trần Văn Bằng
                - Hoàng Thị Cúc        
                - Lương Đắc Chí 
                - Nguyễn Minh Chiến
                -----------------------------------------
                
                """);
        mailSender.send(mimeMessage);

    }
}
