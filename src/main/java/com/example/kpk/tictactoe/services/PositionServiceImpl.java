package com.example.kpk.tictactoe.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.kpk.tictactoe.models.Position;

import org.springframework.stereotype.Service;

@Service
public class PositionServiceImpl implements PositionService {

    private Set<Position> allPositions;

    private List<List<Position>> winningPositions;

    public PositionServiceImpl() {
        allPositions = new HashSet<Position>();
        winningPositions = new ArrayList<List<Position>>();
        initAllPositions();
        initWinningPositions();
    }

    @Override
    public List<Position> getFreePositions(Set<Position> takenPositions) {
        return this.allPositions.stream()
                .filter(position -> !takenPositions.contains(position))
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasWinningPositions(List<Position> positions) {
        return winningPositions.stream().anyMatch(positions::containsAll);
    }

    private void initAllPositions() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                allPositions.add(new Position(row, col));
            }
        }
    }

    private void initWinningPositions() {
        for (int i = 0; i < 3; i++) {
            List<Position> winningRow = new ArrayList<Position>();
            List<Position> winningColumn = new ArrayList<Position>();
            for (int j = 0; j < 3; j++) {
                winningRow.add(new Position(i, j));
                winningColumn.add(new Position(j, i));
            }
            winningPositions.add(winningRow);
            winningPositions.add(winningColumn);
        }

        // Add main diagonal
        winningPositions.add(Arrays.asList(new Position(0, 0), new Position(1, 1), new Position(2, 2)));

        // Add sub diagonal
        winningPositions.add(Arrays.asList(new Position(0, 2), new Position(1, 1), new Position(2, 0)));
    }
}