package com.movie.ticket.booking.system.notification.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.ticket.booking.system.commons.dto.BookingDto;
import com.movie.ticket.booking.system.notification.service.mailconfig.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceKafkaListener {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmailService emailService;


    @KafkaListener(topics = "payment-response",groupId = "paymentresponsenotification")
    public void receiveFromPaymentResponseTopic(String bookingDtoJson){
        log.info("Received confirmation on booking details from payment-response kafka topic");

        try {
            log.info("Inside try block of notification");
            BookingDto bookingDto=objectMapper.readValue(bookingDtoJson, BookingDto.class);
            log.info("Booking Confirmation Response: {} ",bookingDto.toString());
            //SEND EMAIL WITH BOOKING DETAILS
           sendVerificationEmail(bookingDto.getEmailId(), String.valueOf(bookingDto.getBookingStatus()));
            log.info("Mail sent successfully");

        } catch (JsonProcessingException e) {
            log.error("Error:"+e.getMessage());
          log.error("Error while receiving confirmation on booking from payment-response kafka topic");
        }

    }

    private void sendVerificationEmail(String email, String status) {
        String subject = "Your Booking confirmation status";
        String body = "Your Booking status: " + status;
        emailService.sendEmail(subject, body, email);

    }
}
