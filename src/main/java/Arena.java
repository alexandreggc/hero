import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    private final int width;
    private final int height;
    private final Hero hero;
    private final List<Wall> walls;
    private final List<Coin> coins;
    private final List<Monster> monsters;

    public Arena(int width, int height){
        hero = new Hero(10, 10);
        this.width = width;
        this.height = height;
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonsters();
    }

    public void processKey(KeyStroke key){
        switch(key.getKeyType()){
            case ArrowDown -> moveHero(hero.moveDown());
            case ArrowUp -> moveHero(hero.moveUp());
            case ArrowLeft -> moveHero(hero.moveLeft());
            case ArrowRight -> moveHero(hero.moveRight());
        }
        verifyMonsterCollisions();
        moveMonsters();
    }

    public void draw(TextGraphics graphics){
        graphics.setBackgroundColor(TextColor.Factory.fromString("#90FCFA"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        //graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width * 2, height * 2), ' ');

        for(Wall wall : walls)
            wall.draw(graphics);
        for(Coin coin : coins)
            coin.draw(graphics);
        for(Monster monster : monsters)
            monster.draw(graphics);
        verifyMonsterCollisions();
        if(!Game.over) hero.draw(graphics);
        retrieveCoins();
    }

    private void moveHero(Position position){
        if(canHeroMove(position))
            hero.setPosition(position);
    }

    private boolean canHeroMove(Position position) {
        for (Wall wall : walls) {
            if(position.equals(wall.getPosition()))
                return false;
        }
        return true;
    }

    private boolean canMonsterMove(Position position) {
        for(Monster monster : monsters) {
            if(position.equals(monster.getPosition()))
                return false;
        }
        for(Coin coin : coins){
            if(position.equals(coin.getPosition()))
                return false;
        }
        for(Wall wall : walls){
            if(position.equals(wall.getPosition()))
                return false;
        }
        return true;
    }

    private boolean canPlaceCoin(Coin nextCoin, ArrayList<Coin> coins) {
        for(Coin coin : coins){
            if(coin.getPosition().equals(nextCoin.getPosition()))
                return false;
        }
        return !nextCoin.getPosition().equals(hero.getPosition());
    }

    private boolean canPlaceMonster(Monster nextMonster, ArrayList<Monster> monsters) {
        for(Monster monster : monsters){
            if(monster.getPosition().equals(nextMonster.getPosition()))
                return false;
        }
        for(Coin coin : coins){
            if(coin.getPosition().equals(nextMonster.getPosition()))
                return false;
        }
        return !nextMonster.getPosition().equals(hero.getPosition());
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

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            Coin coin = new Coin(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
            if(canPlaceCoin(coin, coins))
                coins.add(coin);
            else
                i -= 1;
        }
        return coins;
    }

    private List<Monster> createMonsters(){
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            Monster monster = new Monster(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
            if(canPlaceMonster(monster, monsters))
                monsters.add(monster);
            else
                i -= 1;
        }
        return monsters;
    }

    private void retrieveCoins(){
        for(Coin coin : coins){
            if(hero.getPosition().equals(coin.getPosition())){
                coins.remove(coin);
                break;
            }
        }
    }

    private void moveMonsters(){
        Position position;
        for(Monster monster : monsters){
            do {
                position = monster.move();
            } while(!canMonsterMove(position));
            monster.setPosition(position);
        }
    }

    public void verifyMonsterCollisions(){
        for(Monster monster : monsters){
            if(monster.getPosition().equals(hero.getPosition())){
                Game.over = true;
                break;
            }
        }
    }
}
