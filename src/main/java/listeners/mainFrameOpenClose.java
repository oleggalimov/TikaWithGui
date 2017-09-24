package listeners;


import gui.mainFrame;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.regex.Pattern;

public class mainFrameOpenClose implements WindowListener {
    private DefaultListModel <Pattern> temp = mainFrame.getDefaultListModel();
    private String currentDirectory = new File(".").getAbsolutePath();
    @Override
    public void windowOpened(WindowEvent e) {

            try (ObjectInputStream oin = new ObjectInputStream(new FileInputStream("patterns.dat"))) {
                DefaultListModel<Pattern> tempObj = (DefaultListModel<Pattern>) oin.readObject();
                for (int i=0;i<tempObj.size();i++) {
                    temp.addElement(tempObj.getElementAt(i));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"Не удалось восстановить список паттернов\n"
                        + ex.getClass()
                );
            }
        if (temp.size()==0) {
            temp.add(0,Pattern.compile("\\d{16}"));
            temp.add(0,Pattern.compile("\\d{4} \\d{4} \\d{4} \\d{4}"));
            temp.add(0,Pattern.compile("\\d{4} \\d{6}"));
            temp.add(0,Pattern.compile("\\d{10}"));
            temp.add(0,Pattern.compile("\\d{2} \\d{2} \\d{6}"));
            temp.add(0,Pattern.compile("ПАСПОРТ №\\d{6}"));
        }

    }

    @Override
    public void windowClosing(WindowEvent e) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("patterns.dat"))) {
            out.writeObject(mainFrame.getDefaultListModel());
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
