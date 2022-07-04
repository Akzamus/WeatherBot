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

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    public User(long id) {
        this.id = id;
    }

    public void setLocation(String location) {
        if(location == null)
            this.location = null;
        else if(location.equalsIgnoreCase("USA"))
            this.location = location.toUpperCase();
        else
            this.location = location.substring(0,1).toUpperCase() +
                            location.substring(1).toLowerCase();
    }
}
