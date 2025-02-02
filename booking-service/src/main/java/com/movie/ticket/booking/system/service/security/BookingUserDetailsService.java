package com.movie.ticket.booking.system.service.security;


import com.movie.ticket.booking.system.service.entity.BookingEntity;
import com.movie.ticket.booking.system.service.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

public class BookingUserDetailsService implements UserDetailsService {

    @Autowired
    private BookingRepository bookingRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<BookingEntity> bookingDetailsByUsername= bookingRepository.findByUserName(username);
        return bookingDetailsByUsername.map(BookingUserDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with given username :" +username));
    }
}
