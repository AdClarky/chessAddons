import chessboard.Board;
import chessboard.BoardListener;
import chessboard.Piece;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Used to run the program. Creates a window in a new thread.
 */
public final class Driver {
    private Driver(){}

    /**
     * Runs the program opening a game window.
     * @param args none taken.
     */
    public static void main(String[] args) {
        Board board = new Board();
        BoardListener whitePieces = new GameWindow(board, Piece.WHITE_PIECE);
        board.addBoardListener(whitePieces);
        new Thread(() -> {
            try {
                Server.main(new String[]{"4444"});
            } catch (IOException e) {
                System.err.println("error");
            }
        }).start();
        new Thread(() -> {
            Client client = new Client("192.168.1.91", 4444, board);
            board.addBoardListener(client);
            client.listenForMessage();
        }).start();
    }
}
