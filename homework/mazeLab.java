public class mazeLab {

	public static int[][] maze;
	public static boolean isSolved = false;

	public static boolean shouldPrintProcess = false;

	public static void main(String[] args) {
		int[][] testMazeSmall = {
				{ 0, 1, 0, 1 },
				{ 0, 0, 0, 0 },
				{ 0, 1, 1, 0 },
				{ 1, 1, 1, 0 },
		};
		int[][] testMazeMedium = {
				{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1 },
				{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1 },
				{ 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1 },
				{ 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1 },
				{ 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
				{ 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1 },
				{ 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1 },
				{ 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
		};
		int[][] testMazeBig = {
				{ 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1 },
				{ 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1 },
				{ 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1 },
				{ 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1 },
				{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
		};
		int[][] testMazeGrid = {
				{ 0, 1, 0, 1, 0, 1, 0, 1 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 1, 0, 1, 0, 1, 0, 1 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 1, 0, 1, 0, 1, 0, 1 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 0, 1, 0, 1, 0, 0 },
		};
		int[][] testMazeNoValidPath = {
				{ 0, 1, 0, 0, 0, },
				{ 0, 1, 0, 1, 0, },
				{ 0, 1, 1, 1, 0, },
				{ 0, 1, 0, 1, 0, },
				{ 0, 0, 0, 1, 0, },
		};
		int[][] testMazeSpawnInWall = {
				{ 1, 1, 1 },
				{ 1, 1, 1 },
				{ 1, 1, 0 }
		};

		maze = testMazeMedium;

		System.out.println("================");
		printMaze(maze);
		System.out.println("================");
		simpleSolveMaze(0, 0, 0);
		if (isSolved) {
			System.out.println("Maze solved!!\n===============");
			printSolvedMaze(maze);
		} else {
			System.out.println("This maze has no path from top left to bottom right.");
		}
	}

	// this function would work just find if `layer` wasn't there. it's just for the
	// debugging print view.
	public static void solveMaze(int r, int c, int layer) {
		// when solveMaze is called on a cell, it sees what state the cell was left in.

		// if the cell = 0, it should look north next. the cell gets set to 8 before
		// doing this.
		// if the cell = 8, it should look east next.
		// if the cell = 6, it should look south next.
		// if the cell = 2, it should look west next.
		// if the cell = 4, it has already looked in all directions so it gets set to 5.

		// nothing happens after that in the function. the cell gets "worn out" after
		// looking in all four directions.
		// this is the base case.

		// java will then resume the function running one layer up. the cell was left
		// holding the number corresponding
		// to the number it should look at next.

		if (maze[0][0] == 1) { // spawn in wall
			return;
		}
		if (!isSolved) {
			setCellAtPosition(r, c, 8);
			if (getCellAtPosition(r - 1, c, layer) == 0) {
				solveMaze(r - 1, c, layer + 1);
			}
		}

		if (!isSolved) {
			setCellAtPosition(r, c, 6);
			if (getCellAtPosition(r, c + 1, layer) == 0) {
				solveMaze(r, c + 1, layer + 1);
			}
		}

		if (!isSolved) {
			setCellAtPosition(r, c, 2);
			if (getCellAtPosition(r + 1, c, layer) == 0) {
				solveMaze(r + 1, c, layer + 1);
			}
		}

		if (!isSolved) {
			setCellAtPosition(r, c, 4);
			if (getCellAtPosition(r, c - 1, layer) == 0) {
				solveMaze(r, c - 1, layer + 1);
			}
		}

		if (!isSolved) {
			setCellAtPosition(r, c, 5);
		}

	}

	public static void simpleSolveMaze(int r, int c, int layer) {
		if (maze[0][0] == 1) { return; }// spawn in wall
		if (isSolved) { return; } // maze solved already

		setCellAtPosition(r, c, 2);

		if (getCellAtPosition(r - 1, c, layer) == 0) { // check north
			solveMaze(r - 1, c, layer + 1);
		}

		if (getCellAtPosition(r, c + 1, layer) == 0) { // check east
			solveMaze(r, c + 1, layer + 1);
		}

		if (getCellAtPosition(r + 1, c, layer) == 0) { // check south
			solveMaze(r + 1, c, layer + 1);
		}

		if (getCellAtPosition(r, c - 1, layer) == 0) { // check west
			solveMaze(r, c - 1, layer + 1);
		}

		// cell was a dead end :(
		setCellAtPosition(r, c, 5);

	}

	public static int getCellAtPosition(int r, int c, int layer) {
		// return if reached bottom-right (solution!!)
		if (r == maze.length - 1 && c == maze[0].length - 1) {
			isSolved = true;
			return 3;
		}
		if (layer >= 0 && shouldPrintProcess) {
			System.out.print("  ".repeat(layer));
			System.out.printf("(%d,%d) = ", r, c);
		}
		// handle out of bounds by pretending it's just a solid wall and returning 1
		if (r < 0 || c < 0 || r >= maze.length || c >= maze[0].length) {
			if (layer >= 0 && shouldPrintProcess)
				System.out.println("OOB");
			return 1;
		}
		// otherwise, return the cell content
		if (layer >= 0 && shouldPrintProcess) {
			System.out.printf("%d\n", maze[r][c]);
		}
		return maze[r][c];
	}

	public static void setCellAtPosition(int r, int c, int value) {
		maze[r][c] = value;
	}

	public static void printMaze(int[][] maze) {
		for (int r = 0; r < maze.length; r++) {
			for (int c = 0; c < maze[r].length; c++) {
				System.out.print(maze[r][c]);
			}
			System.out.println();
		}
	}

	public static void printSolvedMaze(int[][] maze) {
		for (int r = 0; r < maze.length; r++) {
			for (int c = 0; c < maze[r].length; c++) {
				// cells that aren't "worn" out will have these even numbers in them. include
				// those in the path.
				if (maze[r][c] == 8 || maze[r][c] == 6 || maze[r][c] == 2 || maze[r][c] == 4) {
					System.out.print(" ");
				}
				// solveMaze technically stops at the step right before overwriting the "exit
				// cell" of the maze,
				// so force the bottom right cell to be part of the path.
				else if (r == maze.length - 1 && c == maze[r].length - 1) {
					System.out.print(" ");
				}
				// untouched cells or worn-out cells should not be included in the path.
				else if (maze[r][c] == 0 || maze[r][c] == 5) {
					System.out.print("0");
				}
				// preserve all walls.
				else if (maze[r][c] == 1) {
					System.out.print("1");
				}
			}
			System.out.println();
		}
	}
}
