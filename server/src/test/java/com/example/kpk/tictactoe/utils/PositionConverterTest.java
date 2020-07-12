package com.example.kpk.tictactoe.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.kpk.tictactoe.models.Position;

import org.junit.*;

public class PositionConverterTest {
    private PositionConverter positionConverter;

    @Before
    public void setUp() {
        this.positionConverter = new PositionConverter();
    }

    @Test
    public void testConvertToDatabaseColumn() {
        Position position = new Position(1, 2);
        String expectedResult = String.format("%d, %d", position.getRow(), position.getColumn());
        assertEquals(expectedResult, positionConverter.convertToDatabaseColumn(position));
    }

    @Test
    public void testConvertToDatabaseColumnNullPosition() {
        Position position = null;
        assertNull(positionConverter.convertToDatabaseColumn(position));
    }

    @Test
    public void testConvertToDatabaseColumnDefaultValuesPosition() {
        Position position = new Position();
        String expectedResult = String.format("%d, %d", position.getRow(), position.getColumn());
        assertEquals(expectedResult, positionConverter.convertToDatabaseColumn(position));
    }

    @Test
    public void testConvertToEntityAttribute() {
        int row = 1;
        int column = 2;
        String entityPosition = String.format("%d, %d", row, column);
        Position result = positionConverter.convertToEntityAttribute(entityPosition);
        assertEquals(row, result.getRow());
        assertEquals(column, result.getColumn());
    }

    @Test
    public void testConvertToEntityAttributeNullDatabaseColumn() {
        assertNull(positionConverter.convertToEntityAttribute(null));
    }

    @Test
    public void testConvertToEntityAttributeEmptyDatabaseColumn() {
        String entity = "";
        assertNull(positionConverter.convertToEntityAttribute(entity));
    }

    @Test
    public void testConvertBoatWays() {
        Position position = new Position();
        String expectedResult = String.format("%d, %d", position.getRow(), position.getColumn());
        assertEquals(expectedResult, positionConverter.convertToDatabaseColumn(position));
        assertEquals(position, positionConverter.convertToEntityAttribute(expectedResult));
    }
}