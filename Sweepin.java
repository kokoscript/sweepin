/*
 *	This class Copyright (C) 2018 Epocti.
 *	Author: Kokoro (kokoscript)
 */

import java.util.Scanner;

public class Sweepin {
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		Field field = new Field(4, 4, 4);
		int tempInput = 0, action = 0, ax = -1, ay = -1;
		int[] tempFieldParameters = new int[3];
		boolean mineWasHit = false;

		System.out.println("-----------------------------------------");
		System.out.println("SWEEPIN' - A text-based minesweeper clone");
		System.out.println("-----------------------------------------");
		System.out.println("Created by kokoscript @ Epocti");
		System.out.println("-----------------------------------------");

		// Initial field setup
		while(!(tempInput >= 1 && tempInput <= 4)){
			System.out.println("1. Beginner (8x8, 10 mines)");
			System.out.println("2. Novice (16x16, 20 mines)");
			System.out.println("3. Expert (32x32, 30 mines)");
			System.out.println("4. Custom");
			System.out.print("Your choice: ");

			tempInput = input.nextInt();

			if(tempInput == 1) field = new Field(8, 8, 10);
			else if(tempInput == 2) field = new Field(16, 16, 20);
			else if(tempInput == 3) field = new Field(32, 32, 30);
			else if(tempInput == 4){
				System.out.print("Field width? (>= 4 && <= 100): ");
				tempFieldParameters[0] = input.nextInt();
				System.out.print("Field height? (>= 4 && <= 100): ");
				tempFieldParameters[1] = input.nextInt();
				System.out.print("Number of Mines? (< width * height): ");
				tempFieldParameters[2] = input.nextInt();

				if(tempFieldParameters[0] >= 4 && tempFieldParameters[0] <= 100){
					if(tempFieldParameters[1] >= 4 && tempFieldParameters[1] <= 100){
						if(tempFieldParameters[2] < tempFieldParameters[0] * tempFieldParameters[1])
							field = new Field(tempFieldParameters[0], tempFieldParameters[1], tempFieldParameters[2]);
						else {
							System.out.println("Too many mines! Make it so that there's at least 1 less mine than the width times the height.");
							tempInput = 0;
						}
					}
					else {
						System.out.println("Invalid height! Make it between 4 and 100 inclusive.");
						tempInput = 0;
					}
				}
				else {
					System.out.println("Invalid width! Make it between 4 and 100 inclusive");
					tempInput = 0;
				}
			}
			else tempInput = 0;
		}

		System.out.println("Let's get sweepin'!");

		while(!mineWasHit && !field.hasWon()){
			while(!(action == 1 || action == 2)){
				field.printField();
				System.out.println("1. Poke");
				if(field.flagCount() < field.mineCount()) System.out.println("2. Flag\t\t\t| Flagged: [" + field.flagCount() + "/" + field.mineCount() + "]");
				else System.out.println("2. Flag\t\t\t| Flagged: [" + field.flagCount() + "/" + field.mineCount() + "] /!\\");
				action = input.nextInt();
			}

			while(!(ax >= 0 && ax <= 99)){
				System.out.print("X: ");
				ax = input.nextInt();
			}

			while(!(ay >= 0 && ay <= 99)){
				System.out.print("Y: ");
				ay = input.nextInt();
			}

			if(action == 1) mineWasHit = field.pokeTile(ax, ay);
			else if(action == 2) field.flagTile(ax, ay);

			action = 0;
			ax = -1;
			ay = -1;
			if(mineWasHit){
				field.printField();
				System.out.println("KABOOM!! You've hit a mine!");
			}
		}

		// Win text
		if(field.hasWon()) System.out.println("You've won!");
	}
}
