package Game;

import java.util.Random;

public class Enemy {
	static private int health=20, mana=10;
	static private String hero;

	/**
	 * Enemy(health, mana, hero)
	 */
	public Enemy(int hp, int ma, String he){
		health = hp;
		mana = ma;
		hero = he;
	}
	/** setHealth = sets the health of the player(can be used from another class)
	 */
	static public void setHealth(int hp){
		health = hp;
	}
	/** setMana = sets the mana(energy to place cards) of the Enemy.
	 * The first if is to check if the mana is above 40 and if it is, change it to 40.
	 */

	static public void setMana(int ma){
		mana = ma;
		if (mana >40){
			mana = 40;
		}
	}
	/** getHealth = returns player health.
	 */
	static public int getHealth(){
		return health;
	}
	/** getMana = returns the mana(energy to place cards) of the player.
	 */
	static public int getMana(){
		return mana;
	}
	/** getHero = Returns what hero is picked for the Enemy player.
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