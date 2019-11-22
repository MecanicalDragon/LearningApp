package oldLessons.javaChat.examples.ExampleWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ExampleWindow  extends JFrame{
    public ExampleWindow(){
        setTitle("Example Window");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(350,150,600,400);
        setLayout(new GridLayout(3,2));

        JPanel[] pans = new JPanel[6];
        for (int i = 0;i<6;i++) {
            pans[i] = new JPanel();
            add(pans[i]);
            pans[i].setBackground(new Color(100+i*20, 100+i*20, 100+i*20));
        }

        pans[0].setLayout(null);
        ImageIcon image = new ImageIcon(getClass().getResource("/old_lessons/chatting.jpg"));
        JLabel imLabel = new JLabel(image);
        imLabel.setBounds(1,1,500,300);
        pans[0].add(imLabel);
        JButton[] buttons = new JButton[4];
        for (int i = 0, x = 25, y = 12; i<4 ; i++){
            buttons[i] = new JButton("Button "+(i+1));
            pans[0].add(buttons[i]).setBounds(x,y,100,40);
            if (i == 1) {x-=145; y+=52;}
            else if (i%2==0) x+=145;
            else y+=52;
        }

        pans[1].setLayout(new BorderLayout());
        JTextArea text = new JTextArea();
        JScrollPane scroll = new JScrollPane(text);
        pans[1].add(scroll);

        pans[2].setLayout(new GridLayout(2,3));
        JRadioButton[]radio = new JRadioButton[3];
        ButtonGroup radioGroup = new ButtonGroup();
        for (int i = 0; i<3; i++){
            radio[i] = new JRadioButton("Option "+(i+1));
            radioGroup.add(radio[i]);
            pans[2].add(radio[i]);
        }
        JCheckBox[]check = new JCheckBox[3];
        for (int i = 0; i<3; i++){
            check[i] = new JCheckBox("Prefence "+(i+1));
            pans[2].add(check[i]);
        }

        pans[3].setLayout(new FlowLayout());
        String[]combo1 = {"Add","Delete","Info"};
        String[]combo2 = {"Java","C++","Pascal","VBasic","Assembler"};
//        String[]combo3 = {"Anton","Constantine","Victoria","Michelle"};
        Integer[]combo4 = {1,2,3,4,5,6,7,8,9,0};
        JComboBox<String>options1 = new JComboBox<String>(combo1);
        JComboBox<String>options2 = new JComboBox<String>(combo2);
        JComboBox<Integer>options3 = new JComboBox<Integer>(combo4);
        pans[3].add(options1);
        pans[3].add(options2);
        pans[3].add(options3);
        options1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(options1.getSelectedItem().toString());
            }
        });
        options2.addActionListener(e -> System.out.println(options2.getSelectedItem().toString()));
        options3.addActionListener(e -> System.out.println(options3.getSelectedItem().toString()));

        pans[4].setLayout(null);
        JSlider slider = new JSlider();
        JLabel label = new JLabel("Value: 50");
        slider.setMaximum(100);
        slider.setMinimum(0);
        slider.setValue(50);
        slider.setBounds(20,40,260,60);
        slider.setBackground(new Color(160,255,160));
        label.setBounds(10,10,100,20);
        slider.addChangeListener(e -> label.setText("Value: "+slider.getValue()));
        pans[4].add(label);
        pans[4].add(slider);

        image = new ImageIcon(getClass().getResource("/old_lessons/chatting.jpg"));
        imLabel = new JLabel(image);
        pans[5].add(imLabel);

        JMenuBar mainMenu = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenuItem fileNew = new JMenuItem("New");
        JMenuItem editDel = new JMenuItem("Delete");
        JMenuItem fileExit = new JMenuItem("Exit");
        setJMenuBar(mainMenu);
        mainMenu.add(file);
        mainMenu.add(edit);
        file.add(fileNew);
        file.addSeparator();
        file.add(fileExit);
        edit.add(editDel);
        fileExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("BYE");
            }
        });

        setVisible(true);
    }
}
