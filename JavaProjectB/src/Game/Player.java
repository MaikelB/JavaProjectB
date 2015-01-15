package Game;

import java.util.Random;

public class Player {
	static private int health=20, mana=10;
	static private String hero;

	/**
	 * Player(health, mana, hero) 
	 */
	public Player(int hp, int ma, String he){
		health = hp;
		mana = ma;
		hero = he;
	}
	
	/**
	 * Returns health of the player to be used in other classes
	 */
	static public int getHealth(){
		return health;
	}
	/**
	 * Returns mana of player to be used in other classes 
	 */
	static public int getMana(){
		return mana;
	}
	
	/**
	 * Used in other classes to change health of player
	 */
	static public void setHealth(int hp){
		health = hp;
	}
	
	/**
	 * sets mana of player.
	 * if it goes above 40, it is set back to 40
	 */
	static public void setMana(int ma){
		mana = ma;
		if (mana >40){
			mana = 40;
		}
	}
	/**
	 * Returns what random here is picked for the player.
	 */
	static public String getHero(){
		Random rn = new Random();
		int pickhero = rn.nextInt(3 - 1 + 1) + 1;
		switch (pickhero){
		case 1: hero = "deck1"; break;
		case 2: hero = "deck2"; break;
		case 3: hero = "deck3"; break;
		}
		return hero;
	}
}
