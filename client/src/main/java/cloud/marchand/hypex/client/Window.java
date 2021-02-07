package cloud.marchand.hypex.client;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import cloud.marchand.hypex.client.controller.KeyboardController;
import cloud.marchand.hypex.client.controller.ResizeController;
import cloud.marchand.hypex.core.models.Timer;


@SuppressWarnings("serial")
public class Window extends JFrame implements Runnable {

    private Timer timer;
    private Canvas canvas;

    public Window(Dimension dimension, Canvas canvas) {
        this.timer = new Timer(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getRefreshRate());
        this.canvas = canvas;

        initialize(dimension);
        
        Thread thread = new Thread(this);
        thread.start();
    }

    private void initialize(Dimension dimension) {
        // Configuration
        setTitle("Map Visualizer");
        setSize(dimension);
        setLayout(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add canvas
        add(canvas);
        canvas.setBounds(0, 0, getWidth(), getHeight());

        // Add controllers
        this.addComponentListener(new ResizeController(this));
        this.addKeyListener(new KeyboardController());
    }

    @Override
    public void run() {
        while (true) {
            timer.limitRefreshRate();
            revalidate();
            repaint();
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }
    
}
