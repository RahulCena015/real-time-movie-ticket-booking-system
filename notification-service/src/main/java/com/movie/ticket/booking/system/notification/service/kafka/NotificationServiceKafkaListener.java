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
           sendVerificationEmail(bookingDto, bookingDto.getEmailId());
            log.info("Mail sent successfully");

        } catch (JsonProcessingException e) {
            log.error("Error:"+e.getMessage());
          log.error("Error while receiving confirmation on booking from payment-response kafka topic");
        }

    }

    private void sendVerificationEmail(BookingDto bookingResponse , String email) {
        String subject = "Your Booking confirmation status";
        //String body = "Your Booking status: " + status;
        String body = "<html><body>" +
                "<h2>Your booking has been confirmed with the following details:</h2>" +
                "<ul>" +
                "<li><strong>Booking ID:</strong> " + bookingResponse.getBookingId() + "</li>" +
                "<li><strong>Seats Booked:</strong> " + String.join(", ", bookingResponse.getSeatsBooked()) + "</li>" +
                "<li><strong>Show Date:</strong> " + bookingResponse.getShowDate() + "</li>" +
                "<li><strong>Show Time:</strong> " + bookingResponse.getShowTime() + "</li>" +
                "<li><strong>Booking Status:</strong> " + bookingResponse.getBookingStatus() + "</li>" +
                "<li><strong>Booking Amount:</strong> " + bookingResponse.getBookingAmount() + "</li>" +
                "</ul>" +
                "<p>Thank you for your booking!</p>" +
                "<p>Best Regards,<br>Your Movie Booking Team</p>" +
                "</body></html>";

        emailService.sendEmail(subject, body, email);

    }
}
