package cloud.marchand.hypex.client.vues;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import cloud.marchand.hypex.client.App;
import cloud.marchand.hypex.client.interfaces.Vue;
import cloud.marchand.hypex.client.listener.menuvue.ConnectButtonListener;
import cloud.marchand.hypex.client.listener.menuvue.NewGameButtonListener;
import cloud.marchand.hypex.client.listener.menuvue.QuitButtonListener;

@SuppressWarnings("serial")
public class MenuVue extends Vue {

    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 50;

    private JPanel panel;

    private JButton newGameButton;
    private JButton connectButton;
    private JButton quitButton;

    public MenuVue(App app) {
        super(app);
        initialize();

        // Add listeners
        newGameButton.addActionListener(new NewGameButtonListener(app));
        connectButton.addActionListener(new ConnectButtonListener(app));
        quitButton.addActionListener(new QuitButtonListener());
    }

    private void initialize() {
        setBackground(Color.GRAY);

        // Create elements
        panel = new JPanel();
        // panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(CENTER_ALIGNMENT);
        newGameButton = new JButton("New Game");
        connectButton = new JButton("Connect to a server");
        quitButton = new JButton("Quit");

        // Set size
        newGameButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        connectButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        quitButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        // Add elements
        panel.add(newGameButton);
        panel.add(connectButton);
        panel.add(quitButton);
        add(panel);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        setBackground(Color.GRAY);
        // Positionnate elements
        int panelPositionX = (getWidth() - panel.getWidth()) / 2;
        int panelPositionY = (getHeight() - panel.getHeight()) / 2;
        panel.setBounds(panelPositionX, panelPositionY, panel.getWidth(), panel.getHeight());
    }

}
