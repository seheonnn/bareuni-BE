package com.umc.BareuniBE.repository;

import com.umc.BareuniBE.entities.Booking;
import com.umc.BareuniBE.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query(
            value =
                    "SELECT b.bookingIdx, b.createdAt, b.updatedAt, b.user.userIdx, b.hospital, b.method, b.bookingDate " +
                            "FROM Booking b " +
                            "WHERE b.user = :user " +
                            "ORDER BY b.bookingDate ASC"
    )
    List<Object[]> MyBookingList(@Param("user") User user, Pageable pageable);

    void deleteAllByUser(User user);
}
