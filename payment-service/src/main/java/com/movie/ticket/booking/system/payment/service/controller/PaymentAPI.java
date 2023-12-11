package com.movie.ticket.booking.system.payment.service.controller;

import com.movie.ticket.booking.system.commons.constants.LoggerConstants;
import com.movie.ticket.booking.system.commons.dto.BookingDto;
import com.movie.ticket.booking.system.payment.service.dto.PaymentDto;
import com.movie.ticket.booking.system.payment.service.impl.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/payments")
@Slf4j
public class PaymentAPI {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public String test () {
        return "Payment Successfull";
    }


    /*@PostMapping
    public BookingDto createPayment(@RequestBody BookingDto bookingDto){
        log.info(LoggerConstants.ENTERED_CONTROLLER_MESSAGE.getValue(),"createPayment",this.getClass(),bookingDto.toString());
        return this.paymentService.createPayment(bookingDto);
    }*/
}
