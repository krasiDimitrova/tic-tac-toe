import { PlayerType } from "./player";

export class Position {
    row: number;
    column: number
}

export class Move {
    gameId: number;
    position: Position;
    playerType:PlayerType;
}