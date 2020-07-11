import { NgModule } from "@angular/core";

import { BrowserModule } from "@angular/platform-browser";
import { AppComponent } from "./app.component";
import { GameComponent } from "./game/game.component";
import { StartGameComponent } from "./start-game/start-game.component";
import { HttpClientModule } from "@angular/common/http";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { AppRoutingModule } from "./app-routing.module";
import { GameApiService } from "./services/game-api.service";

@NgModule({
  declarations: [AppComponent, GameComponent, StartGameComponent],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
  ],
  providers: [GameApiService],
  bootstrap: [AppComponent],
})
export class AppModule {}
