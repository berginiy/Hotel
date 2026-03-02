package com.hotel.hotel_management.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Column(name = "days", nullable = false)
    private Integer days;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "check_in_date", nullable = false)
    private LocalDateTime checkInDate;

    @Column(name = "check_out_date")
    private LocalDateTime checkOutDate;

    @PrePersist
    protected void onCreate() {
        checkInDate = LocalDateTime.now();
    }
}