package com.ostap.Controllers;

import com.ostap.Entities.FootballPlayer;
import com.ostap.Entities.Player;
import com.ostap.Services.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/players")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/football")
    public ResponseEntity<FootballPlayer> createFootballPlayer(@RequestBody FootballPlayer player) {
        return ResponseEntity.ok(playerService.createFootballPlayer(player));
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferPlayer(
            @RequestParam Long playerId,
            @RequestParam Long sourceTeamId,
            @RequestParam Long targetTeamId,
            @RequestParam double transferFee) {
        playerService.transferPlayer(playerId, sourceTeamId, targetTeamId, transferFee);
        return ResponseEntity.ok("Transfer completed successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.getPlayer(id));
    }

    @PutMapping("/football/{id}")
    public ResponseEntity<FootballPlayer> updateFootballPlayer(
            @PathVariable Long id,
            @RequestBody FootballPlayer player) {
        player.setId(id);
        return ResponseEntity.ok(playerService.updateFootballPlayer(player));
    }
} 