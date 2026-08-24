package com.example.customerpdfemailservice.service;

import com.example.customerpdfemailservice.entity.Customer;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendCustomerPdf(Customer customer, byte[] pdfBytes) {

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            // Recipient
            helper.setTo(customer.getEmail());

            // Subject
            helper.setSubject(
                    "Customer Profile - " + customer.getName()
            );

            // Email body
            String body =
                    "Dear " + customer.getName() + ",\n\n"
                            + "Please find attached your Customer Profile document.\n\n"
                            + "Customer ID: " + customer.getId() + "\n"
                            + "CNIC: " + customer.getCnic() + "\n\n"
                            + "This document was generated automatically "
                            + "by Customer PDF & Email Service.\n\n"
                            + "Regards,\n"
                            + "Customer PDF & Email Service";

            helper.setText(body);

            // Attach generated PDF
            helper.addAttachment(
                    "Customer_Profile_"
                            + customer.getId()
                            + ".pdf",
                    new ByteArrayResource(pdfBytes)
            );

            // Send
            mailSender.send(message);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to send customer email",
                    e
            );
        }
    }
}