package com.poly.controller;

import com.poly.config.JwtUtil;
import com.poly.service.impl.SendMailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/send-mail")
@CrossOrigin("*")
public class TestSendMailController {
    @Autowired
    private SendMailServiceImp sendMailServiceImp;
    @Autowired
    private JwtUtil jwtUtil;
    private String codeConfirmation;

    @GetMapping("/")
    public ResponseEntity<?> sendCode() {
        ResponseEntity<?> message = null;
        try {
            String code = this.sendMailServiceImp.code();
            this.codeConfirmation = code;
            this.sendMailServiceImp.sendCodeConfirm("hoanghonvanoinhoem@gmail.com", "Hung", code);
        } catch (Exception e) {
            message = ResponseEntity.status(HttpStatus.NOT_FOUND).body("error " + e.getMessage());
        }
        return message;
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> sendMail(@PathVariable("code") String code) {
        if (Objects.equals(this.codeConfirmation, code)) {
            return ResponseEntity.status(HttpStatus.OK).body("Code correct");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("Code incorrect");
        }
    }

    @GetMapping("test/{token}")
    public ResponseEntity<?> testJwt(@PathVariable("token") String token) {
        return ResponseEntity.ok(this.jwtUtil.extractExpiration(token));
    }

    @GetMapping("test")
    public ResponseEntity<?> testJwt2() throws ParseException {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"),
                Locale.getDefault());
        Date currentLocalTime = calendar.getTime();
//        String localTime = date.format(currentLocalTime);

//        String date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(a);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        df.setTimeZone(TimeZone.getTimeZone("GMT+0700"));
//        Date startDate = df.parse(date.replaceAll("\\+00:00$", "GMT+0700"));
        Locale.setDefault(new Locale("vi", "VN"));
        LocaleContextHolder.setLocale(Locale.getDefault());
        long a = (System.currentTimeMillis() + 10 * 24 * 60 * 60 * 1000);
        return ResponseEntity.ok(new Date(a));
    }

    @GetMapping("test1")
    public ResponseEntity<?> testJwt1() {
        return ResponseEntity.ok(new Date(1657639132));
    }
}