package com.hospital.utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EmailService {
    private final String SENDER_EMAIL = System.getenv("SENDER_EMAIL");
    private final String SENDER_PASS = System.getenv("SENDER_PASS");

    private static final ExecutorService emailExecutor = Executors.newFixedThreadPool(3);


    public void sendMailAsync(String destination, String subject, String text) {
        emailExecutor.submit(() -> {
            try {
                sendMail(destination, subject, text);
            } catch (Exception e) {

                System.err.println("Failed to send async email to " + destination + ": " + e.getMessage());
            }
        });
    }

    public void sendMail(String destination, String subject, String text) {
        Properties props = setProperties();
        Session session = getSession(props);
        Message message = setMessage(session, destination, subject,text);
        sendMessage(message);
    }


    private Properties setProperties(){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        return props;
    }

    private Session getSession(Properties props){
        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASS);
            }
        });
    }


    private Message setMessage(Session session, String destination, String subject, String context){
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destination));
            message.setSubject(subject);
            message.setText(context);
            return message;
        }
         catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    private void sendMessage(Message message){
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    public static void shutdown() {
        System.out.println("Shutting down EmailService thread pool...");
        emailExecutor.shutdown();
        try {

            if (!emailExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("Forcing shutdown of hanging email threads...");
                emailExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            emailExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("EmailService thread pool stopped.");
    }
}
