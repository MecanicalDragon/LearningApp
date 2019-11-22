package oldLessons.javaChat.examples.ChatClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

class ChatFrame extends JFrame implements Runnable{
    private JTextField textField;
    private JTextArea textArea;
    private JButton send = new JButton("Send");

    ChatFrame(){
        setTitle("JavaChat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(350,150,600,400);
        setLayout(new BorderLayout());
        getTextArea();
        getChatString();
        getSidePanel();
        getMenuBar2();
        setVisible(true);
        new Thread(this).start();
    }

    private void getMenuBar2() {
        JMenuBar mainMenu = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Help");
        JMenuItem fileNew = new JMenuItem("New");
        JMenuItem fileExit = new JMenuItem("Exit");
        JMenuItem about = new JMenuItem("About");
        setJMenuBar(mainMenu);
        mainMenu.add(file);
        mainMenu.add(edit);
        file.add(fileNew);
        file.addSeparator();
        file.add(fileExit);
        edit.add(about);
        fileExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(null,"Thanks for chatting. Bye! :)");
            }
        });
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Made by @MechanicalDragon");
            }
        });
    }

    private void getSidePanel() {
        JPanel sidePanel = new JPanel(new BorderLayout());
        JLabel chatRoom = new JLabel("ChatRoom");
        sidePanel.add(chatRoom, BorderLayout.NORTH);
        sidePanel.setPreferredSize(new Dimension(100,0));
        JList<String> chatMembers = getStringJList();
        ScrollPane chatMembersList = new ScrollPane();
        chatMembersList.add(chatMembers);
        sidePanel.add(chatMembersList, BorderLayout.CENTER);
        add(sidePanel,BorderLayout.EAST);
    }

    private void getChatString() {
        JPanel chatString = new JPanel();
        chatString.setLayout(new BorderLayout());
        textField = new JTextField();
        textField.addActionListener(e -> sendMsg());
        chatString.add(textField, BorderLayout.CENTER);
        send.addActionListener(e ->sendMsg());
        send.setPreferredSize(new Dimension(99,0));
        chatString.add(send, BorderLayout.EAST);
        add(chatString, BorderLayout.SOUTH);
    }

    private void getTextArea() {
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setBackground(new Color(201,242,141));
        textArea.setFont(new Font("Arial", Font.PLAIN,14));
        JScrollPane textPane = new JScrollPane(textArea);
        add(textPane, BorderLayout.CENTER);
    }

    private JList<String> getStringJList() {
        JList<String>chatMembers = new JList<>(new DefaultListModel<>());
        ((DefaultListModel<String>)chatMembers.getModel()).addElement("You");
        ((DefaultListModel<String>)chatMembers.getModel()).addElement("Your ex");
        ((DefaultListModel<String>)chatMembers.getModel()).addElement("Your fat mom");
        ((DefaultListModel<String>)chatMembers.getModel()).addElement("Your jackass dad");
        ((DefaultListModel<String>)chatMembers.getModel()).addElement("Your imaginary friend");
        return chatMembers;
    }

    void sendMsg(){
        if (!textField.getText().trim().isEmpty()) {
            try {
                MainClass.output.writeUTF(textField.getText().trim());
                MainClass.output.flush();
//                System.out.println("Сообщение отправлено");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        textField.setText("");
        textField.grabFocus();
    }

    public void run(){
        while(true){
            try {
                textArea.append(MainClass.input.readUTF()+"\n");
//                System.out.println("Сообщение принято");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
