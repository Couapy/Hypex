package cloud.marchand.hypex.client.listener.menuvue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuitButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent arg0) {
        System.exit(0);
    }

}
