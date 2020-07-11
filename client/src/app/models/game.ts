import { Move } from "./move";
import { PlayerSymbol } from "./player";

export class Game {
  gameId: number;
  gameStatus: GameStatus;
  computerMove?: Move;
  playerSymbol: PlayerSymbol;
}

export enum GameStatus {
  RUNNING = 'RUNNING',
  WON = 'WON',
  TIE = 'TIE',
  LOST = 'LOST',
}
