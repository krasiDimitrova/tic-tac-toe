package com.example.kpk.tictactoe.models;

import javax.persistence.*;

@Entity(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "player_symbol", nullable = false)
    private PlayerSymbol PlayerSymbol;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_status", nullable = false)
    private GameStatus gameStatus;

    @Column(name = "created", nullable = false)
    private Long created;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlayerSymbol getPlayerSymbol() {
        return this.PlayerSymbol;
    }

    public void setPlayerSymbol(PlayerSymbol PlayerSymbol) {
        this.PlayerSymbol = PlayerSymbol;
    }

    public GameStatus getGameStatus() {
        return this.gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Long getCreated() {
        return this.created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }   
}
