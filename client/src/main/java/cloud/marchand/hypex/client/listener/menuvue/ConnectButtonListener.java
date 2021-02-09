package cloud.marchand.hypex.client.listener.menuvue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cloud.marchand.hypex.client.App;

public class ConnectButtonListener implements ActionListener {

    private App app;

    public ConnectButtonListener(App app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        app.connectServer();
    }

}
