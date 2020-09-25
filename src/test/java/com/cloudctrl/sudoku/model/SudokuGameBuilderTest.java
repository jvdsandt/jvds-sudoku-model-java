package com.cloudctrl.sudoku.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cloudctrl.sudoku.model.builder.SudokuGameBuilder;

public class SudokuGameBuilderTest {

    @Test
    public void testUnsolvableGame() {
    	
    	var builder = new SudokuGameBuilder();
    	builder.initFromNumberLine("241000070900205340005000806700004050100302009060500004407000500032806007010000420");
    	var game = builder.newGame();
    	
        AutoPlayer player = new AutoPlayer(game);
        try {
        	player.solve();
        	assertTrue("exception expected", false);
        } catch (InvalidGameException ex) {
        	// ok
        }
    }

    @Test
    public void testGameWithMultipleSolutions() {
    	
    	var builder = new SudokuGameBuilder();
    	builder.initFromNumberLine("000000000000000000000000000000000000000000000000000000000000000000000000000000000");
    	var game = builder.newGame();
    	
        AutoPlayer player = new AutoPlayer(game);
        player.solve();
    }
    
    
}
