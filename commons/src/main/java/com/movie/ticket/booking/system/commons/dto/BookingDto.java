package com.movie.ticket.booking.system.commons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDto {

    private Long bookingId;
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
    private Double bookingAmount;
    @NotNull(message = "email-id is Mandatory field, please enter the correct email id")
    private String emailId;
    @NotNull(message = "seat type is Mandatory field, please enter the seat type from Gold, Silver and Platanium")
    private String seatType;
    private Long numberOfSeats;
}
