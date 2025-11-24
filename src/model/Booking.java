package model;

import java.util.List;

/**
 * Class untuk menyimpan data pemesanan.
 * @author Kelompok 5
 */
public class Booking {
    private String bookingCode;
    private String userId;
    private String showtimeId;
    private List<String> seats;
    private double totalPrice;

    public Booking(String bookingCode, String userId, String showtimeId, List<String> seats, double totalPrice) {
        this.bookingCode = bookingCode;
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.seats = seats;
        this.totalPrice = totalPrice;
    }

    // Getters
    public String getBookingCode() { return bookingCode; }
    public String getUserId() { return userId; }
    public String getShowtimeId() { return showtimeId; }
    public List<String> getSeats() { return seats; }
    public double getTotalPrice() { return totalPrice; }
}
