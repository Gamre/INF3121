import java.util.Random;

// Creates the minefield and checks the moves that the users take. 
// Checks if its a invalid input, already opened and so on. 
class MineField{

	private boolean[][] mines,visible;
	private boolean boom;
	private final int rowMax = 5;
	private final int colMax = 10;
	
	// Constructor that makes the board and placing the bombs randomly. 
	MineField(){
		mines=new boolean[rowMax][colMax];
		visible=new boolean[rowMax][colMax];
		boom=false;
		
		initMap(); // New method that fixes the original bug
		
		int bombCount=15; // Changed it for a better word. 
		int randomRow,randomCol;
		Random RGenerator=new Random();
		
		// Placing the bombs randomly
		while(bombCount>0){
			randomRow=Math.abs(RGenerator.nextInt()%rowMax);
			randomCol=Math.abs(RGenerator.nextInt()%colMax);
			if(trymove(randomRow,randomCol)){
				bombCount--; 
			}
		}		
	}	
	
	// Checks if a place is eligible for a bomb. 
	private boolean trymove(int randomRow, int randomCol) {
		if(mines[randomRow][randomCol]) return false;
		mines[randomRow][randomCol] = true;
		return true;
	}

	// Initialser the minefield. It was this that caused the bug in the original file. 
	private void initMap() {
		for(int row=0;row<rowMax;row++){
			for(int col=0;col<colMax;col++){
				mines[row][col]=false;
				visible[row][col]=false;
			}
		}
	}

	// Checks after bombs and showes them. 
	private void boom() {
		for(int row=0;row<rowMax;row++){
			for(int col=0;col<colMax;col++){
				if(mines[row][col]){
					visible[row][col]=true;
				}
			}
		}
		boom=true;
		show();
	}

	// Gets the value for bombs nearby. Changed it to returns string rather than Char. 
	// Because of that we could remove the switch, and just return count as a string. 
	// Might use a bit more systempower, but in this program its no problem. 
	private String drawChar(int row, int col) {
		int count=0;
		if(visible[row][col]){
			if(mines[row][col]) return "*";
			count = getCount(row, col, count);
		}
		else{
			if(boom){
				return "-";
			}			
			return "?";
		}
		return ""+count; 
	}
	
	// Returns the value of bombs that are nearby 
	// Decreeses the complexity and depth.
	private int getCount(int row, int col, int count) {
		for(int irow = row-1; irow <= row+1; irow++){
			for(int icol = col-1; icol <= col+1; icol++){
				if(icol >= 0 && icol < colMax && irow >= 0 && irow < rowMax){
					if(mines[irow][icol]) count++;
				}
			}
		}
		return count;
	}

	// Returns the boom value, true or false. 
	public boolean getBoom(){
		return boom;
	}

	// Checks if it's legal move. If not, the error message comes out. 
	public boolean legalMoveString(String input) {
		String[] separated=input.split(" ");
		int row;
		int col;
		try{
			row=Integer.parseInt(separated[0]);
			col=Integer.parseInt(separated[1]);
			if(row<0||col<0||row>=rowMax||col>=colMax){
				throw new java.io.IOException();
			}
		}
		catch(Exception e){
			System.out.println("\nInvalid Input!");
			return false;
		}
		
		if(legalMoveValue(row,col)){
			return true;	
		}
		return false;
	}

	// Checks if the move is legal or not.
	private boolean legalMoveValue(int row, int col) {
		if(visible[row][col]){
			System.out.println("You stepped in allready revealed area!");
			return false;
		}

		if(mines[row][col]){
			boom();
			return false;
		}

		visible[row][col]=true;
		return true;
	}

	// Prints out the minefield.
	public void show() {
		System.out.println("\n    0 1 2 3 4 5 6 7 8 9 ");
		System.out.println("   ---------------------");
		for(int row=0;row<rowMax;row++){
			System.out.print(row+" |");
			for(int col=0;col<colMax;col++){
				System.out.print(" " + drawChar(row, col));
			}
			System.out.println(" |");
		}
		System.out.println("   ---------------------");
	}
	
}
