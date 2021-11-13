import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

public class Arena {
    private final int width;
    private final int height;
    private Hero hero;

    public Arena(int width, int height){
        this.width = width;
        this.height = height;
        hero = new Hero(10, 10);
    }

    public void processKey(KeyStroke key){
        switch(key.getKeyType()){
            case ArrowDown -> moveHero(hero.moveDown());
            case ArrowUp -> moveHero(hero.moveUp());
            case ArrowLeft -> moveHero(hero.moveLeft());
            case ArrowRight -> moveHero(hero.moveRight());
        }
    }

    public void draw(TextGraphics graphics){
        graphics.setBackgroundColor(TextColor.Factory.fromString("#90FCFA"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        //graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width * 2, height * 2), ' ');
        hero.draw(graphics);
    }

    private void moveHero(Position position){
        if(canHeroMove(position))
            hero.setPosition(position);
    }

    private boolean canHeroMove(Position position){
        return position.getX() < width && position.getX() >= 0 && position.getY() < height && position.getY() >= 0;
    }

}
