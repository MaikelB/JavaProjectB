package Game;

public class Card {
	
	/** String card_name = holds the name of the card */
	@SuppressWarnings("unused")
	public String card_name;
	
	/** String card_desc = holds the information of the card in text format */
	@SuppressWarnings("unused")
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
		switch(special) {
		case 0:		System.out.println("Nothing");
					break;
		case 1:		System.out.println("Spell Power");
					break;
		case 2:		System.out.println("Defense up");
					break;
		case 3:		System.out.println("attack up");
					break;
		case 4:		System.out.println("Spell");
					break;
		case 5:		System.out.println("Taunt");
					break;
		default: 	System.out.println("Wrong input ()");
					return 3058;
		}
		return special;
	}
	
	/** getRarity() returns an integer for what rarity the card has
	 * if none, return null
	 */
	public int getRarity() {
		/*switch(rarity) {
		case 0:		System.out.println("common");
					break;
		case 1:		System.out.println("uncommon");
					break;
		case 2:		System.out.println("rare");
					break;
		case 3:		System.out.println("legendary");
					break;
		case 4:		System.out.println("god-like");
					break;
		default: 	System.out.println("Wrong input (LINE 79 CARD.JAVA)");
					return 3079;
		}*/
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
}
