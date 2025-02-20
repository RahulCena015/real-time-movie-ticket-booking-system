package com.movie.ticket.booking.system.service.entity;

import com.movie.ticket.booking.system.commons.dto.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Booking_Table")
public class BookingEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="booking_id")
    private Long bookingId;
    @Column(name="user_id")
    private String userId;
    @Column(name="movie_id")
    private Integer movieId;
    @ElementCollection
    private List<String> seatsBooked;
    @Column(name="show_date")
    private LocalDate showDate;
    @Column(name="show_time")
    private LocalTime showTime;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    @Column(name="booking_amount")
    private Double bookingAmount;
    @Column(name="seat_type")
    private String seatType;
    @Column(name="number_of_seats")
    private Long numberOfSeats;

    @Column(name="username")
    private String userName;
    @Column(name="password")
    private String password;
    @Column(name="user_roles")
    private String roles;
}
