package deckofcards;

public class DeckOfCards {
	public static void main(String[] args) {

		String suits[] = {"Clubs","Daimonds","Heart","Spades"};
		String rank[] = {"2","3","4","5","6","7","8","9","10","Jack","Queen","King","Ace"};
		
		String card[]= new String[52];
		
		for(int i=0;i<suits.length;i++) {
			for(int j=0;j<rank.length;j++) {
				card[rank.length*i+j]=rank[j]+ " of " +suits[i];
			}
		}
		shuffle(card);
		String[][] distribute = new String[4][9];
		dist(distribute, card);
		print(distribute);
	}
	
	public static void shuffle(String[] card) {
		String temp;
		for(int i=0;i<card.length;i++) {
			int randomValue = (int)(Math.random() * card.length);
			for(int j=0;j<card.length;j++) {
				temp = card[j];
				card[j] = card[randomValue];
				card[randomValue] = temp;
			}
		}
	}
	
	public static void dist(String[][] distribute, String[] card) {
		int n = 0;
		for(int i=0;i<4;i++) {
			for(int j=0;j<9;j++) {
				distribute[i][j]=card[n++];
			}
		}
			
	}
	
	public static void print(String[][] distribute) {
		for(int i=0;i<4;i++) {
			System.out.println("Player "+(i+1));
			for(int j=0;j<9;j++) {
				System.out.print(distribute[i][j]+",  ");
			}
			System.out.println();
		}
	}
		
	
	
}