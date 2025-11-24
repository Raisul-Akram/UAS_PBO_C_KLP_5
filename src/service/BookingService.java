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

    /**
     * Cek apakah kursi tersedia berdasarkan data booking yang sudah tersimpan,
     * dibatasi per showtime.
     */
    private boolean isSeatsAvailable(String showtimeId, List<String> seats) {
        List<Booking> existingBookings = bookingRepo.findAll();

        // Kumpulkan kursi yang sudah dibooking untuk showtime yang sama
        List<String> bookedSeats = new ArrayList<>();
        for (Booking b : existingBookings) {
            if (b.getShowtimeId().equals(showtimeId)) {
                bookedSeats.addAll(b.getSeats());
            }
        }

        // Cek apakah kursi yang dipilih user sudah terpakai di showtime itu
        for (String seat : seats) {
            if (bookedSeats.contains(seat.trim())) {
                return false;
            }
        }
        return true;
    }

    public List<Booking> getBookingsByUser(String userId) {
        return bookingRepo.findByUser(userId);
    }
}
