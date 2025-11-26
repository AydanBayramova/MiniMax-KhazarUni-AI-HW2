import java.util.ArrayList;
import java.util.List;

public class Board {
    private char[][] grid;
    private int m;
    private int k;
    private char currentPlayer;

    public Board(int m, int k) {
        this.m = m;
        this.k = k;
        this.grid = new char[m][m];
        this.currentPlayer = 'X';
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j] = ' ';
            }
        }
    }


    public Board(Board other) {
        this.m = other.m;
        this.k = other.k;
        this.currentPlayer = other.currentPlayer;
        this.grid = new char[m][m];
        for (int i = 0; i < m; i++) {
            System.arraycopy(other.grid[i], 0, this.grid[i], 0, m);
        }
    }

    public int getM() { return m; }
    public int getK() { return k; }
    public char getCurrentPlayer() { return currentPlayer; }
    public char getOpponent() { return (currentPlayer == 'X') ? 'O' : 'X'; }
    public char getCell(int r, int c) { return grid[r][c]; }


    public List<int[]> getLegalMoves() {
        List<int[]> moves = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == ' ') {
                    moves.add(new int[]{i, j});
                }
            }
        }
        return moves;
    }


    public Board makeMove(int row, int col) {
        if (grid[row][col] != ' ') return null;
        Board newBoard = new Board(this);
        newBoard.grid[row][col] = this.currentPlayer;
        newBoard.currentPlayer = (this.currentPlayer == 'X') ? 'O' : 'X';
        return newBoard;
    }


    public char checkWinner() {

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                char current = grid[i][j];
                if (current == ' ') continue;


                if (j + k <= m && checkLine(i, j, 0, 1, current)) return current;

                if (i + k <= m && checkLine(i, j, 1, 0, current)) return current;

                if (i + k <= m && j + k <= m && checkLine(i, j, 1, 1, current)) return current;

                if (i + k <= m && j - k + 1 >= 0 && checkLine(i, j, 1, -1, current)) return current;
            }
        }
        return ' ';
    }

    private boolean checkLine(int r, int c, int dr, int dc, char player) {
        for (int step = 0; step < k; step++) {
            if (grid[r + step * dr][c + step * dc] != player) return false;
        }
        return true;
    }


    public boolean isTerminal() {
        if (checkWinner() != ' ') return true;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == ' ') return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            sb.append("|");
            for (int j = 0; j < m; j++) {
                sb.append(grid[i][j]).append("|");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}