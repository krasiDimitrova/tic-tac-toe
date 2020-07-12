package com.example.kpk.tictactoe.services;

import java.util.List;

import com.example.kpk.tictactoe.models.Move;
import com.example.kpk.tictactoe.models.PlayerSymbol;
import com.example.kpk.tictactoe.models.Position;

public interface ComputerPlayerService {

    /**
     * Find the next best move based on given taken moves and player symbol
     * 
     * @param moves
     * @param player
     * @return the best next {@link Position}
     */
    public Position findBestPosition(List<Move> moves, PlayerSymbol player);

}