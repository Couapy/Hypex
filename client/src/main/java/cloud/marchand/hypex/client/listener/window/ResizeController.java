package cloud.marchand.hypex.client.listener.window;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import cloud.marchand.hypex.client.Window;
import cloud.marchand.hypex.client.interfaces.Vue;
/**
 * Control the resize event of the window.
 */
public class ResizeController extends ComponentAdapter {

    /**
     * Window observed.
     */
    private Window window;

    /**
     * Initialize the controller.
     * 
     * @param window window to observe
     */
    public ResizeController(Window window) {
        this.window = window;
    }

    /**
     * Defines a new size to the canvas.
     * @param e graphical event send by the frame
     */
    @Override
    public void componentResized(ComponentEvent e) {
        Vue vue = window.getVue();
        if (vue != null) {
            vue.setBounds(0, 0, window.getWidth(), window.getHeight());
            vue.revalidate();
            vue.repaint();
        }
    }
    
}
