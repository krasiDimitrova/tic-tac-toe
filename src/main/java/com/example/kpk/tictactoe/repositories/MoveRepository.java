package com.example.kpk.tictactoe.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.example.kpk.tictactoe.models.Move;
import com.example.kpk.tictactoe.models.PlayerType;

@Repository
public interface MoveRepository extends JpaRepository<Move, Long> {

    /**
     * Find all Moves made in game with given id
     * 
     * @param gameId
     * @return {@link List} of {@link Move}
     */
    List<Move> findAllByGameId(Long gameId);

    /**
     * Find the Moves made in the game with given id by player with given type
     * 
     * @param gameId
     * @param playerType
     * @return {@link List} of {@link Move}
     */
    List<Move> findAllByGameIdAndPlayerType(Long gameId, PlayerType playerType);
}