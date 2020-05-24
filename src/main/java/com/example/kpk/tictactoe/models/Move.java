package com.example.kpk.tictactoe.models;

import javax.persistence.*;

@Entity(name = "move")
public class Move {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Enumerated(EnumType.STRING)
    private PlayerType PlayerType;

    @Column(name = "board_row", nullable = false)
    private int boardRow;

    @Column(name = "board_column", nullable = false)
    private int boardColumn;

    @Column(name = "created", nullable = false)
    private Long created;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getBoardRow() {
        return this.boardRow;
    }

    public void setBoardRow(int boardRow) {
        this.boardRow = boardRow;
    }

    public int getBoardColumn() {
        return this.boardColumn;
    }

    public void setBoardColumn(int boardColumn) {
        this.boardColumn = boardColumn;
    }

    public PlayerType getPlayerType() {
        return this.PlayerType;
    }

    public void setPlayerType(PlayerType PlayerType) {
        this.PlayerType = PlayerType;
    }

    public Long getCreated() {
        return this.created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }
}