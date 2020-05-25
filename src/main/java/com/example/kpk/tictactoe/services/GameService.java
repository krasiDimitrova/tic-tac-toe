package com.example.kpk.tictactoe.services;

import com.example.kpk.tictactoe.dtos.GameDTO;
import com.example.kpk.tictactoe.dtos.MoveDTO;
import com.example.kpk.tictactoe.models.*;

public interface GameService {

    /**
     * Start a game with the given player symbol 
     * and retun the gaame snapshot
     * 
     * @param playerSymbol
     * @return the created {@link GameDTO}
     */
    GameDTO startGame(PlayerSymbol PlayerSymbol);

    /**
     * Make the given move and return the game snapshot
     * 
     * @param move
     * @return the {@link GameDTO} snapshot after the given player move
     */
    GameDTO makeMove(MoveDTO move);
}