package tech.xserver.adminserver.service;

import tech.xserver.adminserver.model.Mail;

public interface EmailService {
    void sendEmail(Mail emailMessage);
}
