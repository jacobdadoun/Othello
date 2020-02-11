package edu.touro.mco264.othello;

import java.util.Scanner;

public class OthelloMain {

    private static boolean gameOn = true;
    private static OthelloLogic newGame = new OthelloLogic();

    public static void main(String[] args) {

        while(gameOn){

            newGame.findLegalMoves();
            newGame.printBoard();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter a position");
            System.out.println("Row: ");
            int row = scanner.nextInt();
            System.out.println("Column: ");
            int column = scanner.nextInt();
            newGame.input(row, column);

            if(newGame.boardIsNotFull()){
                newGame.switchTurn();
            }
            else {
                newGame.getWinner();
                gameOn = false;
            }



        }

    }








}
