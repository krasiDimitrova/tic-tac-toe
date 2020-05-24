package com.example.kpk.tictactoe.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.kpk.tictactoe.models.Move;

@Repository
public interface MoveRepository extends JpaRepository<Move, Long> {
    
}