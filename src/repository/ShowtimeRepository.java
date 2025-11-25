package repository;

import model.Showtime;
import util.FileManager;

import java.util.ArrayList;
import java.util.List;

public class ShowtimeRepository {
    private List<Showtime> showtimes = new ArrayList<>();
    private final String FILE_PATH = "data/showtimes.txt";

    public ShowtimeRepository() {
        loadFromFile();
    }

    public void save(Showtime showtime) {
        showtimes.add(showtime);
        saveToFile();
    }

    public List<Showtime> findAll() {
        return showtimes;
    }

    public Showtime findById(String id) {
        for (Showtime st : showtimes) {
            if (st.getId().equals(id)) {
                return st;
            }
        }
        return null;
    }

    public boolean update(Showtime updated) {
        for (int i = 0; i < showtimes.size(); i++) {
            if (showtimes.get(i).getId().equals(updated.getId())) {
                showtimes.set(i, updated);
                saveToFile();
                return true;
            }
        }
        return false; 
    }

    public boolean delete(String id) {
        for (int i = 0; i < showtimes.size(); i++) {
            if (showtimes.get(i).getId().equals(id)) {
                showtimes.remove(i);
                saveToFile();
                return true;
            }
        }
        return false; 
    }

    // --- BAGIAN PENTING YANG DIPERBAIKI ---
    private void loadFromFile() {
        List<String> lines = FileManager.getInstance().readFile(FILE_PATH);
        showtimes.clear();

        for (String line : lines) {
            if (line == null || line.trim().isEmpty()) continue;
            
            String[] parts = line.split(",");
            
            // KITA UBAH JADI >= 4 (BUKAN 5)
            if (parts.length >= 4) {
                try {
                    String id = parts[0];
                    String movieId = parts[1];
                    String time = parts[2];
                    
                    // HARGA ADA DI INDEX 3 (BUKAN 4)
                    double price = Double.parseDouble(parts[3]); 

                    // Kita kasih tanggal dummy "-" karena di file txt gak ada tanggal
                    // Asumsi Constructor: Showtime(id, movieId, time, date, price)
                    // Kalau constructor kamu beda, sesuaikan urutannya di sini.
                    showtimes.add(new Showtime(id, movieId, time, "-", price));
                    
                } catch (Exception e) {
                    System.out.println("Skip error line: " + line);
                }
            }
        }
    }

    private void saveToFile() {
        List<String> lines = new ArrayList<>();
        for (Showtime st : showtimes) {
            // Simpan 4 kolom saja agar konsisten
            lines.add(st.getId() + "," + st.getMovieId() + "," + st.getTime() + "," + st.getPrice());
        }
        FileManager.getInstance().writeFile(FILE_PATH, lines);
    }
}
