package com.movie.ticket.booking.system.service.services;

import com.movie.ticket.booking.system.commons.dto.BookingDto;

public interface BookingService {

    BookingDto createBooking(BookingDto bookingDto);

    public void processBooking(BookingDto bookingDto);
}
