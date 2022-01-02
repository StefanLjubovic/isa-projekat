package com.backend.service;

import com.backend.email_feedback.Mail;
import com.backend.model.Client;
import com.backend.model.RentingEntity;
import com.backend.model.Reservation;
import com.backend.model.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

@Service
public class MailService{
    @Autowired
    JavaMailSender mailSender;

    @Async
    public void sendEmail(VerificationToken verificationToken, String email) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        Mail mail=new Mail();
        mail.setMailFrom("fishingbooker.isa@gmail.com");
        mail.setMailTo(email);
        mail.setMailSubject("Confirm your account");
        mail.setMailContent("To confirm your account, please click here : "
                +"http://localhost:8082/auth/confirm-account?token="+verificationToken.getToken());
        SendMail(mimeMessage, mail);
    }

    public void sendReservationMail(Reservation reservation){
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        Mail mail=new Mail();
        mail.setMailFrom("fishingbooker.isa@gmail.com");
        mail.setMailTo(reservation.getClient().getEmail());
        mail.setMailSubject("Reservation created successfully!");
        mail.setMailContent("You have created reservation for :"+ simpleDateFormat.format(reservation.getDateTime()));
        SendMail(mimeMessage, mail);
    }


    public void sendActionMail(RentingEntity entity, Client client){
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        Mail mail=new Mail();
        mail.setMailFrom("fishingbooker.isa@gmail.com");
        mail.setMailTo(client.getEmail());
        mail.setMailSubject("Entity "+entity.getName() + " has new action!");
        SendMail(mimeMessage, mail);
    }

    private void SendMail(MimeMessage mimeMessage, Mail mail) {
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom(), "Fishing booker"));
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setText(mail.getMailContent());

            mailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
