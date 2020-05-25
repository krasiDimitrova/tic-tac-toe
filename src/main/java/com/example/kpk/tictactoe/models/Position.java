package com.example.kpk.tictactoe.models;

import java.util.Objects;

public class Position {
    private int row;

    private int column;

    public Position() {
    }

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return this.column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Position)) {
            return false;
        }
        Position position = (Position) o;
        return row == position.row && column == position.column;
    }


    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}