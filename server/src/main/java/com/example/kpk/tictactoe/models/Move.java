package com.example.kpk.tictactoe.models;

import javax.persistence.*;

import com.example.kpk.tictactoe.utils.PositionConverter;

@Entity(name = "move")
public class Move {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Enumerated(EnumType.STRING)
    private PlayerType playerType;

    @Column(name = "board_position", nullable = false)
    @Convert(converter = PositionConverter.class)
    private Position boardPosition;

    @Column(name = "created", nullable = false)
    private Long created;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public PlayerType getPlayerType() {
        return this.playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public Position getBoardPosition() {
        return this.boardPosition;
    }

    public void setBoardPosition(Position boardPosition) {
        this.boardPosition = boardPosition;
    }

    public Long getCreated() {
        return this.created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }
}