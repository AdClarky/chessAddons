import javax.swing.JFileChooser;
import java.io.File;
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
        ChessGame chessGame = new ChessGame();
        BoardListener gameWindow = new GameWindow(chessGame, PieceColour.WHITE);
        chessGame.addBoardListener(gameWindow);
    }
}
