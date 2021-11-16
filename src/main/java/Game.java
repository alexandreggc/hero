import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;

public class Game {
    private Screen screen;
    private final Arena arena;
    private final TextGraphics graphics;
    public static boolean over = false;
    public static boolean win = false;

    public Game(){
        final int WIDTH = 40;
        final int HEIGHT = 20;

        try {
            TerminalSize terminalSize = new TerminalSize(WIDTH, HEIGHT);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null);
            screen.startScreen();
            screen.doResizeIfNecessary();
        } catch (IOException e){
            e.printStackTrace();
        }

        arena = new Arena(WIDTH, HEIGHT);
        graphics = screen.newTextGraphics();
    }

    public void run() throws IOException {
        KeyStroke key;
        while(true){
            draw();
            if(Game.win) { drawGameWin(); break;}
            if(Game.over) { drawGameOver(); break;}
            key = screen.readInput();
            arena.processKey(key);
            if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q'){
                screen.close();
            }
            if (key.getKeyType() == KeyType.EOF){
                break;
            }
        }
        key = screen.readInput();
        screen.close();
    }

    private void draw() throws IOException {
        screen.clear();
        arena.draw(graphics);
        screen.refresh();
    }

    private void drawGameOver() throws IOException {
        graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(15, 10), "Game Over!");
        graphics.disableModifiers(SGR.BOLD, SGR.BLINK);
        graphics.putString(new TerminalPosition(9, 12), "Press any key to quit.");
        screen.refresh();
        System.out.println("Game Over");
    }

    private void drawGameWin() throws IOException {
        graphics.setForegroundColor(TextColor.Factory.fromString("#0000cc"));
        graphics.enableModifiers(SGR.BOLD,SGR.BLINK);
        graphics.putString(new TerminalPosition(11, 10), "Congrats, You Won!");
        graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));
        graphics.disableModifiers(SGR.BOLD, SGR.BLINK);
        graphics.putString(new TerminalPosition(9, 12), "Press any key to quit.");
        screen.refresh();
        System.out.println("You won!");
    }
}
