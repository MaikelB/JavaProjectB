package Game;

public class Player {
	private int health=20, mana=10, hero;

	public Player(int hp, int ma, int he){
		health = hp;
		mana = ma;
		hero = he;
	}
	
	public int getHealth(){
		return health;
	}
	public int getMana(){
		return mana;
	}
	public void setHealth(int he){
		health = he;
	}
	public void setMana(int ma){
		mana = ma;
		if (ma >40){
			ma = 40;
		}
		//if (next turn){
		//ma = ma+10;
		//}
	}
	public void setHero(int he){
		hero = he;
	}
	public int getHere(){
		return hero;
	}
}
