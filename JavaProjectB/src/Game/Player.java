package Game;

public class Player {
	private int health, mana;

	public Player(int hp, int ma){
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
		return mana;
	}


}
