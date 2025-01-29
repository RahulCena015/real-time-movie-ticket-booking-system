package com.movie.ticket.booking.system.payment.service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="payment_table")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="payment_id")
    private Long paymentId;
    @Column(name="booking_id")
    private Long bookingId;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Column(name="payment_amount")
    private Double paymentAmount;
    @Column(name="payment_time")
    private LocalDateTime paymentTimeStamp;
}
