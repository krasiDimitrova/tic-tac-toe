package com.example.kpk.tictactoe.services;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

import javax.persistence.EntityNotFoundException;

import com.example.kpk.tictactoe.dtos.*;
import com.example.kpk.tictactoe.models.*;
import com.example.kpk.tictactoe.repositories.GameRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    private Random random = new Random();

    private GameRepository gameRepository;

    private MoveService moveService;

    @Autowired
    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Autowired
    public void setMoveService(MoveService moveService) {
        this.moveService = moveService;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    @Override
    public GameDTO startGame(PlayerSymbol playerSymbol) {
        if (playerSymbol == null) {
            throw new IllegalArgumentException("playerSymbol is required parameter");
        }

        Game game = new Game();
        game.setGameStatus(GameStatus.RUNNING);
        game.setPlayerSymbol(playerSymbol);
        game.setCreated(new Date().getTime());
        game = gameRepository.saveAndFlush(game);

        // Choose if the computer makes the first move
        Move computerMove = random.nextBoolean() ? moveService.getComputerMove(game) : null;

        return generateGameSnapshot(game.getId(), GameStatus.RUNNING, computerMove);
    }

    @Override
    public GameDTO makeMove(MoveDTO move) {
        Long gameId = move.getGameId();
        Game game = findById(gameId);
        moveService.addMove(move, game);

        if (moveService.playerHasWon(gameId, move.getPlayerType())) {
            return generateGameSnapshot(gameId, GameStatus.WON, null);
        }

        Move computerMove = moveService.getComputerMove(game);
        if (computerMove == null) {
            return generateGameSnapshot(gameId, GameStatus.TIE, null);
        }

        if (moveService.playerHasWon(gameId, PlayerType.COMPUTER)) {
            return generateGameSnapshot(gameId, GameStatus.LOST, computerMove);
        }

        if (moveService.hasNoEmptyPositions(gameId)) {
            return generateGameSnapshot(gameId, GameStatus.TIE, computerMove);
        }

        return generateGameSnapshot(gameId, GameStatus.RUNNING, computerMove);
    }

    private GameDTO generateGameSnapshot(Long gameId, GameStatus gameStatus, Move nextMove) {
        MoveDTO move = null;
        if (nextMove != null) {
            move = new MoveDTO(gameId, nextMove.getBoardPosition(), nextMove.getPlayerType());
        }

        GameDTO gameSnapshot = new GameDTO();
        gameSnapshot.setGameId(gameId);
        gameSnapshot.setGameStatus(gameStatus);
        gameSnapshot.setComputerMove(move);
        return gameSnapshot;
    }

    private Game findById(Long id) {
        Optional<Game> foundGame = gameRepository.findById(id);

        if (!foundGame.isPresent()) {
            throw new EntityNotFoundException(String.format("Game with id = %d does not exist", id));
        }

        return foundGame.get();
    }
}