import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { StartGameComponent } from "./start-game.component";
import { of } from "rxjs";
import { PlayerSymbol } from "../models/player";
import { Game, GameStatus } from "../models/game";
import { NO_ERRORS_SCHEMA } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { GameApiService } from "../services/game-api.service";

describe("StartGameComponent", () => {
  let component: StartGameComponent;
  let fixture: ComponentFixture<StartGameComponent>;

  const playerSymbol = PlayerSymbol.O;
  const testGame: Game = {
    gameId: 1,
    gameStatus: GameStatus.RUNNING,
    computerMove: null,
    playerSymbol: playerSymbol,
  };

  const gameApiServiceStub = {
    startGame: () => of(testGame),
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientTestingModule,
      ],
      declarations: [StartGameComponent],
      providers: [{ provide: GameApiService, useValue: gameApiServiceStub }],
      schemas: [NO_ERRORS_SCHEMA],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StartGameComponent);
    component = fixture.componentInstance;
    spyOn(component.startedGame, "emit");
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });

  it("should init form value with null", () => {
    expect(component.startGameForm.value.playerSymbol).toBeNull();
  });

  it("should emit created game on form submit", () => {
    component.startGameForm.value.playerSymbol = playerSymbol;
    component.onSubmit();
    expect(component.startedGame.emit).toHaveBeenCalledWith(testGame);
  });
});
