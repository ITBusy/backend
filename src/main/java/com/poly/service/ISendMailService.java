package com.poly.service;

import com.poly.dto.SendMailOrderDto;

import javax.mail.MessagingException;

public interface ISendMailService {
    String code();

    void sendCodeConfirm(String toMail, String name, String codeConfirmation);

    void sendMailOrder(SendMailOrderDto sendMailOrderDto) throws MessagingException;
}
