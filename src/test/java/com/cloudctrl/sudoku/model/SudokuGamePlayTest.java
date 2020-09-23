package com.cloudctrl.sudoku.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.cloudctrl.sudoku.model.builder.SudokuBoardBuilder;
import com.cloudctrl.sudoku.model.builder.SudokuGameBuilder;
import com.cloudctrl.sudoku.model.data.SudokuGames;

public class SudokuGamePlayTest {

    @Test
    public void testFirstEasyGame() {
        SudokuGame game = SudokuGames.getEasyGames().get(0);
        testGame(game);
    }

    @Test
    public void testFirstVeryHardGame() {
        SudokuGame game = SudokuGames.getVeryHardGames().get(0);
        
        SudokuGameState state = new SudokuInitialState(game);
        assertFalse(state.isSolved());
        assertTrue(state.hasValidOptions());
        
        System.out.println(state.getPossibleMoves());
        testGame(game);
    }
    
    @Test
    public void testAllVeryHardGames() {
    	testGames(SudokuGames.getVeryHardGames());
    }
    
    @Test
    public void testGameWithGuesses() {
    	
    	var builder = new SudokuGameBuilder();
    	builder.initFromNumberLine("020600080000020504008070306700106803350080041801703002209010400105060000070005010");
    	var game = builder.newGame();
    	
        AutoPlayer player = new AutoPlayer(game);
        while (!player.isSolved()) {
            player.doNextMove();
            System.out.println(player.getCurrentStep().getSolvedCells().size() + " | " + player.getCurrentStep().getLastMove() + "\n");
            System.out.println(player.getCurrentStep());
        }
       	System.out.println(player.toHistoryString());
    }
    
    public void testGames(List<SudokuGame> games) {
    	for(SudokuGame game : games) {
    		testGame(game);
    	}
    }
    
    public void testGame(SudokuGame game) {
        AutoPlayer player = new AutoPlayer(game);
        while (!player.isSolved()) {
            player.doNextMove();
        }
        System.out.println(player.getCurrentStep());
        if (player.hasBadGuesses()) {
        	System.out.println(player.toHistoryString());
        }
    }
    
    
}
