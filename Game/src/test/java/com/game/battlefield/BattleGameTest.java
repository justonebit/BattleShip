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
public class BattleGameTest {

	String[][] input ;
	Player[] players;
	
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
		BattleGame battle = new BattleGame();
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
	 * Test method for {@link com.game.battlefield.BattleGame#continueGame(com.game.battlefield.Player[])}.
	 */
	@Test
	public void testContinueBattle() {
		BattleGame battle = new BattleGame();
		assertTrue(battle.continueGame(players));
	}

	/**
	 * Test method for {@link com.game.battlefield.BattleGame#isValidShipType(java.lang.String)}.
	 */
	@Test
	public void testCase1IsValidShipType() {
		BattleGame battle = new BattleGame();
		String shipType = "Q";
		assertTrue(battle.isValidShipType(shipType));
	}

	/**
	 * Test method for {@link com.game.battlefield.BattleGame#isValidShipType(java.lang.String)}.
	 */
	@Test
	public void testCase2IsValidShipType() {
		BattleGame battle = new BattleGame();
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
		BattleGame battle = new BattleGame();
		try {
			battle.validate(localInput);
		} catch (Exception e) {
			assertEquals("Invalid BATTLEGROUND COL input, exiting...", e.getMessage());
		}
	}

}
