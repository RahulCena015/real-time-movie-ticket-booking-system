package com.movie.ticket.booking.system.service.publisher;

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
public class BookingServiceKafkaPublisher {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void publishPaymentRequest(BookingDto bookingDto){
        log.info("Publishing booking details to the payment-request kafka topic");
        try {
            this.kafkaTemplate.send("payment-request",objectMapper.writeValueAsString(bookingDto));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("Booking data has been published to payment-request topic");
    }
}
