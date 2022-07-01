package ru.akzam.WeatherBot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "bot_user")
@NoArgsConstructor
public class User {

    @Id
    @NotNull
    @Column(name = "user_id")
    private long id;

    @Column(name = "location")
    private String location;

    public User(long id) {
        this.id = id;
    }
}
