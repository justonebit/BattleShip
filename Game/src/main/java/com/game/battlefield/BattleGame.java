/**
 * 
 */
package com.game.battlefield;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author AvinashSingh
 *
 */
public class BattleGame implements Game<String[][]> {

	public static String inputFile = "d://input.txt";

	public static String INPUT[][];
	public static int BATTLEGROUNG_ROW_NUM = -1;
	public static int BATTLEGROUNG_COL_NUM = -1;
	public static int INPUT_ROW_NUM_BATTLEGROUNG = 0;
	public static int INPUT_ROW_NUM_BATTLESHIP = 1;
	public static int TOTAL_PLAYERS = 2;
	
	@Override
	public Player[] configure(String[][] inputObject) throws Exception {
		
		Player[] players = {};
		try {
			if(inputObject.length == 0)
				INPUT = createInputObjectFromFile(Paths.get(inputFile));
			else
				INPUT = inputObject;
			
			validate(INPUT);

			players = new BattleGamePlayer[] {
					new BattleGamePlayer(1, BATTLEGROUNG_ROW_NUM, BATTLEGROUNG_COL_NUM),
					new BattleGamePlayer(2, BATTLEGROUNG_ROW_NUM, BATTLEGROUNG_COL_NUM) };

			setUpPlayer(players);

		} catch (Exception exp) {
			System.out.println(exp.getMessage());
		}
		
		return players;
	}

	@Override
	public boolean validate(String[][] inputObject) throws Exception {

		try {
			Integer.parseInt(INPUT[0][0]);
		} catch (NumberFormatException e) {
			throw new Exception("Invalid BATTLEGROUND COL input, exiting...");
		}
		if (INPUT[0][1].length() > 1
				|| !Character.isAlphabetic(INPUT[0][1].charAt(0))) {
			throw new Exception("Invalid BATTLEGROUND ROW input, exiting...");
		}

		BATTLEGROUNG_ROW_NUM = (int) INPUT[0][1].toUpperCase().toCharArray()[0] - 64;
		BATTLEGROUNG_COL_NUM = Integer.parseInt(INPUT[0][0]);

		if (BATTLEGROUNG_ROW_NUM > 26 || BATTLEGROUNG_ROW_NUM < 1) {
			throw new Exception("BATTLEGROUND ROW input doesnt conform to standard size, exiting...");
		}
		if (BATTLEGROUNG_COL_NUM > 9 || BATTLEGROUNG_COL_NUM < 0) {
			throw new Exception("Invalid BATTLEGROUND COL input doesnt conform to standard size, exiting...");
		}
		if (Integer.parseInt(INPUT[INPUT_ROW_NUM_BATTLESHIP][0]) < 1
				|| Integer.parseInt(INPUT[INPUT_ROW_NUM_BATTLESHIP][0]) > BATTLEGROUNG_ROW_NUM * BATTLEGROUNG_COL_NUM) {
			throw new Exception("Invalid BATTLESHIP number against given BATTLEGROUND, exiting...");
		}
		
		for (int rowNum = 2; rowNum < INPUT.length - TOTAL_PLAYERS; rowNum++) {
			INPUT[rowNum][0] = INPUT[rowNum][0].toUpperCase();
			if (!isValidShipType(INPUT[rowNum][0])) {
				throw new Exception(INPUT[rowNum][0] + " : Invalid Ship Type value provided, exiting...");
			}
			try {
				Integer.parseInt(INPUT[rowNum][1]);
				Integer.parseInt(INPUT[rowNum][2]);
			} catch (NumberFormatException e) {
				throw new Exception("Invalid Ship dimension provided, exiting...");
			}
			if (Integer.valueOf(INPUT[rowNum][1]) > BATTLEGROUNG_COL_NUM) {
				throw new Exception("Ship width is more than battleground width, exiting...");
			}
			if (Integer.valueOf(INPUT[rowNum][2]) > BATTLEGROUNG_ROW_NUM) {
				throw new Exception("Ship height is more than battleground height, exiting...");
			}
			if (INPUT[rowNum].length - 3 != TOTAL_PLAYERS) {
				throw new Exception(
						(rowNum + 1)
								+ "row has invalid number Ship location positions for all players, exiting...");
			}
			for (int colNum = 3; colNum < INPUT[rowNum].length; colNum++) {
				int x = (int) INPUT[rowNum][colNum].charAt(0) - 64;
				Character y = INPUT[rowNum][colNum].charAt(1);
				INPUT[rowNum][colNum] = String.valueOf(x) + ","	+ String.valueOf(y);
			}
		}
		
		return true;
	}

	@Override
	public void startGame(Player[] players) {
		while (continueGame(players)) {
			for (Player player : players) {
				while (((BattleGamePlayer)player).perform())
					;
			}
		}		
	}

	@Override
	public boolean continueGame(Player[] players) {
		for (Player player : players) {
			if (((BattleGamePlayer)player).getMissiles() == 0 && ((BattleGamePlayer)player).getBattleStrength() == 0) {
				return false;
			}
		}
		if(players.length > 0)
			return true;
		else
			return false;		
	}

	/**
	 * @param path
	 * @return
	 * @throws IOException
	 * Comment : Create 2D Matrix from input file dynamically i.e. array 
	 * 			 dimensions are not hard coded and it depends on input provided.
	 */
	public String[][] createInputObjectFromFile(Path path)
			throws IOException {
		return Files.lines(path)
				.map((line) -> line.trim().split("\\s+"))
				.toArray(String[][]::new);
	}
	
	public void setUpPlayer(Player[] players) throws Exception {
		for (int cnt = 0; cnt < players.length; cnt++) {
			setUpTarget((BattleGamePlayer)players[cnt]);
			setUpBattleField((BattleGamePlayer)players[cnt]);
			setUpEnemyPlayer((BattleGamePlayer)players[cnt], players);
		}		
	}
	
	public void setUpTarget(BattleGamePlayer player) {
		player.setTargets(INPUT[INPUT.length - TOTAL_PLAYERS + player.getPlayerNum() - 1]);
	}
	
	public void setUpBattleField(BattleGamePlayer player) throws Exception {
		player.setBattleField(INPUT, TOTAL_PLAYERS);
	}
	
	public void setUpEnemyPlayer(BattleGamePlayer player, Player[] players) {
		if (player.getPlayerNum() == players.length) {
			player.setEnemyPlayer((BattleGamePlayer)players[0]);
		} else {
			player.setEnemyPlayer((BattleGamePlayer)players[player.getPlayerNum()]);
		}
	}
	
	/**
	 * @param shipType
	 * @return
	 * Comment : Check if given ship type belong to valid set i.e. among P & Q. 
	 *           In future can increase more ship types here with respective strengths.
	 */
	public boolean isValidShipType(String shipType) {
		for (ShipType ship : ShipType.values()) {
			if (ship.name().equals(shipType)) {
				return true;
			}
		}
		return false;
	}
	
}
