/**
 * 
 */
package com.game.battlefield;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author AvinashSingh
 *
 */
public interface Game<T> {

	Player[] configure(T inputObject) throws Exception;
	boolean validate(T inputObject) throws Exception;
	T createInputObjectFromFile(Path path) throws IOException;
	void startGame(Player[] players);
	boolean continueGame(Player[] players);
	
}
