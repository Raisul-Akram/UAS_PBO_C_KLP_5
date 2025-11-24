package repository;

import model.Movie;
import util.FileManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository untuk Movie (CRUD lengkap + persistence).
 * Versi Sempurna.
 * @author 
 */
public class MovieRepository {
    private List<Movie> movies = new ArrayList<>();
    private final String FILE_PATH = "data/movies.txt";

    public MovieRepository() {
        loadFromFile();
    }

