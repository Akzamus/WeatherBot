package ru.akzam.WeatherBot.servisec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akzam.WeatherBot.model.User;
import ru.akzam.WeatherBot.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UsersService {

    private final UserRepository userRepository;

    @Autowired
    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public void save(User newUser) {
        userRepository.save(newUser);
    }

    @Transactional
    public void addLocationFromUser(String location, long id) {
        userRepository.findById(id).get().setLocation(location);
    }
}
