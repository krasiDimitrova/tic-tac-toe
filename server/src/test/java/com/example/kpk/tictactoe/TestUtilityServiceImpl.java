package com.example.kpk.tictactoe;

import java.util.Date;

import com.example.kpk.tictactoe.models.*;
import com.example.kpk.tictactoe.repositories.GameRepository;
import com.example.kpk.tictactoe.repositories.MoveRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestUtilityServiceImpl implements TestUtilityService {

    @Autowired
    private MoveRepository moveRepository;

    @Autowired
    private GameRepository gameRepository;

    @Override
    public Game createGame(PlayerSymbol playerSymbol) {
        Game game = new Game();
        game.setPlayerSymbol(playerSymbol);
        game.setGameStatus(GameStatus.RUNNING);
        game.setCreated(new Date().getTime());
        return gameRepository.saveAndFlush(game);
    }

    @Override
    public Move createMove(Position position, PlayerType playerType, Long gameId) {
        Game game = gameRepository.findById(gameId).get();
        Move move = new Move();
        move.setBoardPosition(position);
        move.setGame(game);
        move.setPlayerType(playerType);
        move.setCreated(new Date().getTime());
        return moveRepository.saveAndFlush(move);
    }
}