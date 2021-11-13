import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

public class Arena {
    private int width;
    private int height;
    private Hero hero;

    private void moveHero(Position position){
        if(canHeroMove(position))
            hero.setPosition(position);
    }

    private boolean canHeroMove(Position position){
        return position.getX() < width && position.getX() >= 0 && position.getY() < height && position.getY() >= 0;
    }

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

    public void draw(Screen screen){
        hero.draw(screen);
    }
}
