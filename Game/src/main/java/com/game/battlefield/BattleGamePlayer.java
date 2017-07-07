/**
 * 
 */
package com.game.battlefield;

import java.util.Arrays;

/**
 * @author AvinashSingh
 *
 */
public class BattleGamePlayer implements Player {

	private int LOC_OFFSET = 3;
	private int ROW_OFFSET = 2; //Row to read values for ship details
	
	private int playerNum = 0;
	private int missiles = 0;
	private int battleStrength = 0;
	private int[][] battleField;
	private String[] targets;
	private BattleGamePlayer enemyPlayer;
	
	/**
	 * @param playerNum
	 * @param rowNum
	 * @param colNum
	 */
	public BattleGamePlayer(int playerNum, int rowNum, int colNum) {
		this.playerNum = playerNum;
		battleField = new int[rowNum][colNum];
	}

	@Override
	public boolean perform() {
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

	/**
	 * @return the playerNum
	 */
	public int getPlayerNum() {
		return playerNum;
	}

	/**
	 * @param playerNum the playerNum to set
	 */
	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	/**
	 * @return the missiles
	 */
	public int getMissiles() {
		return missiles;
	}

	/**
	 * @param missiles the missiles to set
	 */
	public void setMissiles(int missiles) {
		this.missiles = missiles;
	}

	/**
	 * @return the battleStrength
	 */
	public int getBattleStrength() {
		return battleStrength;
	}

	/**
	 * @param battleStrength the battleStrength to set
	 */
	public void setBattleStrength(int battleStrength) {
		this.battleStrength = battleStrength;
	}

	/**
	 * @return the battleField
	 */
	public int[][] getBattleField() {
		return battleField;
	}

	/**
	 * @param battleField the battleField to set
	 */
	public void setBattleField(int[][] battleField) {
		this.battleField = battleField;
	}

	/**
	 * @return the targets
	 */
	public String[] getTargets() {
		return targets;
	}

	/**
	 * @param targets the targets to set
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
	public BattleGamePlayer getEnemyPlayer() {
		return enemyPlayer;
	}

	/**
	 * @param enemyPlayer the enemyPlayer to set
	 */
	public void setEnemyPlayer(BattleGamePlayer enemyPlayer) {
		this.enemyPlayer = enemyPlayer;
	}

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
							if (BattleGame.BATTLEGROUNG_ROW_NUM < y
									|| BattleGame.BATTLEGROUNG_COL_NUM < x) {
								throw new Exception("Ship is outside battlefield, exiting...");
							}
							battleField[y][x] = ship.getCode();
							battleStrength += ship.getCode();
						}
					}
				}
			}
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
		BattleGamePlayer other = (BattleGamePlayer) obj;
		if (playerNum != other.playerNum)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BattleGamePlayer [LOC_OFFSET=" + LOC_OFFSET + ", ROW_OFFSET="
				+ ROW_OFFSET + ", playerNum=" + playerNum + ", missiles="
				+ missiles + ", battleStrength=" + battleStrength
				+ ", battleField=" + Arrays.toString(battleField)
				+ ", targets=" + Arrays.toString(targets) + ", enemyPlayer="
				+ enemyPlayer + "]";
	}

}
