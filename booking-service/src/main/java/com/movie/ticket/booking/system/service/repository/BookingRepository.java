package com.movie.ticket.booking.system.service.repository;

import com.movie.ticket.booking.system.commons.dto.BookingDto;
import com.movie.ticket.booking.system.service.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    @Query(value = "SELECT booking_status FROM Booking_Table where booking_id =:id ",nativeQuery = true)
    String findByBookingId(@Param("id") Long id);
}
