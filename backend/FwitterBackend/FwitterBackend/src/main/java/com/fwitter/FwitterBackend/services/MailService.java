package com.fwitter.FwitterBackend.services;

import com.fwitter.FwitterBackend.exceptions.EmailFailedToSendException;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.util.Properties;

@RequiredArgsConstructor
@Service
public class MailService {

    private final Gmail gmail;


    public void sendEmail(String toAddress, String subject, String content) {
        Properties props = new Properties();

        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        try {
            email.setFrom(new InternetAddress("ram063527@gmail.com"));
            email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(toAddress));
            email.setSubject(subject);
            email.setText(content);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            email.writeTo(buffer);

            byte[] rawMessageBytes = buffer.toByteArray();

            String encodedEmail = Base64.encodeBase64String(rawMessageBytes);

            Message message = new Message();
            message.setRaw(encodedEmail);

            message = gmail.users().messages().send("me",message).execute();


        } catch (Exception e) {
          throw new EmailFailedToSendException();
        }
    }

}
