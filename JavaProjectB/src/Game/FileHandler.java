package Game;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Scanner;

public class FileHandler {
	private List<String[]> decks = new ArrayList<String[]>();
	private String[] deck1 = { "deck1", "Decks\\Deck1.data"};
	
	public FileHandler(){
		decks.add(deck1);
	}
	public void loadDeck( String deckName ) throws IOException{
		for (String[] s : decks){
			if (s[0].equals(deckName)){
				FileReader f = new FileReader(deckName);
				BufferedReader br = new BufferedReader(f);
				Scanner sc = new Scanner(f);
				sc.useDelimiter("\\s = \\s|\\s : \\s");
				while(true){
					if (br.readLine() == "{"){
						br.readLine();
						if (sc.next() =="Deck_name"){
							String dn = sc.next();
						}
						else if (sc.next() == "Deck_description"){
							String dc = sc.next();
						}
						else {br.readLine();}			
					}
					else if (br.readLine() =="	{"){
						br.readLine();
						if (sc.next() == "Card_name"){	
							String cn = sc.next();
						}
						else if (sc.next() == "Card_description"){
							String cd = sc.next();
						}
						else if(sc.next() == "Card_stats"){
							int at = sc.nextInt();
							int def = sc.nextInt();
						}
						else if(sc.next() == "Card_special"){
							int spec = sc.nextInt();							
						}
						else if (sc.next() == "Card_rarity"){
							int rar = sc.nextInt();
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
