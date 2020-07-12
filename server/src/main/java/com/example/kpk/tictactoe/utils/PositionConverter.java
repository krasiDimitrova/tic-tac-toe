package com.example.kpk.tictactoe.utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.example.kpk.tictactoe.models.Position;

@Converter
public class PositionConverter implements AttributeConverter<Position, String> {

    private static final String SEPARATOR = ", ";

    @Override
    public String convertToDatabaseColumn(Position position) {
        if (position == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(position.getRow());
        sb.append(SEPARATOR);
        sb.append(position.getColumn());

        return sb.toString();
    }

    @Override
    public Position convertToEntityAttribute(String dbPosition) {
        if (dbPosition == null || dbPosition.isEmpty()) {
            return null;
        }

        String[] pieces = dbPosition.split(SEPARATOR);

        if (pieces == null || pieces.length == 0) {
            return null;
        }

        Position position = new Position(Integer.parseInt(pieces[0]), Integer.parseInt(pieces[1]));

        return position;
    }
}