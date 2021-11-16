import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        while (true){
            Game game = new Game();
            game.run();
            if(!game.playAgain())
                break;
        }
    }
}
