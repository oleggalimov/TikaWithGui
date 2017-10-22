package listeners;

import gui.mainFrame;
import org.apache.tika.Tika;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class mainFrameStart implements ActionListener {
    JTextArea log;
    JTextField jTextArea;
    JFrame owner;

    public mainFrameStart (JTextArea log, JTextField jTextArea, JFrame owner) {
        this.log=log;
        this.jTextArea=jTextArea;
        this.owner=owner;

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        File fin = new File (jTextArea.getText());
        if (fin.exists()) {
            try {
                String resultFile = System.getProperty("user.home").concat("\\result.csv");
                FileOutputStream fout = new FileOutputStream (new File(resultFile));
                owner.setEnabled(false);
                int counter=0;


                for (File f1 : fin.listFiles()) {
                    //получаем список файлов и идем по ним
                    counter++;
                    if (f1.listFiles()!=null) {

                        for (File x : f1.listFiles()) {
                            StringBuilder sb = new StringBuilder();
                            //впилены потоки - 4 шт
                            executorService.submit(()->{
                                sb.append(f1.getAbsolutePath()+"|"+x.getAbsolutePath()+"|");
                                try (FileInputStream z = new FileInputStream (x)){
                                    Tika tika = new Tika();
                                    String s = tika.parseToString(z);//
                                    for (int i=0; i< mainFrame.getDefaultListModel().getSize();i++) {
                                        Pattern current_pattern;
                                        Matcher current_muthcer;
                                        current_pattern=mainFrame.getDefaultListModel().getElementAt(i);
                                        current_muthcer=current_pattern.matcher(s);
                                        if (current_muthcer.find()) {
                                            sb.append(s.substring(current_muthcer.start(),current_muthcer.end())+"|");
                                        } else {
                                            sb.append("Паттерн "+current_pattern.toString()+ " не найден"+"|");
                                        }
                                    }
                                    fout.write((sb+"\n").getBytes());
                                    fout.flush();
                                }catch (NullPointerException NPE)  {
                                    try {
                                        sb.append(NPE.getMessage() + "|" + "\n");
                                        fout.write((sb.toString()).getBytes());
                                        fout.flush();
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                                catch (Exception exe) {
                                    try {
                                        sb.append(exe.getMessage() + "|" + "\n");
                                        fout.write((sb.toString()).getBytes());
                                        fout.flush();
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                                log.append("Обработан каталог "+f1+"\n");
                                log.update(log.getGraphics());

                            });


                        }

                    }


                }
                executorService.shutdown();
                while (!executorService.isTerminated()) {
                    ;
                }
                JOptionPane.showMessageDialog(owner,"Обработка завершена, результат записан в файл:\n"+resultFile,"Завершено", JOptionPane.INFORMATION_MESSAGE);
                owner.setEnabled(true);
            } catch (FileNotFoundException e1) {
                JOptionPane.showMessageDialog(owner,"Ошибка чтения файла","Ошибка",JOptionPane.ERROR_MESSAGE);
                if (!owner.isEnabled()) {
                    owner.setEnabled(true);
                }
                owner.setEnabled(true);
            }
        } else {
            JOptionPane.showMessageDialog(owner,"Заданный каталог не существует!","Ошибка",JOptionPane.ERROR_MESSAGE);
            if (!owner.isEnabled()) {
                owner.setEnabled(true);
            }
        }

    }
}
