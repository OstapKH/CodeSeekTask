package com.ostap.config;

import com.ostap.Entities.FootballPlayer;
import com.ostap.Entities.Team;
import com.ostap.Repositories.FootballPlayerRepository;
import com.ostap.Repositories.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(TeamRepository teamRepository, FootballPlayerRepository footballPlayerRepository) {
        return args -> {
            if (teamRepository.count() == 0) {
                Random random = new Random();
                
                List<Team> teams = new ArrayList<>();
                String[] teamNames = {"Real Madrid", "Barcelona", "Manchester United", "Bayern Munich", "PSG"};
                
                for (String teamName : teamNames) {
                    Team team = new Team(
                        teamName,
                            random.nextDouble(),
                        1000000 + random.nextDouble() * 9000000
                    );
                    teams.add(teamRepository.save(team));
                }

                String[] positions = {"Goalkeeper", "Defender", "Midfielder", "Forward"};
                String[] preferredFeet = {"Left", "Right"};
                String[] firstNames = {"John", "James", "Robert", "Michael", "William", "David", "Richard", "Joseph", "Thomas", "Charles"};
                String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez"};

                for (int i = 0; i < 100; i++) {
                    FootballPlayer player = new FootballPlayer(
                        18 + random.nextInt(23),
                        random.nextInt(240),
                        lastNames[random.nextInt(lastNames.length)],
                        1 + random.nextInt(99),
                        firstNames[random.nextInt(firstNames.length)],
                        teams.get(random.nextInt(teams.size())),
                        positions[random.nextInt(positions.length)],
                        preferredFeet[random.nextInt(preferredFeet.length)]
                    );
                    footballPlayerRepository.save(player);
                }
            }
        };
    }
} 