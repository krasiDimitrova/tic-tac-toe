import { Component, Output, OnInit, EventEmitter } from "@angular/core";
import { FormGroup, FormBuilder } from "@angular/forms";
import { GameApiService } from "../services/game-api.service";
import { Game } from "../models/game";
import { PlayerSymbol } from "../models/player";

@Component({
  selector: "app-start-game",
  templateUrl: "./start-game.component.html",
  styleUrls: ["./start-game.component.scss"],
})
export class StartGameComponent implements OnInit {
  constructor(
    private gameApiService: GameApiService,
    private formBuilder: FormBuilder
  ) {}

  @Output() startedGame: EventEmitter<Game> = new EventEmitter();

  startGameForm: FormGroup;

  err: String;

  readonly playerSymbols = PlayerSymbol;

  ngOnInit() {
    this.startGameForm = this.formBuilder.group({
      playerSymbol: [null],
    });
  }

  onSubmit() {
    let symbol = this.startGameForm.value.playerSymbol;
    this.gameApiService.startGame(symbol).subscribe(
      (game: Game) => {
        game.playerSymbol = symbol;
        this.startedGame.emit(game);
      },
      (err) => (this.err = err.message)
    );
  }
}
