package service;

import model.Booking;
import repository.BookingRepository;
import util.SeatUnavailableException;

import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private BookingRepository bookingRepo;

    public BookingService() {
        this.bookingRepo = new BookingRepository();
    }

    public void createBooking(Booking booking) throws SeatUnavailableException {
        // Sekarang cek kursi per showtime, bukan global
        if (!isSeatsAvailable(booking.getShowtimeId(), booking.getSeats())) {
            throw new SeatUnavailableException("Kursi yang dipilih sudah dipesan.");
        }
        bookingRepo.save(booking);
    }
