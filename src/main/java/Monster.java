import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;

public class Monster extends Element{
    public Monster(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#ff0000"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(this.getPosition().getX(), this.getPosition().getY()), "M");
    }

    public Position move(){
        int dx, dy;
        Random random = new Random();
        do {
            dx = random.nextInt(3) - 1;
            dy = random.nextInt(3) - 1;
        } while (dx == 0 && dy == 0);
        return new Position(this.getPosition().getX() + dx, this.getPosition().getY() + dy);
    }
}
