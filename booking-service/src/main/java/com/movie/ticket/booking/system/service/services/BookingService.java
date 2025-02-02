package com.movie.ticket.booking.system.service.services;

import com.movie.ticket.booking.system.commons.dto.BookingDto;
import com.movie.ticket.booking.system.service.entity.BookingEntity;

import java.util.Optional;

public interface BookingService {

    BookingDto createBooking(BookingDto bookingDto);

    public void processBooking(BookingDto bookingDto);

    BookingDto findByBookingId(Long id);

    Optional<BookingDto> findByUserName(String userName);
}
