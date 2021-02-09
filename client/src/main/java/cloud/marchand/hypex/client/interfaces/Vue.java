package cloud.marchand.hypex.client.interfaces;

import javax.swing.JPanel;

import cloud.marchand.hypex.client.App;

@SuppressWarnings("serial")
public abstract class Vue extends JPanel {

    protected App app;

    protected Vue(App app) {
        this.app = app;
    }

    public void load() {
    }

    public void unload() {
    }

}
