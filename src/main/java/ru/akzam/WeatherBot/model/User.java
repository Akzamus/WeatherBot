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
        if(location == null){
            this.location = null;
        } else if (location.length() == 3) {
            this.location = location.toUpperCase();
        } else {
            String[] words = location.split(" ");
            StringBuilder correctLocationFormat = new StringBuilder();
            for (String word : words)
                correctLocationFormat.append(word.substring(0, 1).toUpperCase()).append(word.substring(1).toLowerCase()).append(" ");
            this.location = correctLocationFormat.substring(0,correctLocationFormat.length()-1);
        }
    }
}
