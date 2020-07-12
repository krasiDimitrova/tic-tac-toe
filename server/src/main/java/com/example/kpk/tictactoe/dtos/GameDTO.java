package com.example.kpk.tictactoe.dtos;

import com.example.kpk.tictactoe.models.GameStatus;

public class GameDTO {
    private Long gameId;

    private GameStatus gameStatus;

    private MoveDTO computerMove;

    public Long getGameId() {
        return this.gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public GameStatus getGameStatus() {
        return this.gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public MoveDTO getComputerMove() {
        return this.computerMove;
    }

    public void setComputerMove(MoveDTO computerMove) {
        this.computerMove = computerMove;
    }  
}