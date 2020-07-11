import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { PlayerSymbol } from "../models/player";
import { Observable } from "rxjs";
import { Game } from "../models/game";
import { Move } from "../models/move";

@Injectable()
export class GameApiService {
  private gameUrl: string;

  constructor(private http: HttpClient) {
    this.gameUrl = "http://localhost:8080/game";
  }

  startGame(playerSymbol: PlayerSymbol): Observable<Game> {
    return this.http.get<Game>(`${this.gameUrl}/start?symbol=${playerSymbol}`);
  }

  makeMove(move: Move): Observable<Game> {
    return this.http.post<Game>(`${this.gameUrl}/move`, move);
  }
}
