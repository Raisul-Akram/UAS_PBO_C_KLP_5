package repository;

import model.Account;
import model.Admin;
import model.User;
import util.FileManager;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private List<Account> users = new ArrayList<>();
    private static final String FILE_PATH = "data/users.txt";

    public UserRepository() {
        loadFromFile();
    }

    private void loadFromFile() {
        List<String> lines = FileManager.getInstance().readFile(FILE_PATH);
        for (String line : lines) {
            if (line == null || line.trim().isEmpty()) continue;
            
            String[] parts = line.split(",");
            if (parts.length < 4) continue;

            String id = parts[0].trim();
            String username = parts[1].trim();
            String password = parts[2].trim();
            String role = parts[3].trim();

            if (role.equalsIgnoreCase("admin")) {
                users.add(new Admin(id, username, password));
            } else {
                users.add(new User(id, username, password));
            }
        }
    }

    // METHOD INI PENTING, JANGAN DIHAPUS
    public List<Account> findAll() {
        return users;
    }

    // INI JUGA PENTING BUAT LOGIN
    public Account findByUsername(String username) {
        for (Account u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }
    
    // JAGA-JAGA KALAU AdminMenu MINTA method getUsers()
    public List<Account> getUsers() {
        return users;
    }
}
