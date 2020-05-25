package com.example.kpk.tictactoe.dtos;

import com.example.kpk.tictactoe.models.PlayerType;
import com.example.kpk.tictactoe.models.Position;

public class MoveDTO {
    Long gameId;

    Position position;

    PlayerType playerType;

    public MoveDTO() {
    }

    public MoveDTO(Long gameId, Position position, PlayerType playerType) {
        this.gameId = gameId;
        this.position = position;
        this.playerType = playerType;
    }

    public Long getGameId() {
        return this.gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public PlayerType getPlayerType() {
        return this.playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }
}