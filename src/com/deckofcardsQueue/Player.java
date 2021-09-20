package com.deckofcardsQueue;

import java.util.Arrays;

/*This class stores the cards distributed to players
*/
public class Player {
	String[] cardArr = new String[9];
	int countCard=0;
	
	
  	public void putCard(String card) {
  		cardArr[countCard++] = card;
  	}
  	
  	public String[] getCardArray() {
		return cardArr;
	}

	@Override
	public String toString() {
		return "Player [" + Arrays.toString(cardArr) + "]";
	}
  	
}
