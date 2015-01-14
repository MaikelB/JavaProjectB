package Game;

public class Player {
	private int health=20, mana=10;

	public Player(int hp, int ma){
		health = hp;
		mana = ma;
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
	public int getHealth(){
		return health;
	}
	public int getMana(){
		return mana;
	}
}
