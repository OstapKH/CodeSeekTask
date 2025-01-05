package com.ostap.Repositories;

import com.ostap.Entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> { 
}
