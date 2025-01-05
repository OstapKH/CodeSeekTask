package com.ostap.Entities;
import jakarta.persistence.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected String name;
    protected String surname;
    protected Integer number;
    protected Integer age;
    protected Integer monthsOfExperience;

    @JsonIgnoreProperties({"players", "commissionRate", "balance", "name"})
    @ManyToOne
    @JoinColumn(name = "team_id")
    protected Team team;

    public Player(Integer age, Integer monthsOfExperience, String surname, Integer number, String name, Team team) {
        this.age = age;
        this.monthsOfExperience = monthsOfExperience;
        this.surname = surname;
        this.number = number;
        this.name = name;
        this.team = team;
    }

    public Player() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getMonthsOfExperience() {
        return monthsOfExperience;
    }

    public void setMonthsOfExperience(Integer monthsOfExperience) {
        this.monthsOfExperience = monthsOfExperience;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Player player)) return false;
        return Objects.equals(id, player.id) && Objects.equals(name, player.name) && Objects.equals(surname, player.surname) && Objects.equals(number, player.number) && Objects.equals(age, player.age) && Objects.equals(monthsOfExperience, player.monthsOfExperience) && Objects.equals(team, player.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, number, age, monthsOfExperience, team);
    }
}

