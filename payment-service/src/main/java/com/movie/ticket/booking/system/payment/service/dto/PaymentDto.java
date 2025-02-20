package com.movie.ticket.booking.system.payment.service.dto;

import com.movie.ticket.booking.system.payment.service.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private Long bookingId;
    private PaymentStatus paymentStatus;
    private Double paymentAmount;
}
