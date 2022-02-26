package mines;

import java.util.Random;
//this class is used to represent the logic level for the game of mines sweeper
public class Mines {
	protected int height, width, numMines;
	protected Slot[][] minefield;//the mineField itself
	private boolean showAll;

	//this class is used to represent a single slot on the mine field
	//it allows us to set open/flags/bombs , and read the state of the slot
	protected class Slot {
		boolean flag, bomb, open;

		public boolean isFlag() {//return the flag state
			return flag;
		}

		public void setFlag() {//invert flag state
			this.flag = !flag;
		}

		public boolean isBomb() {//return if there is a bomb
			return bomb;
		}

		public void setBomb() {//set the bomb in the location
			this.bomb = true;
		}

		public boolean isOpen() {//returns true if the slot was already opened
			return open;
		}

		public void setOpen() {//set the slot to be open
			this.open = true;
		}
	}

	//this is the constructor for the mine it gets the size of the mine and the number of bombs
	//and it creates a mine and randomly places all the bombs
	public Mines(int width,int height,  int numMines) {
		this.width = width;
		this.height = height;
		minefield = new Slot[width][height];//generate a new mineField
		for (int i = 0; i < width; i++) {//allocate a new slot for each slot on the board
			for (int j = 0; j < height; j++) {
				minefield[i][j] = new Slot();
			}
		}
		Random r = new Random();//Initialize a randomer
		while (this.numMines != numMines) {// add mines randomly to the field
			addMine(r.nextInt(width),r.nextInt(height) );//add mine in random location
		}
	}

	// adds a mine to the field
	public boolean addMine(int i, int j) {
		if (!checkBounds(i, j) || minefield[i][j].isBomb())
			return false;// if not within bounds or already has a mine
		minefield[i][j].setBomb();//set the location to have a bomb
		numMines++;//increase number of bombs
		return true;
	}

	// return true if within bounds
	private boolean checkBounds(int i, int j) {
		return i < width && i >= 0 && j < height && j >= 0;
	}

	//this method is used to open a slot on the board
	public boolean open(int i, int j) {
		// if selected block is out of bounds or a mine or already open
		if (!checkBounds(i, j) || minefield[i][j].isBomb() || minefield[i][j].isOpen())
			return false;
		else {// set cell to open and open others around it if possible
			minefield[i][j].setOpen();
			if (nearbyBomb(i, j) != 0) {// if there are bombs around you
				return true;
			}
			//recursive calls to try and open all slots around the given slot
			open(i - 1, j - 1);
			open(i - 1, j);
			open(i - 1, j + 1);
			open(i, j - 1);
			open(i, j + 1);
			open(i + 1, j - 1);
			open(i + 1, j);
			open(i + 1, j + 1);
			return true;
		}
	}

	// returns the number of bombs around a given place in a 3X3 area
	private int nearbyBomb(int i, int j) {
		int cnt = 0;
		for (int k = -1, n = 0; n <= 1; n++, k *= -1) {//go over the 3x3 area
			cnt += checkBounds(i + k, j) && minefield[i + k][j].isBomb() ? 1 : 0;
			cnt += checkBounds(i, j + k) && minefield[i][j + k].isBomb() ? 1 : 0;
			cnt += checkBounds(i + k, j + k) && minefield[i + k][j + k].isBomb() ? 1 : 0;
			cnt += checkBounds(i - k, j + k) && minefield[i - k][j + k].isBomb() ? 1 : 0;
		}
		return cnt;
	}

	// toggles the flag in a given spot
	public void toggleFlag(int x, int y) {
		if (!checkBounds(x, y))// if not within bounds
			return;
		minefield[x][y].setFlag();
	}

	// return true only if all the slots that are not bombs are open
	public boolean isDone() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (!minefield[i][j].isOpen() && !minefield[i][j].isBomb())// if not open and not bomb return false
					return false;
			}
		}
		return true;
	}

	// returns a string representation of a slot on the board
	public String get(int i, int j) {
		if (!minefield[i][j].isOpen() && minefield[i][j].isFlag())
			return "F";// if isn't open and there is a flag
		else if (!minefield[i][j].isOpen() && !minefield[i][j].isFlag() && !showAll)
			return ".";// if isn't open and there is no flag
		if ((minefield[i][j].isOpen() || showAll) && minefield[i][j].isBomb())
			return "X";// if open and there is a bomb
		else {
			int cnt = nearbyBomb(i, j);// get number of bombs around a slot
			return cnt == 0 ? " " : Integer.toString(cnt);
		}
	}

	// set showAll to the desired value
	public void setShowAll(boolean showAll) {
		this.showAll = showAll;
	}

	// returns a string representation of the mineField
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				sb.append(get(i, j));//get the value from the field and add it to the string
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println("test mines");
	}
}
