package com.movie.ticket.booking.system.commons.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class BookingDto {

    private UUID bookingId;
    @NotBlank(message = "user id is Mandatory field, please enter the correct value")
    private String userId;
    @NotNull(message = "movie id is Mandatory field, please enter the correct value")
    private Integer movieId;
    @NotNull(message = "Please select the seats, it's a mandatory field")
    private List<String> seatsBooked;
    @NotNull(message = "show date is Mandatory field, please enter the correct value")
    private LocalDate showDate;
    @NotNull(message = "show time is Mandatory field, please enter the correct value")
    private LocalTime showTime;
    private BookingStatus bookingStatus;
    @NotNull(message = "booking amount is Mandatory field, please enter the correct value")
    private Double bookingAmount;
    @NotNull(message = "email-id is Mandatory field, please enter the correct email id")
    private String emailId;
}
