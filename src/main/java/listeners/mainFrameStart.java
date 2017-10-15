package listeners;

import gui.mainFrame;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
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
        File fin = new File (jTextArea.getText());
        if (fin.exists()) {
            try {
                String resultFile = System.getProperty("user.home").concat("\\result.csv");
                FileOutputStream fout = new FileOutputStream (new File(resultFile));
                owner.setEnabled(false);
                int counter=0;
                String Fpath=null;
                String Fname=null;
                for (File f1 : fin.listFiles()) {
                    //получаем список файлов и идем по ним
                    counter++;
                    if (f1.listFiles()!=null) {
                        for (File x : f1.listFiles()) {
                            Fpath=x.getAbsolutePath();
                            fout.write((f1.getAbsolutePath()+"|"+Fpath+"|").getBytes());
                            fout.flush();


                            try (FileInputStream z = new FileInputStream (x)){
                                Tika tika = new Tika();


                                String s = tika.parseToString(z);//



                                for (int i=0; i< mainFrame.getDefaultListModel().getSize();i++) {
                                    Pattern current_pattern;
                                    Matcher current_muthcer;
                                    current_pattern=mainFrame.getDefaultListModel().getElementAt(i);
                                    current_muthcer=current_pattern.matcher(s);

                                    if (current_muthcer.find()) {
                                        fout.write((s.substring(current_muthcer.start(),current_muthcer.end())+"|").getBytes());
                                        fout.flush();
                                    } else {
                                        fout.write(("Паттерн "+current_pattern.toString()+ " не найден"+"|").getBytes());
                                        fout.flush();
                                    }
                                }
                                fout.write(("\n").getBytes());
                                fout.flush();

                            }catch (NullPointerException NPE)  {
                                fout.write((NPE.getMessage()+"|"+"\n").getBytes());
                                fout.flush();

                            }
                            catch (Exception ex) {
                                fout.write((ex.getMessage()+"|"+"\n").getBytes());
                                fout.flush();
                            }
                            Fpath=null;
                            Fname=null;
                        }
                    }
                    log.append("Обработан каталог "+f1+"\n");
                    log.update(log.getGraphics());
                }
                JOptionPane.showMessageDialog(owner,"Обработка завершена, результат записан в файл:\n"+resultFile,"Завершено", JOptionPane.INFORMATION_MESSAGE);
                owner.setEnabled(true);
            } catch (FileNotFoundException e1) {
                JOptionPane.showMessageDialog(owner,"Ошибка чтения файла","Ошибка",JOptionPane.ERROR_MESSAGE);
                if (owner.isEnabled()==false) {
                    owner.setEnabled(true);
                }
                owner.setEnabled(true);
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(owner,"Ошибка ввода - вывода","Ошибка",JOptionPane.ERROR_MESSAGE);
                if (owner.isEnabled()==false) {
                    owner.setEnabled(true);
                }
                owner.setEnabled(true);
            }
        } else {
            JOptionPane.showMessageDialog(owner,"Заданный каталог не существует!","Ошибка",JOptionPane.ERROR_MESSAGE);
            if (owner.isEnabled()==false) {
                owner.setEnabled(true);
            }
        }
    }
}
