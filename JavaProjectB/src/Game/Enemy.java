package Game;

public class Enemy {
	private int health=20, mana=10;

	public Enemy(int hp, int ma){
		health = hp;
		mana = ma;
	}
	
	public int getHealth(){
		return health;
	}
	public int getMana(){
		return mana;
	}
	public void setHealth(){
		
	}
	public void setMana(){
		if (mana >40){
			mana = 40;
		}
		//if (next turn){
		//mana = mana+10;
		//}
	}
	
}
