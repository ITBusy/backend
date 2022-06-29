package com.poly.service.impl;

import com.poly.dto.SendMailOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SendMailServiceImp implements com.poly.service.ISendMailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public String code() {
        String code = null;
        for (int i = 0; i < 100000; i++) {
            code = String.valueOf(100000 + Math.round(Math.random() * 899900));
        }
        return code;
    }

    @Override
    public void sendCodeConfirm(String toMail, String name, String codeConfirmation) {
        try {
            final String subject = "Mã Xác Nhận";
            String htmlContent = "<h4 style=\"color: #333;\">Xin chào " + name + ",</h4>\n"
                    + "    <div style=\"display: flex;\">\n"
                    + "        <span style=\"color: #333;\">Mã đăng ký tài khoản của bạn và\n"
                    + "            có hiệu lực trong 5 phút:</span>\n"
                    + "    </div>\n"
                    + "\n"
                    + "    <div style=\"display: flex;\">\n"
                    + "        <button style=\"height: 80px; width: 340px; margin: auto; align-items: center; \n"
                    + "                              justify-content: center; font-size: 3rem;border: 1px solid blue; color: white; \n"
                    + "                              text-align: center; background-color: lightgray; \" disable>" + codeConfirmation.substring(0, 3) + "-" + codeConfirmation.substring(3) + "</button>\n"
                    + "    </div>";

            MimeMessage message = this.javaMailSender.createMimeMessage();
            MimeMessageHelper msg = new MimeMessageHelper(message, true);
            msg.setSubject(subject);
            msg.setText(htmlContent, true);
            msg.setTo(toMail);
            this.javaMailSender.send(message);
        } catch (MessagingException ex) {
            System.out.println("Error sendCodeConfirm: " + ex.getMessage());
        }
    }

    @Override
    public void sendMailOrder(SendMailOrderDto sendMailOrderDto) throws MessagingException {
        final String subject = "Đơn Hàng";
        MimeMessage message = this.javaMailSender.createMimeMessage();
        MimeMessageHelper msg = new MimeMessageHelper(message, true);
        msg.setSubject(subject);
        msg.setText(sendMailOrderDto.getContentHtml(), true);
        msg.setTo(sendMailOrderDto.getToMail());
        this.javaMailSender.send(message);
    }
}
