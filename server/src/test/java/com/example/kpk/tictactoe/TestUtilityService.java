package com.example.kpk.tictactoe;

import com.example.kpk.tictactoe.models.*;

public interface TestUtilityService {

    /**
     * Create a game based on the given player symbol 
     * and save and flush it into the database
     * 
     * @param playerSymbol
     * @return the created {@link Game}
     */
    Game createGame(PlayerSymbol playerSymbol);

    /**
     * Create a move base on given position and player type for the game with given id
     * and save and flush it into the database
     * 
     * @param position
     * @param playerType
     * @param gameId
     * @return the created {@link Move}
     */
    Move createMove(Position position, PlayerType playerType, Long gameId);
}