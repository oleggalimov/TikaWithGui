package listeners;


import gui.addPatternFrame;
import gui.mainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainFrameAddButton implements ActionListener {
    JFrame owner;
    public mainFrameAddButton (JFrame owner) {
        this.owner=owner;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JDialog addPatternFrame = new addPatternFrame( owner);
    }
}
