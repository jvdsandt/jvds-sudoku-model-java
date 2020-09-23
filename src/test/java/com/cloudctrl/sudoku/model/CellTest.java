package com.cloudctrl.sudoku.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class CellTest {
	
	@Test
	public void test() {
		var c1 = new Cell(4, 4);
		assertEquals(new Cell(4, 4), c1);
		assertNotEquals(new Cell(2, 4), c1);
	}

	@Test
	public void testCompare() {
		assertEquals(0, new Cell(4, 4).compareTo(new Cell(4, 4)));
		assertEquals(-1, new Cell(4, 4).compareTo(new Cell(3, 5)));
		assertEquals(1, new Cell(4, 4).compareTo(new Cell(5, 3)));
	}
}
