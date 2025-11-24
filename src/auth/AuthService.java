package auth;

import model.Account;
import model.Admin;
import model.User;
import repository.UserRepository;

public class AuthService {

    private UserRepository userRepo;

    public AuthService() {
        this.userRepo = new UserRepository();
    }

    public Account login(String username, String password) throws LoginFailedException {
        Account acc = userRepo.findByUsername(username);

        if (acc == null)
            throw new LoginFailedException("Username tidak ditemukan.");

        if (!acc.getPassword().equals(password))
            throw new LoginFailedException("Password salah.");

        return acc; // return User atau Admin sesuai data
    }
}
