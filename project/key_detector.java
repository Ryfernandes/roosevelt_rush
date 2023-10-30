import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Skeleton frame of class from tutorial
public class KeyDetector implements KeyListener{
    private GraphicsManager graphics;

    public KeyDetector(GraphicsManager graphics) {
        this.graphics = graphics;
    }

    public void keyTyped(KeyEvent e) {
    }
    
    public void keyPressed(KeyEvent e) {
        graphics.registerKeyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        graphics.registerKeyReleased(e);
    }
}
