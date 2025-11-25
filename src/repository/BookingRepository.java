package repository;

import model.Booking;
import util.FileManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository untuk Booking (Repository Pattern).
 * @author Kelompok 5
 */
public class BookingRepository {
    private List<Booking> bookings = new ArrayList<>();
    private static final String FILE_PATH = "data/bookings.txt";

    public BookingRepository() {
        loadFromFile();
    }

    public void save(Booking booking) {
        bookings.add(booking);
        saveToFile();
    }

    public List<Booking> findAll() {
        return new ArrayList<>(bookings);
    }

    public List<Booking> findByUser(String userId) {
        return bookings.stream()
                .filter(b -> b.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    private void loadFromFile() {
        List<String> lines = FileManager.getInstance().readFile(FILE_PATH);
        bookings.clear(); // Bersihkan list sebelum load ulang
        
        for (String line : lines) {
            // PENGAMAN 1: Lewati baris kosong
            if (line == null || line.trim().isEmpty()) continue;
            
            try {
                String[] parts = line.split(",");
                
                // PENGAMAN 2: Pastikan format data lengkap (minimal 5 kolom)
                if (parts.length < 5) {
                    System.out.println("Skip data rusak: " + line);
                    continue; 
                }

                String bookingCode = parts[0];
                String userId = parts[1];
                String showtimeId = parts[2];
                
                // Logika pembacaan kursi yang aman (dipisah titik koma ';')
                String seatsRaw = parts[3];
                List<String> seats = new ArrayList<>();
                if (!seatsRaw.isEmpty()) {
                    String[] seatArr = seatsRaw.split(";");
                    for (String s : seatArr) seats.add(s);
                }
                
                double price = Double.parseDouble(parts[4]);
                
                bookings.add(new Booking(bookingCode, userId, showtimeId, seats, price));
                
            } catch (Exception e) {
                // PENGAMAN 3: Tangkap error apapun per baris
                System.out.println("Gagal load baris: " + line + " -> " + e.getMessage());
            }
        }
    }

    private void saveToFile() {
        List<String> lines = new ArrayList<>();
        for (Booking b : bookings) {
            // Pisahkan kursi dengan titik koma (;) agar tidak bentrok dengan koma pemisah kolom
            String seatsStr = String.join(";", b.getSeats());
            
            String line = String.join(",", 
                b.getBookingCode(), 
                b.getUserId(), 
                b.getShowtimeId(), 
                seatsStr, 
                String.valueOf(b.getTotalPrice())
            );
            lines.add(line);
        }
        FileManager.getInstance().writeFile(FILE_PATH, lines);
    }
}
