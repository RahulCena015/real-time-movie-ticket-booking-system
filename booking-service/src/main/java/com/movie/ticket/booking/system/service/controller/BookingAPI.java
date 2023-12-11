package com.movie.ticket.booking.system.service.controller;

import com.movie.ticket.booking.system.commons.constants.LoggerConstants;
import com.movie.ticket.booking.system.commons.dto.BookingDto;
import com.movie.ticket.booking.system.service.services.BookingService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/bookings")
@Slf4j
public class BookingAPI {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public BookingDto createBooking(@Valid @RequestBody BookingDto bookingDto){
        log.info(LoggerConstants.ENTERED_CONTROLLER_MESSAGE.getValue(),"createBooking",this.getClass(),bookingDto.toString());
        return this.bookingService.createBooking(bookingDto);

    }
}
