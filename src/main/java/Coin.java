import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Coin extends Element{
    public Coin(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#cc9900"));
        graphics.disableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(this.getPosition().getX(), this.getPosition().getY()), "O");
    }
}
