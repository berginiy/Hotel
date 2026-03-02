package com.hotel.hotel_management.service;

import com.hotel.hotel_management.model.Booking;
import com.hotel.hotel_management.model.Room;
import com.hotel.hotel_management.repository.BookingRepository;
import com.hotel.hotel_management.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public List<Room> getAvailableRooms() {
        return roomRepository.findByStatus(Room.RoomStatus.AVAILABLE);
    }

    public List<Room> getOccupiedRooms() {
        return roomRepository.findByStatus(Room.RoomStatus.OCCUPIED);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAllByOrderByCheckInDateDesc();
    }

    @Transactional
    public void checkIn(Long roomId, String clientName, Integer days) {

        if (days == null || days <= 0) {
            throw new IllegalArgumentException("Количество суток должно быть больше 0");
        }
        if (clientName == null || clientName.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя клиента обязательно");
        }

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Номер не найден: " + roomId));

        if (room.getStatus() != Room.RoomStatus.AVAILABLE) {
            throw new IllegalStateException("Номер уже занят");
        }

        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setClientName(clientName.trim());
        booking.setDays(days);

        BigDecimal price = room.getPricePerDay()
                .multiply(BigDecimal.valueOf(days))
                .setScale(2, RoundingMode.HALF_UP);

        booking.setTotalPrice(price);

        bookingRepository.save(booking);

        room.setStatus(Room.RoomStatus.OCCUPIED);
        roomRepository.save(room);
    }

    public Optional<Booking> getActiveBookingByRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Номер не найден: " + roomId));

        return bookingRepository.findByRoomAndCheckOutDateIsNull(room);
    }

    @Transactional
    public BigDecimal checkOut(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Номер не найден: " + roomId));

        Booking booking = bookingRepository.findByRoomAndCheckOutDateIsNull(room)
                .orElseThrow(() -> new IllegalStateException("Активное заселение в номере не найдено"));

        booking.setCheckOutDate(LocalDateTime.now());
        bookingRepository.save(booking);

        room.setStatus(Room.RoomStatus.AVAILABLE);
        roomRepository.save(room);

        return booking.getTotalPrice();
    }
}