package edu.touro.mco264.othello;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Jacob Dadoun
 * Project Type: Game
 * Project Name: Othello
 * Version 3
 * Hours Worked: 12.5
 */

enum CellState {_____, BLACK, WHITE, __X__}

public class OthelloLogic {

    private class Points{
        int x;
        int y;
    }

    private ArrayList<Points> perimeter = new ArrayList<>(8);
    private ArrayList<Points> opponentPos = new ArrayList<>();
    private int BOARD_SIZE = 8;
    private CellState[][] board;
    private int playCount = 0;
    public CellState currentTurn;
    public CellState opponent;



    public OthelloLogic(){
        board = new CellState[BOARD_SIZE][BOARD_SIZE];
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                board[i][j] = CellState._____;
            }
        }

        currentTurn = CellState.BLACK;
        opponent = CellState.WHITE;

        board[3][3] = opponent;
        board[4][3] = currentTurn;
        board[3][4] = currentTurn;
        board[4][4] = opponent;

        initPerimeter();
    }

    private void initPerimeter(){

        for(int row = -1; row < 2; row++){
            for(int column = -1; column < 2; column++){

                Points position = new Points();
                position.x = column;
                position.y = row;
                perimeter.add(position);

            }
        }
        perimeter.remove(4);

    }


    public void findLegalMoves(){
        for(int row = 0; row < BOARD_SIZE; row++){
            for(int column = 0; column < BOARD_SIZE; column++){

                if(board[row][column] == currentTurn){
                    for(Points p: perimeter){
                        try {

                            if(board[row + p.y][column + p.x].equals(opponent)){
                                int rowDir = row + p.y;
                                int columnDir = column + p.x;

                                while(!board[rowDir][columnDir].equals(currentTurn) && !board[rowDir][columnDir].equals(CellState.__X__)){
                                    rowDir = rowDir + p.y;
                                    columnDir = columnDir + p.x;

                                    if(board[rowDir][columnDir].equals(CellState._____)){
                                        board[rowDir][columnDir] = CellState.__X__;
                                        break;
                                    }
                                }
                            }

                        }
                        catch (ArrayIndexOutOfBoundsException e){
                            System.out.println("Out of bounds at Row: " + p.y + ", Col: "+ p.x + " to mark X");
                        }
                    }
                }
            }
        }
    }


    public void clearLegalMoves(){
        for(int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {

                if(board[row][column].equals(CellState.__X__)){
                    board[row][column] = CellState._____;
                }

            }
        }
    }




    public void input(int row, int column){

        if(board[row][column].equals(CellState.__X__)){
            board[row][column] = currentTurn;
            playCount++;
            clearLegalMoves();
            checkNeighbors(row, column);
        }
        else {
            System.out.println("Not a valid move. lost your turn.");
            clearLegalMoves();
        }

    }

    private void checkNeighbors(int row, int column){

        for(Points p: perimeter){

            try{
                if(board[row + p.y][ column + p.x] == opponent) {
                    int rowDir = row + p.y;
                    int columnDir = column + p.x;

                    while (!board[rowDir][columnDir].equals(currentTurn)) {

                        Points opponentP = new Points();
                        opponentP.y = rowDir;
                        opponentP.x = columnDir;
                        opponentPos.add(opponentP);

                        rowDir = rowDir + p.y;
                        columnDir = columnDir + p.x;

                        if(board[rowDir][columnDir].equals(CellState._____)){
                            opponentPos.clear();
                            break;
                        }

                    }
                    flipOpponentPos();
                }
            }
            catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Out of bounds at Row: " + p.y + ", Col: " + p.x );
            }



        }

    }


    private void flipOpponentPos(){

        if(! opponentPos.isEmpty()){

            for(Points p: opponentPos){

                board[p.y][p.x] = currentTurn;

            }
            opponentPos.clear();
        }

    }


    public void printBoard(){
        System.out.println(currentTurn + "'s Turn");
        System.out.println("     0      1      2      3      4      5      6      7   ");
        for(int i = 0; i< board.length; i++){
            System.out.print(i  + " ");
            System.out.println(Arrays.toString(board[i]));
        }
        System.out.println();
    }


    public void switchTurn(){

        if(currentTurn == CellState.BLACK){
            currentTurn = CellState.WHITE;
            opponent = CellState.BLACK;
        }
        else{
            currentTurn = CellState.BLACK;
            opponent = CellState.WHITE;
        }

    }

    public boolean boardIsNotFull(){

        if(playCount < 63){
            return true;
        }
        else{
            return false;
        }
    }

    public void getWinner(){
        int black = 0;
        int white = 0;

        for(int row = 0; row < BOARD_SIZE; row++){
            for(int column = 0; column < BOARD_SIZE; column++){
                if(board[row][column].equals(CellState.BLACK)){
                    black++;
                }
                else if(board[row][column].equals(CellState.WHITE)){
                    white++;
                }
            }
        }

        if(black > white){
            System.out.println("Black Wins!");
        }
        else if (black == white){
            System.out.println("Tie Game!");
        }
        else{
            System.out.println("White Wins!");
        }
    }


}
