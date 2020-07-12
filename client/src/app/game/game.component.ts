import { Component, OnInit } from "@angular/core";
import { Game, GameStatus } from "../models/game";
import { GameApiService } from "../services/game-api.service";
import { PlayerSymbol, PlayerType } from "../models/player";
import { Move } from "../models/move";

@Component({
  selector: "app-game",
  templateUrl: "./game.component.html",
  styleUrls: ["./game.component.scss"],
})
export class GameComponent implements OnInit {
  constructor(private gameApiService: GameApiService) {}

  game: Game;

  err: string;

  gameBoard: string[][];

  readonly GAME_STATUS = GameStatus;

  private readonly SIZE = 3;

  private computerSymbol: string;

  ngOnInit() {
    this.gameBoard = [];
    for (let i = 0; i < this.SIZE; i++) {
      let row = new Array<string>(this.SIZE).fill("");
      this.gameBoard.push(row);
    }
  }

  populateGame($game: Game) {
    this.game = $game;
    this.populateComputerSymbol();
    this.makeComputerMove();
  }

  makePlayerMove(row: number, column: number) {
    if (!this.hasGameEnded()) {
      let playerMove: Move = {
        gameId: this.game.gameId,
        position: {
          row: row,
          column: column,
        },
        playerType: PlayerType.PLAYER,
      };

      this.gameApiService
        .makeMove(playerMove)
        .subscribe((gameSnapshot: Game) => {
          this.makeMove(row, column, this.game.playerSymbol);
          this.game.gameStatus = gameSnapshot.gameStatus;
          this.game.computerMove = gameSnapshot.computerMove;
          this.makeComputerMove();
        });
    }
  }

  hasGameEnded(): boolean {
    return this.game && this.game.gameStatus !== GameStatus.RUNNING;
  }

  isCellEmpty(row: number, column: number) {
    return this.gameBoard[row][column] === "";
  }

  clearGame() {
    this.game = null;
    this.gameBoard.forEach((row) => row.fill(""));
  }

  private makeComputerMove() {
    let move = this.game.computerMove;
    if (move) {
      this.makeMove(
        move.position.row,
        move.position.column,
        this.computerSymbol
      );
    }
  }

  private makeMove(row: number, column: number, symbol: string) {
    this.gameBoard[row][column] = symbol;
  }

  private populateComputerSymbol() {
    this.computerSymbol =
      this.game.playerSymbol == PlayerSymbol.X
        ? PlayerSymbol.O
        : PlayerSymbol.X;
  }
}
