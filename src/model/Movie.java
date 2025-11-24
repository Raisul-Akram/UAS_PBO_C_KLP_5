package model;

/**
 * Class untuk menyimpan data film.
 * @author Kelompok 5
 */
public class Movie {
    private String id;
    private String title;
    private String genre;
    private int popularity; // Jumlah pemesan

    public Movie(String id, String title, String genre) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.popularity = 0;
    }
