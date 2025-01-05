package com.ostap.Services;

import com.ostap.Entities.FootballPlayer;
import com.ostap.Entities.Player;
import com.ostap.Entities.Team;
import com.ostap.Repositories.FootballPlayerRepository;
import com.ostap.Repositories.PlayerRepository;
import com.ostap.Exceptions.ResourceNotFoundException;
import com.ostap.Exceptions.InvalidOperationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerService {
    private final FootballPlayerRepository footballPlayerRepository;
    private final PlayerRepository playerRepository;
    private final TeamService teamService;

    public PlayerService(FootballPlayerRepository footballPlayerRepository,
                        PlayerRepository playerRepository,
                        TeamService teamService) {
        this.footballPlayerRepository = footballPlayerRepository;
        this.playerRepository = playerRepository;
        this.teamService = teamService;
    }

    public FootballPlayer createFootballPlayer(FootballPlayer player) {
        if (player.getTeam() == null || player.getTeam().getId() == null) {
            throw new InvalidOperationException("Player must be assigned to a team");
        }
        Team team = teamService.getTeam(player.getTeam().getId());
        player.setTeam(team);
        
        validatePlayer(player);
        return footballPlayerRepository.save(player);
    }

    @Transactional
    public void transferPlayer(Long playerId, Long sourceTeamId, Long targetTeamId, double transferFee) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new ResourceNotFoundException("Player not found"));
        Team sourceTeam = teamService.getTeam(sourceTeamId);
        Team targetTeam = teamService.getTeam(targetTeamId);

        if (!player.getTeam().getId().equals(sourceTeamId)) {
            throw new InvalidOperationException("Player does not belong to the source team");
        }

        if (transferFee <= 0) {
            throw new InvalidOperationException("Transfer fee must be positive");
        }

        if (targetTeam.getBalance() < transferFee) {
            throw new InvalidOperationException("Target team has insufficient funds");
        }

        double commission = transferFee * sourceTeam.getCommissionRate();
        double finalAmount = transferFee - commission;

        sourceTeam.updateBalance(finalAmount);
        targetTeam.updateBalance(-transferFee);
        player.setTeam(targetTeam);

        teamService.updateTeam(sourceTeam);
        teamService.updateTeam(targetTeam);
        playerRepository.save(player);
    }

    public Player getPlayer(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player not found"));
    }

    public Player updatePlayer(Player player) {
        if (!playerRepository.existsById(player.getId())) {
            throw new ResourceNotFoundException("Player not found");
        }
        validatePlayer(player);
        return playerRepository.save(player);
    }

    public FootballPlayer updateFootballPlayer(FootballPlayer player) {
        if (!footballPlayerRepository.existsById(player.getId())) {
            throw new ResourceNotFoundException("Player not found with id: " + player.getId());
        }
        Team team = teamService.getTeam(player.getTeam().getId());
        player.setTeam(team);
        validatePlayer(player);
        return footballPlayerRepository.save(player);
    }

    private void validatePlayer(Player player) {
        if (player.getName() == null || player.getName().trim().isEmpty()) {
            throw new InvalidOperationException("Player name cannot be empty");
        }
        if (player.getSurname() == null || player.getSurname().trim().isEmpty()) {
            throw new InvalidOperationException("Player surname cannot be empty");
        }
        if (player.getAge() == null || player.getAge() < 16 || player.getAge() > 50) {
            throw new InvalidOperationException("Player age must be between 16 and 50");
        }
        if (player.getNumber() == null || player.getNumber() < 1 || player.getNumber() > 99) {
            throw new InvalidOperationException("Player number must be between 1 and 99");
        }
        if (player.getMonthsOfExperience() == null || player.getMonthsOfExperience() < 0) {
            throw new InvalidOperationException("Months of experience cannot be negative");
        }
        if (player.getTeam() == null || player.getTeam().getId() == null) {
            throw new InvalidOperationException("Player must be assigned to a team");
        }
    }
} 