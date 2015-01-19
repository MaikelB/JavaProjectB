package Game;

public class Card {
	
	/** String card_name = holds the name of the card */
	public String card_name;
	
	/** String card_desc = holds the information of the card in text format */
	public String card_desc; 
	
	private Boolean showBack = true;
	
	/** int special = holds the special of the card in number format 
	 * 0 = nothing
	 * 1 = spell power = 10 mana
	 * 2 = defense up = 10 mana
	 * 3 = attack up = 10 mana
	 * 4 = spell	 * 
	 * 5 = taunt = 5 mana
	 */
	private int special;
	
	/** int[] card_stats = holds the attack and defense values
	 * card_stats[0] = attack
	 * card_stats[1] = defense
	 */
	public int[] card_stats; 
	
	/** int rarity = holds the rarity number of the card. numbers are below from worst to best
	 * 0 = common (white) 5
	 * 1 = uncommon (blue) 10 mana
	 * 2 = rare (purple) = 15 mana
	 * 3 = legendary (gold) = 20 mana
	 * 4 = GOD-LIKE (pink) = 30 mana
	 */
	private int rarity;
	/** Card( Name, Description, Special ability number, Array with attack&defense ) */
	public Card(String nm, String desc, int spec, int[] st, int rar) {
		card_stats = new int[2];
		card_name 	= nm;
		card_desc 	= desc;
		special		= spec;
		card_stats[0]	= st[0];
		card_stats[1]	= st[1];
		rarity		= rar;
	}
	
	/** getSpecial() returns an integer for what special the card has
	 * if none, return null
	 */
	public int getSpecial() {
		return special;
	}
	
	/** getRarity() returns an integer for what rarity the card has
	 * if none, return null
	 */
	public int getRarity() {
		return rarity;
	}
	
	/** buffAttack( number of attack gameEngine needs to buff )
	 * Adds #att to the attack of the card
	 */
	public void buffAttack(int att) {
		card_stats[0] += att;
	}
	
	/** debuffAttack( number of attack gameEngine needs to debuff )
	 * removes #att attack of the card
	 */
	public void debuffAttack(int att) {
		card_stats[0] -= att;
	}
	
	/** buffDefense( number of defense gameEngine needs to buff )
	 * Adds #def to the  of the card
	 */
	public void buffDefense(int def) {
		card_stats[1] += def;
	}
	
	/** debuffDefense( number of defense gameEngine needs to buff )
	 * removes #def defense of the card
	 */
	public void debuffDefense(int def) {
		card_stats[1] -= def;
	}

	public Boolean getShowBack() {
		return showBack;
	}

	public void setShowBack(Boolean showBack) {
		this.showBack = showBack;
	}
	public int getAttack(){
		return card_stats[0];
	}
	public int getDefense(){
		return card_stats[1];
	}
	public int getManaCost() {
		int[] r = new int[5];
		r[0] = 5;
		r[1] = 10;
		r[2] = 15;
		r[3] = 20;
		r[4] = 30;
		int[] sp = new int[6];
		sp[0] = 0;
		sp[1] = 10;
		sp[2] = 10;
		sp[3] = 10;
		sp[4] = 0;
		sp[5] = 5;
		return r[rarity]+sp[special];
	}
}
