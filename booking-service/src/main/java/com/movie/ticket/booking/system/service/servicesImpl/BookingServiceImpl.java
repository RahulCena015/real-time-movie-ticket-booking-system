package com.movie.ticket.booking.system.service.servicesImpl;

import com.movie.ticket.booking.system.commons.dto.BookingDto;
import com.movie.ticket.booking.system.commons.dto.BookingStatus;
import com.movie.ticket.booking.system.service.broker.PaymentServiceBroker;
import com.movie.ticket.booking.system.service.entity.BookingEntity;
import com.movie.ticket.booking.system.service.publisher.BookingServiceKafkaPublisher;
import com.movie.ticket.booking.system.service.repository.BookingRepository;
import com.movie.ticket.booking.system.service.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    public static final String ROLE_USER = "ROLE_USER";
    @Autowired
    private PaymentServiceBroker paymentServiceBroker;

    @Autowired
    private BookingServiceKafkaPublisher bookingServiceKafkaPublisher;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public BookingDto createBooking(BookingDto bookingDto) {
        Double totalAmount = calculateAmountOnTheBasisOfSeatType(bookingDto);
        BookingEntity bookingEntity=BookingEntity.builder()
                .bookingAmount(totalAmount)
                .seatsBooked(bookingDto.getSeatsBooked())
                .bookingStatus(BookingStatus.PENDING)
                .movieId(bookingDto.getMovieId())
                .userId(bookingDto.getUserId())
                .showDate(bookingDto.getShowDate())
                .showTime(bookingDto.getShowTime())
                .numberOfSeats(bookingDto.getNumberOfSeats())
                .seatType(bookingDto.getSeatType())
                .userName(bookingDto.getUserName())
                .password(passwordEncoder.encode(bookingDto.getPassword()))
                .roles(ROLE_USER)
                .build();
        this.bookingRepository.save(bookingEntity);
        bookingDto.setBookingId(bookingEntity.getBookingId());
        bookingDto.setBookingStatus(BookingStatus.PENDING);
        bookingDto.setBookingAmount(totalAmount);
        this.bookingServiceKafkaPublisher.publishPaymentRequest(bookingDto);
        return bookingDto;
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

    @Override
    public BookingDto findByBookingId(Long id) {
        BookingEntity byBookingId = bookingRepository.findByBookingId(id);
        BookingDto bookingDto=new BookingDto();
        bookingDto.setBookingId(byBookingId.getBookingId());
        bookingDto.setBookingStatus(byBookingId.getBookingStatus());
        bookingDto.setBookingAmount(byBookingId.getBookingAmount());
        bookingDto.setSeatsBooked(byBookingId.getSeatsBooked());
        bookingDto.setShowDate(byBookingId.getShowDate());
        bookingDto.setShowTime(byBookingId.getShowTime());
        return bookingDto;
    }

    //Method kept on hold TODO
    @Override
    public Optional<BookingDto> findByUserName(String userName) {
        BookingEntity findByUserName = bookingRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with given username :" + userName));
        BookingDto bookingDto=new BookingDto();
        bookingDto.setUserName(findByUserName.getUserName());
        return null;
    }

    private static Double calculateAmountOnTheBasisOfSeatType(BookingDto bookingDto){
        int pricePerSeat = 0;
        switch (bookingDto.getSeatType().toLowerCase()) {
            case "gold":
                pricePerSeat = 300;
                break;
            case "silver":
                pricePerSeat = 200;
                break;
            case "platinum":
                pricePerSeat = 100;
                break;
            default:
                System.out.println("Invalid seat type.");
                return (double) 0L;
        }
        long numberOfSeats = bookingDto.getSeatsBooked().size();
        return (double) (pricePerSeat * numberOfSeats);
    }

}
