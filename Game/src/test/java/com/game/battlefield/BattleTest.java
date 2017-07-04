/**
 * 
 */
package com.game.battlefield;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author AvinashSingh
 *
 */
public class BattleTest {

	String[][] input ;
	BattlePlayer[] players;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {		
		input = new String[][] {
				{"5","E"},
				{"2"},
				{"Q","1","1","A1","B2"},
				{"P","2","1","D4","C3"},
				{"A1","B2","B2","B3"},
				{"A1","B2","B3","A1","D1","E1","D4","D4","D5","D5"}			
		};
		Battle battle = new Battle();
		players = battle.configure(input);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		input = null;
		players = null;
	}

	/**
	 * Test method for {@link com.game.battlefield.Battle#continueBattle(com.game.battlefield.BattlePlayer[])}.
	 */
	@Test
	public void testContinueBattle() {
		Battle battle = new Battle();
		assertTrue(battle.continueBattle(players));
	}

	/**
	 * Test method for {@link com.game.battlefield.Battle#isValidShipType(java.lang.String)}.
	 */
	@Test
	public void testCase1IsValidShipType() {
		Battle battle = new Battle();
		String shipType = "Q";
		assertTrue(battle.isValidShipType(shipType));
	}

	/**
	 * Test method for {@link com.game.battlefield.Battle#isValidShipType(java.lang.String)}.
	 */
	@Test
	public void testCase2IsValidShipType() {
		Battle battle = new Battle();
		String shipType = "a";
		assertFalse(battle.isValidShipType(shipType));
	}
	
	/**
	 * Test method for {@link com.game.battlefield.Battle#validateInput(java.lang.String[][])}.
	 */
	@Test
	public void testCase1ValidateInput() {
		String[][] localInput = new String[][] {
				{"5Q","E"},
				{"2"},
				{"Q","1","1","A1","B2"},
				{"P","2","1","D4","C3"},
				{"A1","B2","B2","B3"},
				{"A1","B2","B3","A1","D1","E1","D4","D4","D5","D5"}			
		};
		Battle battle = new Battle();
		try {
			battle.validateInput(localInput);
		} catch (Exception e) {
			assertEquals("Invalid BATTLEGROUND COL input, exiting...", e.getMessage());
		}
	}

}
