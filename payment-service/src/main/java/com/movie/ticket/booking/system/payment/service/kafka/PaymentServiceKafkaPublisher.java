package com.movie.ticket.booking.system.payment.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.movie.ticket.booking.system.commons.dto.BookingDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentServiceKafkaPublisher {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;


    public void pushDataToPaymentResponseTopic(BookingDto bookingDto){
        log.info("Publishing booking details to payment-response topic");
        try {
            this.kafkaTemplate.send("payment-response",objectMapper.writeValueAsString(bookingDto));
        } catch (JsonProcessingException e) {
            log.error("Error while publishing booking details to payment-response topic");
        }
    }
}
