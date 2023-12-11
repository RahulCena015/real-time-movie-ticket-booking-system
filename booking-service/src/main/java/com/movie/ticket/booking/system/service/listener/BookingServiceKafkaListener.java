package com.movie.ticket.booking.system.service.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.movie.ticket.booking.system.commons.dto.BookingDto;
import com.movie.ticket.booking.system.service.services.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookingServiceKafkaListener {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "payment-response",groupId = "paymentresponse1")
    public void receiveFromPaymentResponseTopic(String bookingDtoJson){
        log.info("Received Confirmation on booking details from payment-response kafka topic");
        try {
            BookingDto bookingDto=objectMapper.readValue(bookingDtoJson, BookingDto.class);
            this.bookingService.processBooking(bookingDto);
        } catch (JsonProcessingException e) {
            log.error("Error while receiving confirmatation on booking from payment-response kafka topic");
        }
    }
}
