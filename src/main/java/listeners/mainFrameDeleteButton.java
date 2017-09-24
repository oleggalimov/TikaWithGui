package listeners;

import gui.mainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainFrameDeleteButton implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        int index = mainFrame.getListOfPatterns().getSelectedIndex();
        if (mainFrame.getDefaultListModel().isEmpty()) {
            JOptionPane.showMessageDialog(null,"Список пуст!","Ошибка удаления элемента",JOptionPane.ERROR_MESSAGE);
        } else if (index==-1) {
            JOptionPane.showMessageDialog(null,"Необходимо выбрать элемент!","Ошибка удаления элемента",JOptionPane.INFORMATION_MESSAGE);
        }else {
            mainFrame.getDefaultListModel().remove(index);
        }
    }
}
