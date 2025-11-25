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

    /**
     * Membuat booking baru.
     */
    public void createBooking(Booking booking) throws SeatUnavailableException {
        // Cek lagi ketersediaan kursi untuk keamanan ganda
        if (!isSeatsAvailable(booking.getShowtimeId(), booking.getSeats())) {
            throw new SeatUnavailableException("Kursi yang dipilih sudah dipesan orang lain.");
        }
        bookingRepo.save(booking);
    }

    /**
     * Cek ketersediaan kursi (untuk Validasi Input User).
     */
    public boolean isSeatsAvailable(String showtimeId, List<String> seats) {
        List<String> bookedSeats = getBookedSeats(showtimeId); // Pakai method helper di bawah biar rapi

        for (String seat : seats) {
            // Gunakan trim() dan equalsIgnoreCase() biar lebih robust
            // Contoh: user input "a1 ", di data "A1" -> Tetap terdeteksi
            boolean found = false;
            for (String booked : bookedSeats) {
                if (booked.trim().equalsIgnoreCase(seat.trim())) {
                    found = true;
                    break;
                }
            }
            
            if (found) {
                return false; // Kursi sudah ada yang punya
            }
        }
        return true; // Semua kursi aman
    }

    /**
     * Mengambil riwayat booking user tertentu.
     */
    public List<Booking> getBookingsByUser(String userId) {
        List<Booking> allBookings = bookingRepo.findAll();
        List<Booking> userBookings = new ArrayList<>();
        
        for (Booking b : allBookings) {
            if (b.getUserId().equals(userId)) {
                userBookings.add(b);
            }
        }
        return userBookings;
    }

    /**
     * METHOD BARU: Mengambil daftar semua kursi yang sudah laku untuk Showtime tertentu.
     * Dipanggil oleh UserMenu untuk menggambar Peta Kursi (Seat Map).
     */
    public List<String> getBookedSeats(String showtimeId) {
        List<Booking> existingBookings = bookingRepo.findAll();
        List<String> bookedSeats = new ArrayList<>();

        for (Booking b : existingBookings) {
            if (b.getShowtimeId().equals(showtimeId)) {
                // Normalisasi: Simpan semua dalam UpperCase & Trim (misal "A1")
                for (String seat : b.getSeats()) {
                    bookedSeats.add(seat.trim().toUpperCase());
                }
            }
        }
        return bookedSeats;
    }
}
