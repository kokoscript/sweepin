/*
 *	This class Copyright (C) 2018 Epocti.
 *	Author: Kokoro (kokoscript)
 */


public class Tile {
	private int type = 0;
	private int numSurround = 0;
	private boolean isCovered = true;
	private boolean isFlagged = false;

	public Tile(){
	}

	public boolean uncover(){
		// Returns true if there was a mine
		if(!isCovered) System.out.println("That tile is already uncovered.");
		else if(isFlagged) System.out.println("That tile is flagged - you probably don't want to poke it.");
		else isCovered = false;
		return (type == 0);
	}

	public int getSurround(){
		return numSurround;
	}
	public void setSurround(int surround){
		numSurround = surround;
	}

	public void setMine(){
		type = 1;
	}

	public void flag(){
		if(!isFlagged && isCovered) isFlagged = true;
		else if(isFlagged) System.out.println("Can't flag: That tile is already flagged.");
		else if(!isCovered) System.out.println("Can't flag: That tile is uncovered.");
	}
	public void unflag(){
		if(isFlagged) isFlagged = false;
		else System.out.println("Tile already has no flag on it.");
	}

	public boolean isFlagged(){
		return isFlagged;
	}

	public boolean isCovered(){
		return isCovered;
	}

	public boolean isMine(){
		return type == 1;
	}

	public String toString(){
		if(isFlagged) return "&&|"; // Covered, flagged
		else if(isCovered) return "##|"; // Covered, not flagged
		else if(type == 0 && numSurround == 0) return "  |"; // Cleared with no surrounding
		else if(type == 0) return " " + numSurround + "|"; // Cleared with surrounding
		else if(type == 1) return "**|"; // Mine
		else return "!!|"; // Error
	}
}
