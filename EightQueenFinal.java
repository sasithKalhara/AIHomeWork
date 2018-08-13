import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EightQueen {
    
    public static int numberOfQueens = 8;
    private int[][] chessBoard;
    private int[] queenPositions;
    
    
    public static void main(String[] args) {
        
        boolean climb = true;
        
        while (climb) {
            
            EightQueen chessBoard = new EightQueen(new int[8][8], new int[8]);
            
//            randomly place queens
            chessBoard.placeQueens();
            System.out.println("Initial board:");
            chessBoard.printBoard();
            System.out.println("Pairs of attacking queens: " + chessBoard.calculateCost() + "\n");

//          initial cost
            int initialCost = chessBoard.calculateCost();
            
            if (initialCost == 0){
                climb = false;
                System.out.println("Initial board has zero attacking queens");                
            }
            
            boolean best = false;
            int[] bestQueenPositions = new int[8];
            
            int currentCost = chessBoard.calculateCost();
            
            for (int j = 0; j < 8; j++) {
                best = false;
                for (int i = 0; i < 8 ; i++) {
                    chessBoard.moveQueen(i, j);
                    
                    if (chessBoard.calculateCost() < currentCost) {
                        best = true;
                        currentCost = chessBoard.calculateCost();
                        bestQueenPositions[j] = i;
                        
                    }else
                    // reset to original
			chessBoard.resetQueen(i, j);
                    }
				// Removing marker
				chessBoard.resetBoard(j);
				
				if (best) {
					//  place queen in the best position
					chessBoard.placeBestQueen(j, bestQueenPositions[j]);
					System.out.println("Best board found with the iteration of column " + j);
					chessBoard.printBoard();
					System.out.println("Pairs of queens attacking each other: "+ chessBoard.calculateCost()+"\n");
                                        
                                        if (chessBoard.calculateCost() == 0 && j != 7){
                                            climb = false;
                                            System.out.println("A Global Minimum was obtained with the iteration of column " + j + "\n");
                                        }else if (chessBoard.calculateCost() == 0 && j==7){
                                            System.out.println("A Global Minimum was obtained by iterating through all columns \n");
                                        }else if(chessBoard.calculateCost() != 0 && j==7){
                                            System.out.println("A Local Minimum was obtained by iterating through all columns \n");
                                        } 
                                        
				} else {
					if(j < 7){
                                            System.out.println("No better board was found with the iteration of column " + j +"\n");
                                            chessBoard.printBoard();
					
					}else if(j == 7){
                                            int finalCost = chessBoard.calculateCost();
                                            System.out.println("No better board was found with the iteration of column " + j +"\n");
                                            
                                            if(finalCost == 0 ){
                                                chessBoard.printBoard();
                                                System.out.println("A Global Minimum was obtained");
                                            }else if (finalCost<initialCost){
                                                chessBoard.printBoard();
                                                System.out.println("A Local Minimum was obtained");
                                            }
					}
				}
			} 
			climb = false;
		}
	}

    public EightQueen(int[][] board, int[] positions) {
        
        this.chessBoard = board;
        this.queenPositions = positions;
        
    }

    private int[] generateQueens() {
        
        List<Integer> randomNumber = new ArrayList<Integer>();
        Random r = new Random();
        
        for (int i = 0; i < 8; i++) {
            randomNumber.add(r.nextInt(8));
        }
        
        int[] randomQueenPositions = new int[8];
        
        for (int i = 0; i < 8; i++) {
            randomQueenPositions[i] = randomNumber.get(i);
        }
        
        return randomQueenPositions;
	}

    public void placeQueens() {
        queenPositions = generateQueens();
        
        for (int i = 0; i < chessBoard.length; i++) {
            chessBoard[queenPositions[i]][i] = 1;
        }
    }

    public int calculateCost(){

        int totalPairs = 0;
        int rowCounter = 0;
        int diagonalCounterRight = 0;
        int diagonalCounterLeft = 0;
        int sum = 1;
        
        while (sum <= 14){
            for (int i = 0; i < chessBoard.length; i++){
                for (int j = 0; j < chessBoard[i].length; j++){
                    if(i+j == sum){
                        for(int m = i+1; m<=7; m++){
                            for(int n=j-1; n>=0; n--){
                                if(chessBoard[i][j] == chessBoard[m][n] && chessBoard[i][j] == 1 && n+m == sum){
                                    //System.out.print("|" + i + j + "," + m + n + "|" + " ");
                                    diagonalCounterLeft++;
                                }
                            }
                        }
                    }
                }
            }sum=sum+1; 
        }
//        System.out.print("Left Diagonal Attacking Pairs " + diagonalCounterLeft + "\n" );
        
        for(int i = 0; i < chessBoard.length; i++){
            for (int j = 0; j < chessBoard[i].length; j++){
                int k=i+1;
                int l=j+1;
                while(k<=7 && l <=7){
                    if(chessBoard[i][j]== chessBoard[k][l] && chessBoard [i][j]==1){
//                        System.out.print("|" + i + j + "," + k + l + "|" + " ");
                        diagonalCounterRight++;
                    } 
                    k++;
                    l++;
                }
            }
        }
//        System.out.print("Right Diagonal Attacking Pairs " + diagonalCounterRight + "\n" );

        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[i].length; j++) {
                for (int k = 0; k < 8 && k != j; k++){
                    if (chessBoard[i][j]== chessBoard[i][k] && chessBoard[i][j] == 1){
                        rowCounter++;
//                        System.out.print("|" + i + j + "," + i + k + "|" + " ");
                    }
                }
            }
        }
//        System.out.print("Row Attacking Pairs " + rowCounter + "\n" );
        
        totalPairs = rowCounter+diagonalCounterRight+diagonalCounterLeft;
        return totalPairs;
	}
	


    public void moveQueen(int row, int col) {
        chessBoard[queenPositions[col]][col] = 2;
        chessBoard[row][col] = 1;
    }

    void resetQueen(int row, int col) {
        if (chessBoard[row][col] == 1)
            chessBoard[row][col] = 0;
    }

    public void resetBoard(int col) {
        for (int i = 0; i < 8; i++) {
            if (chessBoard[i][col] == 2)
                chessBoard[i][col] = 1;
        }
    }

    public void placeBestQueen(int col, int queenPos) {
        for (int i = 0; i < 8; i++) {
            if (chessBoard[i][col] == 1)
                chessBoard[i][col] = 2;
        }
    
        chessBoard[queenPos][col] = 1;
        for (int i = 0; i < 8; i++) {
            if (chessBoard[i][col] == 2)
                chessBoard[i][col] = 0;
        }
    }

    public void printBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(chessBoard[i][j] + " ");
            }
            System.out.println("");
        }
    }
}
