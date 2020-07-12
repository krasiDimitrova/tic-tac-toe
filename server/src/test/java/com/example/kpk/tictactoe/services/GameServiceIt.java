package com.example.kpk.tictactoe.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.kpk.tictactoe.TestUtilityService;
import com.example.kpk.tictactoe.dtos.*;
import com.example.kpk.tictactoe.models.*;
import com.example.kpk.tictactoe.repositories.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServiceIt {
    @Autowired
    private MoveRepository moveRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameService gameService;

    @Autowired
    private TestUtilityService testUtilityService;

    @Spy
    private Random random = new Random();

    @Before
    public void setUp() {
        ((GameServiceImpl) gameService).setRandom(random);
    }

    @After
    public void tearDown() {
        moveRepository.deleteAll();
        gameRepository.deleteAll();
    }

    @Test
    public void testStartGame() {
        PlayerSymbol playerSymbol = PlayerSymbol.X;
        GameDTO startedGame = gameService.startGame(playerSymbol);
        assertNotNull(startedGame);
        assertEquals(GameStatus.RUNNING, startedGame.getGameStatus());

        assertTrue(gameRepository.existsById(startedGame.getGameId()));
    }

    @Test
    public void testStartGameWithNullPlayerSymbol() {
        assertThrows(IllegalArgumentException.class, () -> gameService.startGame(null));
        assertEquals(0, gameRepository.findAll().size());
    }

    @Test
    public void testStartGameWithComputerFirstMove() {
        PlayerSymbol playerSymbol = PlayerSymbol.X;

        when(random.nextBoolean()).thenReturn(true);

        GameDTO startedGame = gameService.startGame(playerSymbol);
        assertNotNull(startedGame);
        assertEquals(GameStatus.RUNNING, startedGame.getGameStatus());
        assertNotNull(startedGame.getComputerMove());

        assertTrue(gameRepository.existsById(startedGame.getGameId()));
        assertEquals(1, moveRepository.findAll().size());
    }

    @Test
    public void testMakeMove() {
        GameDTO startedGame = gameService.startGame(PlayerSymbol.X);

        when(random.nextBoolean()).thenReturn(false);

        MoveDTO playerMove = new MoveDTO(startedGame.getGameId(), new Position(1, 1), PlayerType.PLAYER);
        GameDTO gameSnapshot = gameService.makeMove(playerMove);

        assertNotNull(gameSnapshot);
        assertEquals(GameStatus.RUNNING, gameSnapshot.getGameStatus());
        assertNotNull(gameSnapshot.getComputerMove());

        assertEquals(2, moveRepository.findAll().size());
    }

    @Test
    public void testMakeMoveWithWinningMove() {
        Game game = testUtilityService.createGame(PlayerSymbol.X);
        Long gameId = game.getId();

        testUtilityService.createMove(new Position(0, 2), PlayerType.PLAYER, gameId);
        testUtilityService.createMove(new Position(2, 0), PlayerType.PLAYER, gameId);

        MoveDTO winningMove = new MoveDTO(gameId, new Position(1, 1), PlayerType.PLAYER);
        GameDTO gameSnapshot = gameService.makeMove(winningMove);

        assertNotNull(gameSnapshot);
        assertEquals(GameStatus.WON, gameSnapshot.getGameStatus());
        assertNull(gameSnapshot.getComputerMove());

        assertEquals(3, moveRepository.findAll().size());
    }

    @Test
    public void testMakeMoveWithLastEmptyPosition() {
        Game game = testUtilityService.createGame(PlayerSymbol.X);
        Long gameId = game.getId();

        // Geenrate sequence of move owners to ensure no one would win the game
        List<PlayerType> playerTypes = new ArrayList<PlayerType>();
        playerTypes.add(PlayerType.COMPUTER);
        playerTypes.add(PlayerType.PLAYER);
        for (int i = 0; i < 4; i++) {
            playerTypes.addAll(playerTypes);
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                // Without the last move wich would be made by the PLAYER
                if (!(row == 2 && col == 2)) {
                    testUtilityService.createMove(new Position(row, col), playerTypes.get(row + col), gameId);
                }
            }
        }

        MoveDTO lastMove = new MoveDTO(gameId, new Position(2, 2), PlayerType.PLAYER);
        GameDTO gameSnapshot = gameService.makeMove(lastMove);

        assertNotNull(gameSnapshot);
        assertEquals(GameStatus.TIE, gameSnapshot.getGameStatus());
        assertNull(gameSnapshot.getComputerMove());

        assertEquals(9, moveRepository.findAll().size());
    }

    @Test
    public void testMakeMoveWithComputerMakingLastMove() {
        Game game = testUtilityService.createGame(PlayerSymbol.X);
        Long gameId = game.getId();

        // Geenrate sequence of move owners to ensure no one would win the game
        List<PlayerType> playerTypes = new ArrayList<PlayerType>();
        playerTypes.add(PlayerType.COMPUTER);
        playerTypes.add(PlayerType.PLAYER);
        for (int i = 0; i < 3; i++) {
            playerTypes.addAll(playerTypes);
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                // Without the last row moves
                if (row != 2) {
                    testUtilityService.createMove(new Position(row, col), playerTypes.get(row + col), gameId);
                }
            }
        }
        testUtilityService.createMove(new Position(2, 0), PlayerType.PLAYER, gameId);

        MoveDTO lastMove = new MoveDTO(gameId, new Position(2, 2), PlayerType.PLAYER);
        GameDTO gameSnapshot = gameService.makeMove(lastMove);

        assertNotNull(gameSnapshot);
        assertEquals(GameStatus.TIE, gameSnapshot.getGameStatus());
        MoveDTO computerMove = gameSnapshot.getComputerMove();
        assertNotNull(computerMove);
        assertEquals(new Position(2, 1), computerMove.getPosition());

        assertEquals(9, moveRepository.findAll().size());
    }

    @Test
    public void testMakeMoveWithComputerWins() {
        Game game = testUtilityService.createGame(PlayerSymbol.X);
        Long gameId = game.getId();

        // Geenrate sequence of move owners to ensure last COMPUTER move will win the game
        List<PlayerType> playerTypes = new ArrayList<PlayerType>();
        playerTypes.add(PlayerType.COMPUTER);
        playerTypes.add(PlayerType.PLAYER);
        for (int i = 0; i < 3; i++) {
            playerTypes.addAll(playerTypes);
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                // Without the last row moves
                if (row != 2) {
                    testUtilityService.createMove(new Position(row, col), playerTypes.get(row + col), gameId);
                }
            }
        }
        testUtilityService.createMove(new Position(2, 0), PlayerType.PLAYER, gameId);

        MoveDTO lastMove = new MoveDTO(gameId, new Position(2, 1), PlayerType.PLAYER);
        GameDTO gameSnapshot = gameService.makeMove(lastMove);

        assertNotNull(gameSnapshot);
        assertEquals(GameStatus.LOST, gameSnapshot.getGameStatus());
        MoveDTO computerMove = gameSnapshot.getComputerMove();
        assertNotNull(computerMove);
        assertEquals(new Position(2, 2), computerMove.getPosition());

        assertEquals(9, moveRepository.findAll().size());
    }
}