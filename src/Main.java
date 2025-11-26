import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Generalized Tic-Tac-Toe AI");
        System.out.print("Enter Board Size (m): ");
        int m = scanner.nextInt();
        System.out.print("Enter Win Length (k): ");
        int k = scanner.nextInt();

        if (k > m) {
            System.out.println("Error: k cannot be greater than m.");
            return;
        }

        Board board = new Board(m, k);
        AIAgent ai = new AIAgent();

        boolean useAlphaBeta = true;
        int depthLimit = (m > 3) ? 4 : 10;

        System.out.println("Game Start! You are 'O', AI is 'X'.");
        System.out.println(board);

        while (!board.isTerminal()) {
            if (board.getCurrentPlayer() == 'X') {

                System.out.println("AI ('X') is thinking...");
                int[] move = ai.getBestMove(board, useAlphaBeta, depthLimit);
                if (move != null) {
                    board = board.makeMove(move[0], move[1]);
                    System.out.println("AI played: " + move[0] + ", " + move[1]);
                }
            } else {

                System.out.println("Your Turn ('O'). Enter row and col:");
                int r = scanner.nextInt();
                int c = scanner.nextInt();
                if (r >= 0 && r < m && c >= 0 && c < m && board.getCell(r, c) == ' ') {
                    board = board.makeMove(r, c);
                } else {
                    System.out.println("Invalid move! Try again.");
                    continue;
                }
            }
            System.out.println(board);
        }

        char winner = board.checkWinner();
        if (winner != ' ') {
            System.out.println("Winner: " + winner);
        } else {
            System.out.println("Draw!");
        }
        scanner.close();
    }
}