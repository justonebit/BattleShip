/**
 * 
 */
package com.game.battlefield;

/**
 * @author AvinashSingh
 *
 */
public enum ShipType {
	P(1), Q(2);
	ShipType(int code) {
		this.code = code;
	}

	private int code;

	public int getCode() {
		return this.code;
	}
}