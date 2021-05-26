package interfaces;

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

final public class GUIConsole {

    private static JTextArea console;
    private static JFrame frame;
    private static JScrollPane scrollPane;
    private static PrintStream outStream;

    public static void run(final String title,final int width, final int height){

        console = new JTextArea("",10, 30);
        frame = new JFrame(title);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        console.setEditable(false);
        console.setLineWrap(true);

        //Default JTextFrame font colors
        console.setBackground(Color.BLACK);
        console.setForeground(Color.WHITE);

        scrollPane = new JScrollPane(console);
        scrollPane.setBorder(new EmptyBorder(8, 8, 8, 8));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        frame.getContentPane().setLayout(new GridLayout());
        frame.getContentPane().add(scrollPane);
        frame.setResizable(true);
        frame.setLocation(200,100);

        //Listeners
        console.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {

                switch (e.getKeyCode()) {

                    case KeyEvent.VK_F9:
                        SwingUtilities.invokeLater(new Runnable(){

                            @Override
                            public void run() {
                                Color foreground = JColorChooser.showDialog(new JPanel(), "Choose Foreground color", Color.BLACK);
                                console.setForeground(foreground);
                            }

                        });
                        break;


                    case KeyEvent.VK_F10:

                        SwingUtilities.invokeLater(new Runnable() {

                            @Override
                            public void run() {
                                Color background = JColorChooser.showDialog(new JPanel(), "Choose Background color", Color.BLACK);
                                console.setBackground(background);
                            }

                        });
                        break;

                    default:
                        break;
                }//End of switch(KeyEvent e)

            }

                @Override
                public void keyReleased (KeyEvent e){}



        });

            outStream = new PrintStream(new OutputStream() {

                    @Override
                    public void write ( int b)throws IOException {
                    	try {
                    		GUIConsole.append(new String(Character.toChars(b)));
                    	}
                    	catch (Exception e1) {
                    		GUIConsole.append(new String(Character.toChars('_')));
                    	}
                        
                    }
                }
            );

            System.setOut(outStream);

            frame.pack();
            scrollPane.setVisible(true);
            frame.setVisible(true);
        }

    public static void setText(String text){
        console.setText(text);
    }

    public static void append(String text){
        console.append(text);
    }

    public static void append(int number){
        console.append("" + number);
    }

    public static void append(double number){
        console.append("" + number);
    }

    public static void append(boolean bool){
        console.append("" + bool);
    }

    public static String getText(){
        return console.getText();
    }

    public static void main(String[] args){
        
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        GUIConsole.run("MyGUIConsole",200,100);

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                java.util.ArrayList<Object> elements = new java.util.ArrayList<Object>();

                for( Object a : new Object[]{ "Testing", 12, 32.53f, 'c', 0x43, true }){
                    elements.add(a);
                }


                Iterator <Object> i =  elements.iterator();
                Object obj;
                for(obj = i.next(); i.hasNext(); obj = i.next()){
                    System.out.println(obj + " --- " + new Date() );
                }

            }
        });

    }

}
