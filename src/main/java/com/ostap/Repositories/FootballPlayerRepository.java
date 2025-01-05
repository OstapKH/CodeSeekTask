package com.ostap.Repositories;

import com.ostap.Entities.FootballPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FootballPlayerRepository extends JpaRepository<FootballPlayer, Long> {
}
