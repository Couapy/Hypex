package cloud.marchand.hypex.client.listener.gamevue;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import cloud.marchand.hypex.client.Pov;


public class KeyboardListener extends KeyAdapter {

    private Pov pov;

    private static final double POSITION_THRESHOLD = 0.5d;
    private static final double ANGLE_THRESHOLD = Math.PI / 6;

    public KeyboardListener(Pov pov) {
        this.pov = pov;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_Z) {
            pov.y -= POSITION_THRESHOLD;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            pov.y += POSITION_THRESHOLD;
        } else if (e.getKeyCode() == KeyEvent.VK_Q) {
            pov.x -= POSITION_THRESHOLD;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            pov.x += POSITION_THRESHOLD;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            pov.angle -= ANGLE_THRESHOLD;
        } else if (e.getKeyCode() == KeyEvent.VK_E) {
            pov.angle += ANGLE_THRESHOLD;
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }

}
