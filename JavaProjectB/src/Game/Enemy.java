package Game;

public class Enemy {
	private int health=20, mana=10;
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
	public void setHealth(int hp){
		health = hp;
	}
	/** setMana = sets the mana(energy to place cards) of the Enemy.
	 * The first if is to check if the mana is above 40 and if it is, change it to 40.
	 * the 2nd if is to give the player +10 mana after his/her turn.
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
	/** setHero = This uses Math.random to pick a random number between 1 and 3 and then puts it through the switch statement to
	 * decide which deck is picked. the Main uses this to load the enemy deck.
	 */
	public void setHero(String he){
		hero = he;
		int pickhero = (int) Math.random()*2 + 1;
		switch (pickhero){
		case 1: he = "deck1"; break;
		case 2: he = "deck2"; break;
		}
	
	}
	/** getHealth = returns player health.
	 */
	public int getHealth(){
		return health;
	}
	/** getMana = returns the mana(energy to place cards) of the player.
	 */
	public int getMana(){
		return mana;
	}
	/** getHero = Returns what hero is picked for the Enemy player.
	 */
	static public String getHero(){
		return hero;
	}


}
