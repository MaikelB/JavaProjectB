package Game;

public class Enemy {
	private int health, mana;

	public Enemy(int hp, int ma){
		health = hp;
		mana = ma;
	}
	
	public int Health(){
		health = 20;
		return health;
	}
	public int Mana(){
		mana = 10;
		if (mana >40){
			mana = 40;
		}
		//if (next turn){
		//mana = mana+10;
		//}
		return mana;
	}
}
