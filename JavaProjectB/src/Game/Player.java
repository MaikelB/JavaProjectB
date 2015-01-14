package Game;

public class Player {
	private int health=20, mana=10;
	private String hero;

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
	public int getHealth(){
		return health;
	}
	/**
	 * Returns mana of player to be used in other classes 
	 */
	public int getMana(){
		return mana;
	}
	
	/**
	 * Used in other classes to change health of player
	 */
	public void setHealth(int he){
		health = he;
	}
	
	/**
	 * sets mana of player.
	 * if it goes above 40, it is set back to 40
	 * player gets +10 mana next turn.
	 */
	public void setMana(int ma){
		mana = ma;
		if (ma >40){
			ma = 40;
		}
		//if (next turn){
		//ma = ma+10;
		//}
	}
	/**
	 * Is called from another class to set player hero
	 */
	public void setHero(String he){
		hero = he;
	}
	/**
	 * Returns what hero the player has.
	 */
	public String getHero(){
		return hero;
	}
}
