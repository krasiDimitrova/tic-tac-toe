package com.example.kpk.tictactoe.services;

import java.util.List;
import java.util.Set;

import com.example.kpk.tictactoe.models.Position;

public interface PositionService {

    /**
     * Find the remaining free positions based on the given set of taken positions
     * 
     * @param takenPositions
     * @return {@link Set} of free {@link Position}
     */
    List<Position> getFreePositions(Set<Position> takenPositions);

    /**
     * Check if there is winnning combination of positions in the given list
     * 
     * @param positions
     * @return boolean
     */
    boolean hasWinningPositions(List<Position> positions);
}