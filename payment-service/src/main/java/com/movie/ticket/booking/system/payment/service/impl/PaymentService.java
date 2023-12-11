package com.movie.ticket.booking.system.payment.service.impl;

import com.movie.ticket.booking.system.commons.dto.BookingDto;
import org.springframework.web.bind.annotation.RequestBody;

public interface PaymentService {

    //public BookingDto createPayment(BookingDto bookingDto);

    public void processPayment(BookingDto bookingDto);
}
