package com.example.kpk.tictactoe.services;

import com.example.kpk.tictactoe.dtos.MoveDTO;
import com.example.kpk.tictactoe.models.Game;
import com.example.kpk.tictactoe.models.Move;
import com.example.kpk.tictactoe.models.PlayerType;

public interface MoveService {

    /**
     * Create and save Move from given MoveDTO and Game
     *
     * @param moveToAdd
     * @param game
     * @return {@link Move} saved in the db
     */
    Move addMove(MoveDTO moveToAdd, Game game);

    /**
     * Check if the player with given type has won the game with given id
     * 
     * @param gameId
     * @param playerType
     * @return boolean
     */
    boolean playerHasWon(Long gameId, PlayerType playerType);

    /**
     * Check if there are no more empty positions
     * 
     * @param gameId
     * @return boolean
     */
    boolean hasNoEmptyPositions(Long gameId);

    /**
     * Make a move as player type Computer for the given game
     * 
     * @param game
     * @return the created computer {@link Move}
     */
    Move getComputerMove(Game game);
}