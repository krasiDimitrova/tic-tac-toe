package com.example.kpk.tictactoe.services;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.example.kpk.tictactoe.models.Position;
import com.example.kpk.tictactoe.dtos.MoveDTO;
import com.example.kpk.tictactoe.models.Game;
import com.example.kpk.tictactoe.models.GameStatus;
import com.example.kpk.tictactoe.models.Move;
import com.example.kpk.tictactoe.models.PlayerType;
import com.example.kpk.tictactoe.repositories.MoveRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoveServiceImpl implements MoveService {

    private MoveRepository moveRepository;

    private PositionService positionService;

    private ComputerPlayerService computerPlayerService;

    @Autowired
    public void setMoveRepository(MoveRepository moveRepository) {
        this.moveRepository = moveRepository;
    }

    @Autowired
    public void setPositionService(PositionService positionService) {
        this.positionService = positionService;
    }

    @Autowired
    public void setComputerPlayerService(ComputerPlayerService computerPlayerService) {
        this.computerPlayerService = computerPlayerService;
    }

    @Override
    public Move addMove(MoveDTO moveToAdd, Game game) {
        validateGameIsNotEnded(game);
        validatePosition(moveToAdd);
        Move move = new Move();
        move.setBoardPosition(moveToAdd.getPosition());
        move.setGame(game);
        move.setPlayerType(moveToAdd.getPlayerType());
        move.setCreated(new Date().getTime());
        return moveRepository.saveAndFlush(move);
    }

    @Override
    public boolean playerHasWon(Long gameId, PlayerType playerType) {
        List<Move> playerMoves = findMovesForPlayer(gameId, playerType);
        return positionService.hasWinningPositions(getPositionsFromMoves(playerMoves));
    }

    @Override
    public boolean hasNoEmptyPositions(Long gameId) {
        return getEmptyPositions(gameId).isEmpty();
    }

    @Override
    public Move getComputerMove(Game game) {
        List<Move> takenMoves = getTakenPositions(game.getId());
        Position computerNextPosition = computerPlayerService.findBestPosition(takenMoves, game.getPlayerSymbol());

        return addMove(new MoveDTO(game.getId(), computerNextPosition, PlayerType.COMPUTER), game);
    }

    private void validateGameIsNotEnded(Game game) {
        if (game.getGameStatus() != GameStatus.RUNNING) {
            throw new IllegalStateException(
                    String.format("Game has status = %s and new moves cannot be added", game.getGameStatus()));
        }
    }

    private void validatePosition(MoveDTO move) {
        List<Position> emptyPositions = getEmptyPositions(move.getGameId());
        Position position = move.getPosition();
        if (!emptyPositions.isEmpty() && !emptyPositions.contains(position)) {
            throw new IllegalArgumentException(String.format("Position (%d, %d) is taken. Select an empty position.",
                    position.getRow(), position.getColumn()));
        }
    }

    private List<Move> findMovesForPlayer(Long gameId, PlayerType playerType) {
        return moveRepository.findAllByGameIdAndPlayerType(gameId, playerType);
    }

    private List<Position> getEmptyPositions(Long gameId) {
        List<Position> takenPositions = getPositionsFromMoves(getTakenPositions(gameId));
        return positionService.getFreePositions(new HashSet<Position>(takenPositions));
    }

    private List<Move> getTakenPositions(Long gameId) {
        return moveRepository.findAllByGameId(gameId);
    }

    private List<Position> getPositionsFromMoves(List<Move> moves) {
        return moves.stream().map(Move::getBoardPosition).collect(Collectors.toList());
    }
}