package com.movie.ticket.booking.system.service.broker;

import com.movie.ticket.booking.system.commons.dto.BookingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient (name = "payment-service")
public interface PaymentServiceBroker {

    @GetMapping ("/payments")
    public String test ();


    @PostMapping("/payments")
    public BookingDto createPayment(@RequestBody BookingDto bookingDto);
}
