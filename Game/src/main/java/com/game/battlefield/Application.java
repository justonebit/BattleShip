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
		
		Battle battle = new Battle();
		String[][] input = {};
		BattlePlayer[] players = battle.configure(input);
		// System.out.println(Arrays.deepToString(players));
		battle.startBattle(players);
		// System.out.println(Arrays.deepToString(players));
	}

}