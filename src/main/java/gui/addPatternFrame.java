package gui;

import oracle.jrockit.jfr.JFR;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public  class addPatternFrame extends JDialog {

    public addPatternFrame (JFrame owner) {
        super(owner,"Добавить паттерн",true);
        Dimension dimension = new Dimension(500,150);
        this.setSize(dimension);
        this.setLocationRelativeTo(null);
        GridLayout gridLayout = new GridLayout(2,0);
        gridLayout.setHgap(10);
        JPanel panel = new JPanel();
        panel.setLayout(gridLayout);

        JTextField pattern = new JTextField();
        pattern.setBorder(BorderFactory.createTitledBorder("Введите регулярное выражение"));
        panel.add(pattern);
        JButton addButton = new JButton("Добавить");
        addButton.addActionListener(e -> {
            try {
                String temp = pattern.getText();
                if (temp.equals("")||temp.length()==0) {
                    throw new Exception("Строка пустая!");
                } else {
                    mainFrame.getDefaultListModel().addElement(Pattern.compile(temp));
                }
            } catch (Exception ex) {
                if (ex==null) {
                    JOptionPane.showMessageDialog(null,"Не удалось добавить паттерн\n"
                            + ex.getClass()
                    );
                } else  {
                    JOptionPane.showMessageDialog(null,"Не удалось добавить паттерн\n"
                            + ex.getMessage()
                    );
                }

            }

        });
        panel.add(addButton);
        this.add(panel);
        this.setVisible(true);

    }
}
