package Game;

public class Enemy {
	private int health=20, mana=10, hero;

	public Enemy(int hp, int ma, int he){
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
	public void setHealth(int hp){
		health = hp;
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
		he = (int) Math.random()*3 + 1;
	}
	public int getHere(){
		return hero;
	}
	
}
