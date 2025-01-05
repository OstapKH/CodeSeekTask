package com.ostap.Services;

import com.ostap.Entities.Team;
import com.ostap.Repositories.TeamRepository;
import com.ostap.Exceptions.ResourceNotFoundException;
import com.ostap.Exceptions.InvalidOperationException;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team createTeam(Team team) {
        if (team.getName() == null || team.getName().trim().isEmpty()) {
            throw new InvalidOperationException("Team name cannot be empty");
        }
        if (team.getCommissionRate() < 0 || team.getCommissionRate() > 1) {
            throw new InvalidOperationException("Commission rate must be between 0 and 1");
        }
        return teamRepository.save(team);
    }

    public Team getTeam(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
    }

    public Team updateTeam(Team team) {
        if (!teamRepository.existsById(team.getId())) {
            throw new ResourceNotFoundException("Team not found");
        }
        if (team.getName() == null || team.getName().trim().isEmpty()) {
            throw new InvalidOperationException("Team name cannot be empty");
        }
        if (team.getCommissionRate() < 0 || team.getCommissionRate() > 1) {
            throw new InvalidOperationException("Commission rate must be between 0 and 1");
        }
        return teamRepository.save(team);
    }
} 