import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { of } from "rxjs";
import { cloneDeep } from "lodash";
import { PlayerSymbol, PlayerType } from "../models/player";
import { Game, GameStatus } from "../models/game";
import { NO_ERRORS_SCHEMA } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { GameApiService } from "../services/game-api.service";
import { GameComponent } from "./game.component";
import { Move } from "../models/move";

describe("GameComponent", () => {
  let component: GameComponent;
  let fixture: ComponentFixture<GameComponent>;

  const playerSymbol = PlayerSymbol.O;
  const computerMove: Move = {
    gameId: 1,
    position: {
      row: 1,
      column: 1,
    },
    playerType: PlayerType.COMPUTER,
  };
  const testGame: Game = {
    gameId: 1,
    gameStatus: GameStatus.RUNNING,
    computerMove: computerMove,
    playerSymbol: playerSymbol,
  };

  const gameApiServiceStub = {
    startGame: () => of(testGame),
    makeMove: jasmine.createSpy("makeMove"),
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [BrowserModule, HttpClientTestingModule],
      declarations: [GameComponent],
      providers: [{ provide: GameApiService, useValue: gameApiServiceStub }],
      schemas: [NO_ERRORS_SCHEMA],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });

  it("should init game board with empty strings", () => {
    expect(component.gameBoard.length).toBeTruthy(3);
    component.gameBoard.forEach((row) => {
      expect(row.length).toBeTruthy(3);
      row.forEach((cell) => expect(cell).toBe(""));
    });
  });

  it("should populate game and make computer move with the correct computer symbol", () => {
    component.populateGame(testGame);
    expect(component.game).toBe(testGame);
    expect(component.gameBoard[1][1]).toBe(PlayerSymbol.X);
  });

  it("should make player move and corresponding computer move", () => {
    component.populateGame(testGame);
    const currentGame = cloneDeep(testGame);
    currentGame.computerMove.position = { row: 1, column: 2 };
    gameApiServiceStub.makeMove.and.callFake(() => of(currentGame));
    component.makePlayerMove(0, 0);
    expect(component.gameBoard[0][0]).toBe(playerSymbol);
    expect(component.gameBoard[1][2]).toBe(PlayerSymbol.X);
    expect(component.game).toEqual(currentGame);
  });

  it("should not make player move if game is ended", () => {
    const currentGame = cloneDeep(testGame);
    currentGame.gameStatus = GameStatus.LOST;
    component.populateGame(currentGame);
    component.makePlayerMove(0, 0);
    expect(component.gameBoard[0][0]).toBe("");
  });

  it("should check the game status", () => {
    component.populateGame(testGame);
    expect(component.hasGameEnded()).toBeFalsy();
    const currentGame = cloneDeep(testGame);
    currentGame.gameStatus = GameStatus.LOST;
    gameApiServiceStub.makeMove.and.callFake(() => of(currentGame));
    component.makePlayerMove(0, 0);
    expect(component.hasGameEnded()).toBeTruthy();
  });

  it("should check if cell is empty", () => {
    component.populateGame(testGame);
    const position = testGame.computerMove.position;
    expect(component.isCellEmpty(position.row, position.column)).toBeFalsy();
    expect(component.isCellEmpty(1, 1)).toBeTruthy();
  });
});
