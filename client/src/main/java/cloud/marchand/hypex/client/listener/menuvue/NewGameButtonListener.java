package cloud.marchand.hypex.client.listener.menuvue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cloud.marchand.hypex.client.App;

public class NewGameButtonListener implements ActionListener {

    private App app;

    public NewGameButtonListener(App app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        app.createGame();
    }
    
}
