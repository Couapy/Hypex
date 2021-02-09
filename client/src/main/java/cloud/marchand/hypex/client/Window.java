package cloud.marchand.hypex.client;

import java.awt.Dimension;

import javax.swing.JFrame;

import cloud.marchand.hypex.client.listener.window.ResizeController;
import cloud.marchand.hypex.client.interfaces.Vue;
import cloud.marchand.hypex.client.listener.window.KeyboardController;

/**
 * Graphical interface displaying a vue.
 */
@SuppressWarnings("serial")
public class Window extends JFrame {

    /**
     * Current vue displayed.
     */
    private Vue vue;

    /**
     * Initialize a window from a size.
     * 
     * @param dimension size of the window
     * @param vue current vue
     */
    public Window(Dimension dimension, Vue vue) {
        // Configuration
        setTitle("hypex");
        setSize(dimension);
        setLayout(null);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Display vue
        setVue(vue);

        // Add controllers
        this.addComponentListener(new ResizeController(this));
        this.addKeyListener(new KeyboardController());
    }

    /**
     * Defines the current vue.
     * 
     * @param vue current vue
     */
    public void setVue(Vue vue) {
        if (vue != null) {
            if (this.vue != null) {
                this.vue.unload();
                remove(this.vue);
            }
            this.vue = vue;
            this.vue.load();
            add(vue);
            vue.setBounds(0, 0, getWidth(), getHeight());
        }
        revalidate();
        repaint();
    }

    /**
     * Get the current view.
     */
    public Vue getVue() {
        return vue;
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (vue != null) {
            if (visible) {
                vue.load();
            } else {
                vue.unload();
            }
        }
        revalidate();
        repaint();
    }

}
