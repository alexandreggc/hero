import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.util.ArrayList;
import java.util.List;

public class Arena {
    private final int width;
    private final int height;
    private Hero hero;
    private List<Wall> walls;

    public Arena(int width, int height){
        this.width = width;
        this.height = height;
        this.walls = createWalls();
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
        for(Wall wall : walls)
            wall.draw(graphics);
        hero.draw(graphics);
    }

    private void moveHero(Position position){
        if(canHeroMove(position))
            hero.setPosition(position);
    }

    private boolean canHeroMove(Position position) {
        for (Wall wall : walls) {
            if (wall.getPosition().getX() == position.getX() && wall.getPosition().getY() == position.getY())
                return false;
        }
        return true;
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();
        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }
        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }
        return walls;
    }
}
