package Game;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Scanner;

public class FileHandler {
	private List<String[]> decks = new ArrayList<String[]>();
	private String[] deck1 = { "deck1", "Decks\\Deck1.data"};
	private String dn, dc, cn, cd;
	private int spec, rar;
	private int[] st;
	private boolean search = true;

	public FileHandler(){
		decks.add(deck1);
	}
	public void loadDeck( String deckName, boolean player ) throws IOException{
		if(player){
			for (String[] s : decks){
				if (s[0].equals(deckName)){
					String read = null;
					FileReader f = new FileReader(s[1]);
					BufferedReader br = new BufferedReader(f);
					Scanner sc = new Scanner(read);
					sc.useDelimiter("\\s = \\s|\\s : \\s");
					while(true){
						if (br.readLine() == "{"){
							read = br.readLine();
							if (sc.next() =="Deck_name"){
								dn = sc.next();
							}
							else if (sc.next() == "Deck_description"){
								dc = sc.next();
							}
							else if (br.readLine() == "}"){
								Deck a = new Deck(dn, dc);
								Main.setPlayerDeck(a);
							}
							else {read = br.readLine();}	
						}
						else if (br.readLine() =="\t{"){
							br.readLine();
							if (sc.next() == "Card_name"){	
								cn = sc.next();
							}
							else if (sc.next() == "Card_description"){
								cd = sc.next();
							}	
							else if(sc.next() == "Card_stats"){
								st[0] = sc.nextInt();
								st[1] = sc.nextInt();
							}
							else if(sc.next() == "Card_special"){
								spec = sc.nextInt();							
							}
							else if (sc.next() == "Card_rarity"){
								rar = sc.nextInt();
							}
							else if (br.readLine() == "\t}"){
								Card a = new Card(cn, cd, spec, st, rar);
								Deck.setCard(a);
							}
							else {br.readLine();}
						}
						else if (br.readLine() == "}"){
							break;
						}
						else {
							br.readLine();
						}
					}
					sc.close();	
				}
			}
		}
		else {
			for (String[] s : decks){
				if (s[0].equals(deckName)){
					FileReader f = new FileReader(s[1]);
					BufferedReader br = new BufferedReader(f);
					Scanner sc = new Scanner(f);
					sc.useDelimiter("\\s = \\s|\\s : \\s");
					while(true){
						if (br.readLine() == "{"){
							br.readLine();
							if (sc.next() =="Deck_name"){
								dn = sc.next();
							}
							else if (sc.next() == "Deck_description"){
								dc = sc.next();
							}
							else if (br.readLine() == "}"){
								Deck a = new Deck(dn, dc);
								Main.setEnemyDeck(a);
							}
							else {br.readLine();}	
						}
						else if (br.readLine() =="\t{"){
							br.readLine();
							if (sc.next() == "Card_name"){	
								cn = sc.next();
							}
							else if (sc.next() == "Card_description"){
								cd = sc.next();
							}	
							else if(sc.next() == "Card_stats"){
								st[0] = sc.nextInt();
								st[1] = sc.nextInt();
							}
							else if(sc.next() == "Card_special"){
								spec = sc.nextInt();							
							}
							else if (sc.next() == "Card_rarity"){
								rar = sc.nextInt();
							}
							else if (br.readLine() == "\t}"){
								Card a = new Card(cn, cd, spec, st, rar);
								Deck.setCard(a);
							}
							else {br.readLine();}
						}
						else if (br.readLine() == "}"){
							break;
						}
						else {
							br.readLine();
						}
					}
					sc.close();	
				}
			}
		}
	}
}
