package gui;

import listeners.mainFrameAddButton;
import listeners.mainFrameDeleteButton;
import listeners.mainFrameOpenClose;
import listeners.mainFrameStart;

import javax.swing.*;
import javax.xml.stream.events.StartDocument;

import java.awt.*;
import java.util.regex.Pattern;

public class mainFrame extends JFrame {

    private static DefaultListModel<Pattern> defaultListModel = new DefaultListModel<>();
    private static JList<Pattern> listOfPatterns = new JList<>();

    public static DefaultListModel<Pattern> getDefaultListModel() {
        return defaultListModel;
    }

    public static JList<Pattern> getListOfPatterns() {
        return listOfPatterns;
    }

    public mainFrame() {
        listOfPatterns.setModel(defaultListModel);

        //Инициализируем главное окно
        Container container = this.getContentPane();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new mainFrameOpenClose());
        this.setTitle("Анализ текста");
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);


        Box mainBox = Box.createVerticalBox();

        Box bottomBox = Box.createHorizontalBox();
        JTextArea logPanel = new JTextArea();
        logPanel.setText("Поиск по регулярным выражениям. Версия 1.1. \nАвтор - О.Д. Галимов\n");
        logPanel.setBorder(BorderFactory.createTitledBorder("Журнал"));
        bottomBox.add(logPanel);

        Box topBox = Box.createHorizontalBox();

        JScrollPane leftPane = new JScrollPane(listOfPatterns);
        leftPane.setBorder(BorderFactory.createTitledBorder("Список паттернов поиска"));

        JPanel rightPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(4, 0);
        gridLayout.setVgap(10);
        rightPanel.setLayout(gridLayout);

        JButton CreateButton = new JButton("Добавить");
        CreateButton.addActionListener(new mainFrameAddButton(this));
        JButton DeleteButton = new JButton("Удалить");
        DeleteButton.addActionListener(new mainFrameDeleteButton());
        JTextField pathToFile = new JTextField("Укажите путь к файлам");
        pathToFile.setBorder(BorderFactory.createTitledBorder("Путь к файлам обработки"));
        JButton StatButon = new JButton("Начать обработку");
        StatButon.addActionListener(new mainFrameStart(logPanel,pathToFile,this));


        rightPanel.add(CreateButton);
        rightPanel.add(DeleteButton);

        rightPanel.add(pathToFile);
        rightPanel.add(StatButon);

        topBox.add(leftPane);
        topBox.add(rightPanel);


        mainBox.add(topBox);
        mainBox.add(bottomBox);

        this.getContentPane().add(mainBox);


    }
}
