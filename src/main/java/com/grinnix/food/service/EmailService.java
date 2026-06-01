package com.grinnix.food.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

  @Autowired
  private final JavaMailSender mailSender;

  public void sendEmail(String to, String subject, String emailBody) {
    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

      helper.setTo(to);
      helper.setSubject(subject);
      helper.setFrom("noreply@grinnix-food.com");
      helper.setText(emailBody, false);

      mailSender.send(message);
    } catch (Exception e) {
      log.error("Erro ao enviar email para={}", to, e);
      e.printStackTrace();
    }
  }
}
