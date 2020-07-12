package com.example.kpk.tictactoe.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.example.kpk.tictactoe.models.Position;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class PositionServiceTest {
    private PositionService positionService;

    @Before
    public void setUp() {
        this.positionService = new PositionServiceImpl();
    }

    @Test
    public void testGetFreePositions() {
        // For board 3x3
        int allPositionsCount = 3 * 3;
        List<Position> takenPositions = Arrays.asList(new Position(0, 0), new Position(0, 2), new Position(2, 2));
        List<Position> freePositions = positionService.getFreePositions(new HashSet<Position>(takenPositions));
        assertEquals(allPositionsCount - takenPositions.size(), freePositions.size());
        assertThat(freePositions, not(containsInAnyOrder(takenPositions)));
    }

    @Test
    public void testGetFreePositionsWithEmptyTakenPositions() {
        // For board 3x3
        int allPositionsCount = 3 * 3;
        List<Position> takenPositions = new ArrayList<Position>();
        List<Position> freePositions = positionService.getFreePositions(new HashSet<Position>(takenPositions));
        assertEquals(allPositionsCount - takenPositions.size(), freePositions.size());
    }

    @Test
    public void testHasWinningPositions() {
        List<List<Position>> winningPositions = Arrays.asList(
                Arrays.asList(new Position(0, 0), new Position(0, 1), new Position(0, 2)),
                Arrays.asList(new Position(0, 0), new Position(1, 1), new Position(0, 1), new Position(2, 2),
                        new Position(2, 1)));

        for (List<Position> winningPosition : winningPositions) {
            assertTrue(positionService.hasWinningPositions(winningPosition));
        }
    }

    @Test
    public void testHasWinningPositionsWithNotWinningPositions() {
        List<Position> notWinningPositions = Arrays.asList(new Position(0, 0), new Position(0, 2), new Position(1, 0),
                new Position(1, 1), new Position(2, 1));
        assertFalse(positionService.hasWinningPositions(notWinningPositions));
    }

}