

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
public class index {
    public static void main(String[] args) {
        //设置主界面
        JFrame mainFrame= new JFrame("WC.exe");
        mainFrame.setSize(400,400);
        mainFrame.setLayout(new GridLayout(3, 1));
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        //设置按钮
        JPanel controlPanel=new JPanel();
        JButton zifu= new JButton("wc.exe -c(字符数)");

        JButton danci = new JButton("wc.exe -w(单词数) ");
        JButton hangshu = new JButton("wc.exe -l(行数)");
        JButton a = new JButton("-a(代码行，注释行，空行)");
        controlPanel.add(zifu);
        controlPanel.add(hangshu);
        controlPanel.add(danci);
        controlPanel.add(a);
        //将按钮放入主界面
        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);

        //统计file文档的行数
        hangshu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int  k=line("file.c");
                JOptionPane.showMessageDialog(null, "file文件里面的行数为"+k);

            }});

        //统计file文档的字符数
        zifu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int  k=zifu("file.c");
                JOptionPane.showMessageDialog(null, "file文件里面的字符数为"+k+"（包括空格不包括回车）");

            }});

        //统计file文档的单词数
        danci.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int  k=word("file.c");
                JOptionPane.showMessageDialog(null, "file文件里面的单词数为"+k);

            }});


        a.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                a("file.c");


            }});

    }








    //统计file文档的字符数
    private static int zifu(String filename) {

        File file=new File(filename);
        BufferedReader reader=null;
        int character=0;
        try{
            reader = new BufferedReader(new FileReader(file));
            int tempchar;
            while ((tempchar=reader.read()) != -1) {
                if((char)tempchar!='\r'&&(char)tempchar!='\n'){
                    character++;
                }
            }
            reader.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "不存在该文件");
        }
        return character;
    }



    //统计file文档的单词数
    public static int word(String fileName) {
        File file = new File(fileName);
        int word = 0;
        BufferedReader reader = null;

        try {

            reader = new BufferedReader(new FileReader(file));

            Pattern Pa =Pattern.compile("[a-zA-Z]+\\b");//判定为单词的正则表达式条件

            String w=null;
            while ((w = reader.readLine()) != null) {
                Matcher Ma =Pa.matcher(w);
                while(Ma.find()) //当找到单词时，单词数+1
                    word++;
            }
            reader.close();
        } catch (IOException k) {
            k.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        // 返回单词数

        return word;
    }








    //统计file文档的行数
    public static int line(String fileName) {
        File file = new File(fileName);
        int line = 0;
        BufferedReader reader = null;
        String tempString = null;
        try {

            reader = new BufferedReader(new FileReader(file));


            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {

                line++;
            }
            reader.close();
        } catch (IOException l) {
            l.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        // 返回行数
        return line;

    }


    //-a扩展程序，统计代码行数，注释行数与空白行数
    public static void a(String fileName) {
        File file = new File(fileName);
        int blankcount = 0;   //空白行
        int notescount = 0;   //注释行
        int codecount = 0;    //代码行
        BufferedReader reader = null;
        String a = null;
        int state = 0;//判断是否有注释标记
        try {

            reader = new BufferedReader(new FileReader(file));


            // 一次读入一行，直到读入null为文件结束
            while ((a = reader.readLine()) != null) {
                a=remove(a,'{');
                a=remove(a,'}');   //去除干扰判断的{和}符号
                if(state == 0){        //注释没有标记时

                    if(a.trim().indexOf("//") == 0 || a.trim().indexOf("/*") == 0)
                        notescount++;

                    else if(a.trim().length() > 1) 	codecount++;
                    else blankcount++;
                    if(a.contains("/*"))   state = 1;  //如果有/*，则注释标记

                }
                else
                {

                    if(a.contains("*/")) {    //取消注释标记
                        state = 0;

                    }
                    notescount++;

                }
            }

            reader.close();
        } catch (IOException l) {
            l.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        JOptionPane.showMessageDialog(null, "file文件里面的空白行为"+blankcount+"\n代码行为"+codecount+"\n注释行为"+notescount);

    }
    //去除字符串中指定字符
    public static String remove(String sourceString, char chElemData) {
        String deleteString = "";
        for (int i = 0; i < sourceString.length(); i++) {
            if (sourceString.charAt(i) != chElemData) {
                deleteString += sourceString.charAt(i);
            }
        }
        return deleteString;
    }


}
