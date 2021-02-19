package cloud.marchand.hypex.client.listener.window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class KeyboardController extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }
    
}
