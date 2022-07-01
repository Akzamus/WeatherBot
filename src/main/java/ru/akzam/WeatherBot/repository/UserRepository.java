package ru.akzam.WeatherBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.akzam.WeatherBot.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
