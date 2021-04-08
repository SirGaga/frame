package com.feityz.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class EmailConfig {

    @Async
    public void sendEmail(String subject,String text,String toUser){
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.host", "yzmail.feg.cn");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.ssl.trust", "yzmail.feg.cn");
        Authenticator auth = new MyAuthenticator();
        Session smtpSession = Session.getInstance(props, auth);
        //smtpSession.setDebug(true);

        MimeMessage message = new MimeMessage(smtpSession);
        try {
            message.setFrom(new InternetAddress("noticeserver.feityz@yz.feg.cn"));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toUser));
            final String encoding = "UTF-8";

            message.setSubject(subject, encoding);
            message.setText(text, encoding);
            Transport.send(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public class MyAuthenticator extends Authenticator {

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            String username = "noticeserver.feityz@yz.feg.cn";
            String password = "abc.2016";
            return new PasswordAuthentication(username, password);

        }
    }
}
