package com.movie.ticket.booking.system.service.servicesImpl;

import com.movie.ticket.booking.system.commons.dto.BookingDto;
import com.movie.ticket.booking.system.commons.dto.BookingStatus;
import com.movie.ticket.booking.system.service.broker.PaymentServiceBroker;
import com.movie.ticket.booking.system.service.entity.BookingEntity;
import com.movie.ticket.booking.system.service.publisher.BookingServiceKafkaPublisher;
import com.movie.ticket.booking.system.service.repository.BookingRepository;
import com.movie.ticket.booking.system.service.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private PaymentServiceBroker paymentServiceBroker;

    @Autowired
    private BookingServiceKafkaPublisher bookingServiceKafkaPublisher;

    @Autowired
    BookingRepository bookingRepository;

    @Override
    @Transactional
    public BookingDto createBooking(BookingDto bookingDto) {
        BookingEntity bookingEntity=BookingEntity.builder()
                .bookingAmount(bookingDto.getBookingAmount())
                .seatsBooked(bookingDto.getSeatsBooked())
                .bookingStatus(BookingStatus.PENDING)
                .movieId(bookingDto.getMovieId())
                .userId(bookingDto.getUserId())
                .showDate(bookingDto.getShowDate())
                .showTime(bookingDto.getShowTime())
                .build();
        this.bookingRepository.save(bookingEntity); //CREATE A BOOKING WITH STATUS PENDING
        bookingDto.setBookingId(bookingEntity.getBookingId());
        bookingDto.setBookingStatus(BookingStatus.PENDING);
        //PUBLISH THE BOOKING DATA TO KAFKA TOPIC
        this.bookingServiceKafkaPublisher.publishPaymentRequest(bookingDto);

        //CALL FOR PAYMENT SERVICE
       // bookingDto= this.paymentServiceBroker.createPayment(bookingDto);
        // bookingEntity.setBookingStatus(bookingDto.getBookingStatus());
        return bookingDto;

        /*return BookingDto.builder()
                .bookingId(bookingEntity.getBookingId())
                .bookingAmount(bookingEntity.getBookingAmount())
                .bookingStatus(bookingEntity.getBookingStatus())
                .seatsBooked(bookingEntity.getSeatsBooked())
                .movieId(bookingEntity.getMovieId())
                .showTime(bookingEntity.getShowTime())
                .showDate(bookingEntity.getShowDate())
                .userId(bookingEntity.getUserId())
                .build();*/
    }

    @Override
    @Transactional
    public void processBooking(BookingDto bookingDto) {
        Optional<BookingEntity> bookingEntityOptional= this.bookingRepository.findById(bookingDto.getBookingId());
        if(bookingEntityOptional.isPresent()){
            BookingEntity bookingEntity=bookingEntityOptional.get();
            bookingEntity.setBookingStatus(bookingDto.getBookingStatus());
        }
    }
}
