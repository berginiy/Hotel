package com.hotel.hotel_management.repository;

import com.hotel.hotel_management.model.Booking;
import com.hotel.hotel_management.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByRoom(Room room);

    List<Booking> findAllByOrderByCheckInDateDesc();

    Optional<Booking> findByRoomAndCheckOutDateIsNull(Room room);
}