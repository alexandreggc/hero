import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;

public class Game {
    private Screen screen;
    private Arena arena;

    public Game(){
        final int WIDTH = 40;
        final int HEIGHT = 20;

        arena = new Arena(WIDTH, HEIGHT);
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
    }

    public void run() throws IOException {
        KeyStroke key;
        while (true){
            draw();
            key = screen.readInput();
            if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q'){
                screen.close();
            }
            if (key.getKeyType() == KeyType.EOF){
                break;
            }
            processKey(key);
        }
    }

    private void processKey(KeyStroke key) throws IOException {
        arena.processKey(key);
    }

    private void draw() throws IOException {
        screen.clear();
        arena.draw(screen.newTextGraphics());
        screen.refresh();
    }
}
