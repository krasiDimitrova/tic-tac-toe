package com.example.kpk.tictactoe.services;

import static org.junit.Assert.*;

import com.example.kpk.tictactoe.TestUtilityService;
import com.example.kpk.tictactoe.dtos.MoveDTO;
import com.example.kpk.tictactoe.models.*;
import com.example.kpk.tictactoe.repositories.*;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MoveServiceIt {

    @Autowired
    private MoveRepository moveRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private MoveService moveService;

    @Autowired
    private TestUtilityService testUtilityService;

    @After
    public void tearDown() {
        moveRepository.deleteAll();
        gameRepository.deleteAll();
    }

    @Test
    public void testAddMove() {
        Game game = testUtilityService.createGame(PlayerSymbol.X);
        MoveDTO moveToAdd = new MoveDTO();
        moveToAdd.setGameId(game.getId());
        moveToAdd.setPlayerType(PlayerType.PLAYER);
        moveToAdd.setPosition(new Position(0, 0));

        Move addedMove = moveService.addMove(moveToAdd, game);
        assertEquals(moveToAdd.getPosition(), addedMove.getBoardPosition());
        assertEquals(moveToAdd.getPlayerType(), addedMove.getPlayerType());
        assertEquals(moveToAdd.getGameId(), addedMove.getGame().getId());

        assertTrue(moveRepository.existsById(addedMove.getId()));
    }

    @Test
    public void testAddMoveTakenPosition() {
        Game game = testUtilityService.createGame(PlayerSymbol.X);
        Long gameId = game.getId();
        Position position = new Position(1, 1);
        testUtilityService.createMove(position, PlayerType.COMPUTER, gameId);

        MoveDTO moveToAdd = new MoveDTO();
        moveToAdd.setGameId(gameId);
        moveToAdd.setPlayerType(PlayerType.PLAYER);
        moveToAdd.setPosition(position);

        assertThrows(IllegalArgumentException.class, () -> moveService.addMove(moveToAdd, game));
        assertEquals(1, moveRepository.findAll().size());
    }

    @Test
    public void testAddMoveEndedGame() {
        Game game = testUtilityService.createGame(PlayerSymbol.X);
        game.setGameStatus(GameStatus.TIE);
        gameRepository.saveAndFlush(game);

        MoveDTO moveToAdd = new MoveDTO();
        moveToAdd.setGameId(game.getId());
        moveToAdd.setPlayerType(PlayerType.PLAYER);
        moveToAdd.setPosition(new Position(1, 1));

        assertThrows(IllegalStateException.class, () -> moveService.addMove(moveToAdd, game));
        assertEquals(0, moveRepository.findAll().size());
    }

    @Test
    public void testPlayerHasWon() {
        Game game = testUtilityService.createGame(PlayerSymbol.O);
        Long gameId = game.getId();
        PlayerType playerType = PlayerType.PLAYER;
        for (int i = 0; i < 3; i++) {
            testUtilityService.createMove(new Position(i, i), playerType, gameId);
        }

        assertTrue(moveService.playerHasWon(gameId, playerType));
        assertFalse(moveService.playerHasWon(gameId, PlayerType.COMPUTER));
    }

    @Test
    public void testPlayerHasWonNotWinningCombinations() {
        Game game = testUtilityService.createGame(PlayerSymbol.O);
        Long gameId = game.getId();
        PlayerType playerType = PlayerType.COMPUTER;
        for (int i = 0; i < 2; i++) {
            testUtilityService.createMove(new Position(i, i), playerType, gameId);
        }

        assertFalse(moveService.playerHasWon(gameId, playerType));
        assertFalse(moveService.playerHasWon(gameId, PlayerType.PLAYER));
    }

    @Test
    public void testHasNoEmptyPositions() {
        Game game = testUtilityService.createGame(PlayerSymbol.O);
        Long gameId = game.getId();
        PlayerType playerType = PlayerType.COMPUTER;

        assertFalse(moveService.hasNoEmptyPositions(gameId));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                testUtilityService.createMove(new Position(row, col), playerType, gameId);
            }
        }

        assertTrue(moveService.hasNoEmptyPositions(gameId));
    }

    @Test
    public void testGetComputerMove() {
        Game game = testUtilityService.createGame(PlayerSymbol.O);
        Long gameId = game.getId();
        Move playerMove = testUtilityService.createMove(new Position(0, 0), PlayerType.PLAYER, gameId);

        Move computerMove = moveService.getComputerMove(game);
        assertEquals(PlayerType.COMPUTER, computerMove.getPlayerType());
        assertNotEquals(playerMove.getBoardPosition(), computerMove.getBoardPosition());

        assertTrue(moveRepository.existsById(computerMove.getId()));
    }

    @Test
    public void testGetComputerMoveNoEmptyPositions() {
        Game game = testUtilityService.createGame(PlayerSymbol.O);
        Long gameId = game.getId();
        PlayerType playerType = PlayerType.PLAYER;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                testUtilityService.createMove(new Position(row, col), playerType, gameId);
            }
        }

        assertTrue(moveService.hasNoEmptyPositions(gameId));

        Move computerMove = moveService.getComputerMove(game);
        assertNull(computerMove);
    }
}