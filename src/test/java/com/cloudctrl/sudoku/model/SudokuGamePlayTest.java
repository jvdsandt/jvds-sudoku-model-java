package com.cloudctrl.sudoku.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

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
        
        GameState state = new GameInitialState(game);
        assertFalse(state.isSolved());
        assertTrue(state.hasValidOptions());
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
        player.solve();
        //assertFalse(player.getBadGuesses().isEmpty());
        assertEquals("524639187637821594918574326792146853356982741841753962269318475185467239473295618", player.getCurrentStep().asNumberLine());
    }
    
    @Test
    public void testManualPlay() {
    	
    	var builder = new SudokuGameBuilder();
    	builder.initFromNumberLine("041000070900205340005000806700004050100302009060500004407000500032806007010000420");
    	var game = builder.newGame();
    	
        AutoPlayer player = new AutoPlayer(game);
        assertTrue(player.isMovePossible(new Cell(1,1), 2));
        assertTrue(player.isMovePossible(new Cell(1,1), 8));
        assertFalse(player.isMovePossible(new Cell(1,1), 5));
        assertFalse(player.isMovePossible(new Cell(2,1), 5));

        // start with two bad moves 
        player.doManualMove(new Cell(1, 1), 2);
        player.doManualMove(new Cell(1, 9), 8);
        player.solve();
        assertEquals("641938275978265341325417896783694152154372689269581734497123568532846917816759423", player.getCurrentStep().asNumberLine());
    }
    
    public void testGames(List<SudokuGame> games) {
    	for(SudokuGame game : games) {
    		testGame(game);
    	}
    }
    
    public void testGame(SudokuGame game) {
        AutoPlayer player = new AutoPlayer(game);
        while (!player.isSolved()) {
            player.doAutoMove();
        }
    }
    
    
}
