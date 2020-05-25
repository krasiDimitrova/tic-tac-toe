package com.example.kpk.tictactoe.controllers;

import com.example.kpk.tictactoe.dtos.*;
import com.example.kpk.tictactoe.models.PlayerSymbol;
import com.example.kpk.tictactoe.services.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
    
    private GameService gameService;

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/game/start", params = {"symbol"})
    public GameDTO startGame(@RequestParam PlayerSymbol symbol) {
        return gameService.startGame(symbol);
    }
    
    
    @RequestMapping(method = RequestMethod.POST, value = "/game/move")
    public GameDTO makeMove(@RequestBody MoveDTO moveDTO) {
        return gameService.makeMove(moveDTO);
    }
}