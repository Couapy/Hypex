package cloud.marchand.hypex.client;

import java.awt.Dimension;

import javax.swing.JFrame;

import cloud.marchand.hypex.client.controller.ResizeController;
import cloud.marchand.hypex.client.map.Map;
import cloud.marchand.hypex.core.models.Timer;


@SuppressWarnings("serial")
public class Window extends JFrame implements Runnable {

    private Map map;
    private Timer timer;
    private Canvas canvas;

    public Window(Map map) {
        this.map = map;
        this.timer = new Timer(144);

        initialize();
        
        Thread thread = new Thread(this);
        thread.start();
    }

    private void initialize() {
        // Configuration
        setTitle("Map Visualizer");
        setSize(new Dimension(1440, 1024));
        setLayout(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add canvas
        canvas = new Canvas(map);
        add(canvas);
        canvas.setBounds(0, 0, getWidth(), getHeight());

        // Add controllers
        this.addComponentListener(new ResizeController(this));
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
