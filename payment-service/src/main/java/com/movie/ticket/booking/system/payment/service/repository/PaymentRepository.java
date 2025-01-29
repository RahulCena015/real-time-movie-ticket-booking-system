package com.movie.ticket.booking.system.payment.service.repository;

import com.movie.ticket.booking.system.payment.service.entity.PaymentEntity;
import org.springframework.data.repository.CrudRepository;


public interface PaymentRepository extends CrudRepository<PaymentEntity, Long> {
}
