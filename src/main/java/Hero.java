import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;

public class Hero {
    private int y;
    private int x;

    public Hero(int x, int y){
        setX(x);
        setY(y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void moveUp(){
        setY(getY() - 1);
    }

    public void moveDown(){
        setY(getY() + 1);
    }

    public void moveRight(){
        setX(getX() + 1);
    }

    public void moveLeft(){
        setX(getX() - 1);
    }

    public void draw(Screen screen){
        screen.setCharacter(x, y, TextCharacter.fromCharacter('X')[0]);
    }
}
