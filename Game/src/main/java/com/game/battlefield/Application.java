/**
 * 
 */
package com.game.battlefield;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author AvinashSingh
 *
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) throws Exception {
		ApplicationContext appContext = SpringApplication.run(Application.class, args);
		
		Game battle = new BattleGame();
		String[][] input = {};
		Player[] players = battle.configure(input);
		// System.out.println(Arrays.deepToString(players));
		battle.startGame(players);
		// System.out.println(Arrays.deepToString(players));
	}

}
