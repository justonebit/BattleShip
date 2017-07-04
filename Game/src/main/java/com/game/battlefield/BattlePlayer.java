/**
 * 
 */
package com.game.battlefield;

import java.util.Arrays;

/**
 * @author AvinashSingh
 *
 */
public class BattlePlayer {

	private int LOC_OFFSET = 3;
	private int ROW_OFFSET = 2; //Row to read values for ship details
	
	private int playerNum = 0;
	private int missiles = 0;
	private int battleStrength = 0;
	private int[][] battleField;
	private String[] targets;
	private BattlePlayer enemyPlayer;

	/**
	 * @param playerNum
	 * @param rowNum
	 * @param colNum
	 */
	public BattlePlayer(int playerNum, int rowNum, int colNum) {
		this.playerNum = playerNum;
		battleField = new int[rowNum][colNum];
	}
	
	/**
	 * @param playerNum
	 * @param rowNum
	 * @param colNum
	 * @return
	 * Comment : STATIC FACTORY METHOD
	 */
	public static BattlePlayer newInstance(int playerNum, int rowNum, int colNum) {
		return new BattlePlayer(playerNum, rowNum, colNum);
	}
	
	/**
	 * @return the playerNum
	 */
	public int getPlayerNum() {
		return playerNum;
	}
	
	/**
	 * @return the missiles
	 */
	public int getMissiles() {
		return missiles;
	}
	
	/**
	 * @return the battleStrength
	 */
	public int getBattleStrength() {
		return battleStrength;
	}

	/**
	 * @param battleStrength
	 *            the battleStrength to set
	 */
	public void setBattleStrength(int battleStrength) {
		this.battleStrength = battleStrength;
	}
	
	/**
	 * @return the targets
	 */
	public String[] getTargets() {
		return targets;
	}
	
	/**
	 * @param targets
	 *            the targets to set
	 */
	public void setTargets(String[] targets) {
		this.targets = targets;
		this.missiles = targets.length;
		for (int cnt = 0; cnt < targets.length; cnt++) {
			int x = (int) targets[cnt].charAt(0) - 64;
			char y = targets[cnt].charAt(1);
			targets[cnt] = String.valueOf(x) + "," + String.valueOf(y);
		}
	}
	
	/**
	 * @return the enemyPlayer
	 */
	public BattlePlayer getEnemyPlayer() {
		return enemyPlayer;
	}

	/**
	 * @param enemyPlayer
	 *            the enemyPlayer to set
	 */
	public void setEnemyPlayer(BattlePlayer enemyPlayer) {
		this.enemyPlayer = enemyPlayer;
	}
	
	/**
	 * @return the battleField
	 */
	public int[][] getBattleField() {
		return battleField;
	}

	/**
	 * @param input
	 * @param totalPlayers
	 * @throws Exception
	 */
	public void setBattleField(String[][] input, int totalPlayers)
			throws Exception {
		for (int cnt = ROW_OFFSET; cnt < input.length - totalPlayers; cnt++) {
			String shipType = input[cnt][0];
			for (ShipType ship : ShipType.values()) {
				if (ship.name().equals(shipType)) {
					String[] location = input[cnt][LOC_OFFSET + playerNum - 1]
							.split(",");
					int xLoc = Integer.parseInt(location[0]);
					int yLoc = Integer.parseInt(location[1]);
					int width = Integer.parseInt(input[cnt][1]);
					int height = Integer.parseInt(input[cnt][2]);
					for (int x = xLoc - 1; x < xLoc + width - 1; x++) {
						for (int y = yLoc - 1; y < yLoc + height - 1; y++) {
							if (battleField[y][x] != 0) {
								throw new Exception("Overlapping ship positions provided, exiting...");
							}
							if (Battle.BATTLEGROUNG_ROW_NUM < y
									|| Battle.BATTLEGROUNG_COL_NUM < x) {
								throw new Exception("Ship is outside battlefield, existing...");
							}
							battleField[y][x] = ship.getCode();
							battleStrength += ship.getCode();
						}
					}
				}
			}
		}
	}

	/**
	 * @return
	 */
	public boolean fireMissile() {
		if (missiles < 1) {
			System.out.println("Player-" + playerNum + " has no more missiles left to launch.");
			return false;
		} else if (enemyPlayer.getBattleStrength() < 1) {
			System.out.println("Player-" + playerNum + " won the battle.");
			return false;
		} else {
			String[] location = targets[targets.length - missiles].split(",");
			int xLoc = Integer.parseInt(location[0]);
			int yLoc = Integer.parseInt(location[1]);
			if (enemyPlayer.getBattleField()[xLoc - 1][yLoc - 1] > 0) {
				enemyPlayer.getBattleField()[xLoc - 1][yLoc - 1] -= 1;
				enemyPlayer.setBattleStrength(enemyPlayer.getBattleStrength() - 1);
				missiles = missiles - 1;
				System.out.println("Player-" + playerNum
						+ " fires a missile with target " + (char) (xLoc + 64)
						+ yLoc + " which got hit.");
				return true;
			} else if (enemyPlayer.getBattleField()[xLoc - 1][yLoc - 1] == 0) {
				System.out.println("Player-" + playerNum
						+ " fires a missile with target " + (char) (xLoc + 64)
						+ yLoc + " which got miss.");
				missiles = missiles - 1;
				return false;
			}
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + playerNum;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BattlePlayer other = (BattlePlayer) obj;
		if (playerNum != other.playerNum)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("Player [playerNum=" + playerNum + ", missiles=" + missiles
				+ ", targets=" + Arrays.deepToString(targets) + ", battleField=\n") ;
		for(int[] field: battleField) 
			str.append(Arrays.toString(field) +"\n");
		return str.toString();
	}

}