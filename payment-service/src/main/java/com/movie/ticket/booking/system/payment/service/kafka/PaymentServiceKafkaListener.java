package com.movie.ticket.booking.system.payment.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.movie.ticket.booking.system.commons.dto.BookingDto;
import com.movie.ticket.booking.system.payment.service.impl.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentServiceKafkaListener {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "payment-request", groupId = "paymentrequest")
    public void pullFromPaymentRequestTopic(String bookingDtoJson){
        log.info("Recieved booking details to the payment-request kafka topic in the payment-service");

        try {
            BookingDto bookingDto = objectMapper.readValue(bookingDtoJson, BookingDto.class);
            this.paymentService.processPayment(bookingDto);
        } catch (JsonProcessingException e) {
            log.error("Error while recieving booking details to the payment-request kafka topic in the payment-service");
        }
    }
}
