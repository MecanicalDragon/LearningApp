package oldLessons.javaChat.examples.Lesson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame {
    private JTextField jtf;
    private JTextArea jta;

    public Window(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("My Application");
        setBounds(90,50,600,400);
        setLayout(new FlowLayout());
        JButton jb1 = new JButton("Click");
        JButton jb2 = new JButton("Send");
        JButton jb3 = new JButton("Warning");
        jtf = new JTextField(30);
        jta = new JTextArea(10,25);
        jta.setLineWrap(true);
        jta.setEditable(false);
        jtf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMsg();
            }
        });
        jb1.setPreferredSize(new Dimension(70,30));
        jb2.setPreferredSize(new Dimension(70,30));
        jb3.setPreferredSize(new Dimension(70,30));
        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Warning message!");
            }
        });

        jb1.setBounds(30,30, 70,30);

        jb1.addActionListener(e -> System.out.println("clicked!"));

        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMsg();
            }
        });
//        add(jb1);
//        add(jtf);
//        add(jb2);
        JScrollPane jsp = new JScrollPane(jta);
        add(jsp);

        JPanel up = new JPanel(new BorderLayout());
        up.add(jb1, BorderLayout.WEST);
        up.add(jb2, BorderLayout.EAST);
        up.add(jtf, BorderLayout.CENTER);
        up.add(jb3, BorderLayout.SOUTH);
        add(up);
        Font f = new Font("Arial", Font.BOLD, 18);
        jta.setFont(f);


        setVisible(true);
    }
    public void sendMsg(){
        jta.append(jtf.getText()+"\n");
        jtf.setText("");
        jtf.grabFocus();
    }
}
