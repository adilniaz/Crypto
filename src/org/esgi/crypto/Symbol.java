package org.esgi.crypto;

public class Symbol {

	private Character character;
	private float frequence;
	private int occurence;
	
	public Symbol() {
		character = '?';
		frequence = 0;
		occurence = 0;
	}

	public Symbol(char c, float frequence) {
		this.character = c;
		this.frequence = frequence;
	}
	
	public Symbol(char c, int occurence) {
		this.character = c;
		this.occurence = occurence;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public float getFrequence() {
		return frequence;
	}

	public void setFrequence(float frequence) {
		this.frequence = frequence;
	}

	public int getOccurence() {
		return occurence;
	}

	public void setOccurence(int occurence) {
		this.occurence = occurence;
	}
}
