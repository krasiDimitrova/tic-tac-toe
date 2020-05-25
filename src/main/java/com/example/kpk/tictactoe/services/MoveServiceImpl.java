package com.example.kpk.tictactoe.services;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.example.kpk.tictactoe.models.Position;
import com.example.kpk.tictactoe.dtos.MoveDTO;
import com.example.kpk.tictactoe.models.Game;
import com.example.kpk.tictactoe.models.Move;
import com.example.kpk.tictactoe.models.PlayerType;
import com.example.kpk.tictactoe.repositories.MoveRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoveServiceImpl implements MoveService {

    private static Random random = new Random();

    private MoveRepository moveRepository;

    private PositionService positionService;

    @Autowired
    public void setMoveRepository(MoveRepository moveRepository) {
        this.moveRepository = moveRepository;
    }

    @Autowired
    public void setPositionService(PositionService positionService) {
        this.positionService = positionService;
    }

    @Override
    public Move addMove(MoveDTO moveToAdd, Game game) {
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
        List<Position> positions = getEmptyPositions(game.getId());
        int size = positions.size();
        if (size == 0) {
            return null;
        }

        return addMove(new MoveDTO(game.getId(), positions.get(random.nextInt(size)), PlayerType.COMPUTER), game);
    }

    private List<Move> findMovesForPlayer(Long gameId, PlayerType playerType) {
        return moveRepository.findAllByGameIdAndPlayerType(gameId, playerType);
    }

    private List<Position> getEmptyPositions(Long gameId) {
        List<Position> takenPositions = getPositionsFromMoves(moveRepository.findAllByGameId(gameId));
        return positionService.getFreePositions(new HashSet<Position>(takenPositions));
    }

    private List<Position> getPositionsFromMoves(List<Move> moves) {
        return moves.stream().map(Move::getBoardPosition).collect(Collectors.toList());
    }

    private void validatePosition(MoveDTO move) {
        List<Position> emptyPositions = getEmptyPositions(move.getGameId());
        Position position = move.getPosition();
        if (!emptyPositions.contains(position)) {
            throw new IllegalArgumentException(String.format("Position (%d, %d) is taken. Select an empty position.",
                    position.getRow(), position.getColumn()));
        }
    }
}