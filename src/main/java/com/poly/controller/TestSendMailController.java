package com.poly.controller;

import com.poly.service.impl.SendMailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/send-mail")
@CrossOrigin("*")
public class TestSendMailController {
    @Autowired
    private SendMailServiceImp sendMailServiceImp;

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
}
