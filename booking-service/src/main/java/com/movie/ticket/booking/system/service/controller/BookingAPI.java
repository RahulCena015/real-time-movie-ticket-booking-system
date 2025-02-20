package com.movie.ticket.booking.system.service.controller;

import com.movie.ticket.booking.system.commons.constants.LoggerConstants;
import com.movie.ticket.booking.system.commons.dto.BookingDto;
import com.movie.ticket.booking.system.service.entity.BookingEntity;
import com.movie.ticket.booking.system.service.services.BookingService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/bookings")
@Slf4j
public class BookingAPI {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public BookingDto createBooking(@Valid @RequestBody BookingDto bookingDto){
        log.info(LoggerConstants.ENTERED_CONTROLLER_MESSAGE.getValue(),"createBooking",this.getClass(),bookingDto.toString());
        return this.bookingService.createBooking(bookingDto);

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/getstatus/{id}")
    public BookingDto getBookingStatusByBookingId(@PathVariable Long id){
        return bookingService.findByBookingId(id);
    }
}
