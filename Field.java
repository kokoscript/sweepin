/*
 *	This class Copyright (C) 2018 Epocti.
 *	Author: Kokoro (kokoscript)
 */


import java.util.concurrent.ThreadLocalRandom;

public class Field {
	private Tile[][] field;
	private int width, height, mines, flags;

	public Field(int width, int height, int numMines){
		// Initialize field
		field = new Tile[height][width];
		this.width = width;
		this.height = height;

		// Create the tiles (default non-mines)
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				field[i][j] = new Tile();
				// Debug: field[i][j].uncover();
			}
		}

		// Create mines randomly
		mines = numMines;
		int minesLeft = numMines;
		int randX, randY;
		while(minesLeft != 0){
			randX = ThreadLocalRandom.current().nextInt(0, width);
			randY = ThreadLocalRandom.current().nextInt(0, height);
			if(field[randY][randX].isMine()){
				minesLeft++;
			}
			else field[randY][randX].setMine();
			minesLeft--;
		}

		// Generate surround numbers
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				if(!field[y][x].isMine()){
					int tempSurround = 0;
					if((x > 0 && y > 0) && field[y - 1][x - 1].isMine()) tempSurround++;						// Top left
					if((y > 0) && field[y - 1][x].isMine()) tempSurround++;										// Top
					if((x < (width - 1) && y > 0) && field[y - 1][x + 1].isMine()) tempSurround++;				// Top right
					if((x > 0) && field[y][x - 1].isMine()) tempSurround++;										// Left
					if((x < (width - 1)) && field[y][x + 1].isMine()) tempSurround++;							// Right
					if((x > 0 && y < (height - 1)) && field[y + 1][x - 1].isMine()) tempSurround++;				// Bottom left
					if((y < (height - 1)) && field[y + 1][x].isMine()) tempSurround++;							// Bottom
					if((x < (width - 1) && y < (height - 1)) && field[y + 1][x + 1].isMine()) tempSurround++;	// Bottom right
					field[y][x].setSurround(tempSurround);
				}
			}
		}
	}

	public boolean pokeTile(int x, int y){
		// Returns true if there was a mine.
		field[y][x].uncover();
		if((!field[y][x].isMine() && field[y][x].getSurround() == 0) && !field[y][x].isCovered()){
			// Recursive tile clearing if all neighbors have no surrounding mines
			if(x > 0 && y > 0) if(field[y - 1][x - 1].isCovered()) pokeTile(x - 1, y - 1);				// Top left
			if(y > 0) if(field[y - 1][x].isCovered()) pokeTile(x, y - 1);									// Top
			if(x < (width - 1) && y > 0) if(field[y - 1][x + 1].isCovered()) pokeTile(x + 1, y - 1);				// Top right
			if(x > 0) if(field[y][x - 1].isCovered()) pokeTile(x - 1, y);									// Left
			if(x < (width - 1)) if(field[y][x + 1].isCovered()) pokeTile(x + 1, y);							// Right
			if(x > 0 && y < (height - 1)) if(field[y + 1][x - 1].isCovered()) pokeTile(x - 1, y + 1);			// Bottom left
			if(y < (height - 1)) if(field[y + 1][x].isCovered()) pokeTile(x, y + 1);							// Bottom
			if(x < (width - 1) && y < (height - 1)) if(field[y + 1][x + 1].isCovered()) pokeTile(x + 1, y + 1);	// Bottom right
		}
		return field[y][x].isMine();
	}

	public void flagTile(int x, int y){
		field[y][x].flag();
		if(field[y][x].isFlagged()) flags++;
	}

	public boolean hasWon(){
		int minesFlagged = 0;
		for(int y = 0; y < field.length; y++){
			for(int x = 0; x < field[0].length; x++){
				if(field[y][x].isMine() && field[y][x].isFlagged()){
					minesFlagged++;
				}
			}
		}
		return minesFlagged == mines && minesFlagged == flags;
	}

	public int mineCount(){
		return mines;
	}

	public int flagCount(){
		return flags;
	}

	public void printField(){
		// TODO: clean this up
		// X coordinate drawing
		System.out.print("|");
		for(int k = 0; k < field[0].length; k++){
			if(k < 10) System.out.print(" " + k + "|");
			else System.out.print(k + "|");
		}
		System.out.print("\n");
		for(int k = 0; k < field[0].length; k++){
			System.out.print("---");
		}
		System.out.print("-\n");
		for(int i = 0; i < field.length; i++){
			System.out.print("|");
			// Draw tiles
			for(int j = 0; j < field[0].length; j++){
				System.out.print(field[i][j]);
			}
			System.out.print(i); // Y coordinate drawing
			System.out.print("\n");
			// Separator
			for(int k = 0; k < field[0].length; k++){
				System.out.print("---");
			}
			System.out.print("-\n");
		}
	}
}
