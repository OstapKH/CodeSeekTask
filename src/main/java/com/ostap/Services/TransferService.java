package com.ostap.Services;

import com.ostap.Entities.Player;
import com.ostap.Entities.Team;
import com.ostap.Repositories.PlayerRepository;
import com.ostap.Repositories.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TransferService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public TransferService(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    @Transactional
    public void transferPlayer(Long playerId, Long targetTeamId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));
        Team sourceTeam = player.getTeam();
        Team targetTeam = teamRepository.findById(targetTeamId)
                .orElseThrow(() -> new IllegalArgumentException("Target team not found"));

        double transferValue = calculateTransferValue(player);
        double commission = transferValue * sourceTeam.getCommissionRate();
        double totalCost = transferValue + commission;

        if (targetTeam.getBalance() < totalCost) {
            throw new IllegalArgumentException("Target team does not have enough balance");
        }

        targetTeam.updateBalance(-totalCost);
        sourceTeam.updateBalance(totalCost);

        player.setTeam(targetTeam);

        teamRepository.save(sourceTeam);
        teamRepository.save(targetTeam);
        playerRepository.save(player);
    }

    private double calculateTransferValue(Player player) {
        if (player.getAge() <= 0) {
            throw new IllegalArgumentException("Player age must be greater than 0");
        }
        return (player.getMonthsOfExperience() * 100000.0) / player.getAge();
    }
}
