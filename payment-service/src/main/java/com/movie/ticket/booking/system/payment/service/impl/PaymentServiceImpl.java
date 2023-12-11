package com.movie.ticket.booking.system.payment.service.impl;

import com.movie.ticket.booking.system.commons.constants.LoggerConstants;
import com.movie.ticket.booking.system.commons.dto.BookingDto;
import com.movie.ticket.booking.system.commons.dto.BookingStatus;
import com.movie.ticket.booking.system.payment.service.dto.PaymentDto;
import com.movie.ticket.booking.system.payment.service.entity.PaymentEntity;
import com.movie.ticket.booking.system.payment.service.entity.PaymentStatus;
import com.movie.ticket.booking.system.payment.service.kafka.PaymentServiceKafkaPublisher;
import com.movie.ticket.booking.system.payment.service.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private StripePaymentGateway stripePaymentGateway;

    @Autowired
    private PaymentServiceKafkaPublisher paymentServiceKafkaPublisher;

   /* @Override
    @Transactional
    public BookingDto createPayment(BookingDto bookingDto) {
        log.info(LoggerConstants.ENTERED_SERVICE_MESSAGE.getValue(),"createPayment",this.getClass());
        PaymentEntity paymentEntity=  PaymentEntity.builder()
                .bookingId(bookingDto.getBookingId())
                .paymentAmount(bookingDto.getBookingAmount())
                .paymentStatus(PaymentStatus.PENDING)
                .build();
        this.paymentRepository.save(paymentEntity);
        //MAKE A CALL TO PAYMENT GATEWAY (STRIPE-PAYMENT-GATEWAY)
         this.stripePaymentGateway.makePayment(bookingDto);

        if(bookingDto.getBookingStatus().equals(BookingStatus.CONFIRMED)){
            paymentEntity.setPaymentStatus(PaymentStatus.APPROVED);
        } else {
            paymentEntity.setPaymentStatus(PaymentStatus.FAILED);
            bookingDto.setBookingStatus(BookingStatus.CANCELLED);
        }

        return bookingDto;
    }*/

    @Override
    @Transactional
    public void processPayment(BookingDto bookingDto) {
        log.info("Received booking details in payment service with data : {}",bookingDto.toString());
        PaymentEntity paymentEntity=  PaymentEntity.builder()
                .bookingId(bookingDto.getBookingId())
                .paymentAmount(bookingDto.getBookingAmount())
                .paymentStatus(PaymentStatus.PENDING)
                .build();
        this.paymentRepository.save(paymentEntity);
        this.stripePaymentGateway.makePayment(bookingDto);
        paymentEntity.setPaymentTimeStamp(LocalDateTime.now());
        if(bookingDto.getBookingStatus().equals(BookingStatus.CONFIRMED)){
            paymentEntity.setPaymentStatus(PaymentStatus.APPROVED);
        } else {
            paymentEntity.setPaymentStatus(PaymentStatus.FAILED);
            bookingDto.setBookingStatus(BookingStatus.CANCELLED);
        }
        this.paymentServiceKafkaPublisher.pushDataToPaymentResponseTopic(bookingDto);
    }
}
