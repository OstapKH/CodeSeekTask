package com.ostap.Entities;

import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
public class FootballPlayer extends Player {
    private String position;
    private String preferredFoot;

    public FootballPlayer(Integer age, Integer monthsOfExperience, String surname, Integer number, String name, Team team, String position, String preferredFoot) {
        super(age, monthsOfExperience, surname, number, name, team);
        this.position = position;
        this.preferredFoot = preferredFoot;
    }

    public FootballPlayer() {}

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPreferredFoot() {
        return preferredFoot;
    }

    public void setPreferredFoot(String preferredFoot) {
        this.preferredFoot = preferredFoot;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FootballPlayer that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(position, that.position) && Objects.equals(preferredFoot, that.preferredFoot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), position, preferredFoot);
    }
}
