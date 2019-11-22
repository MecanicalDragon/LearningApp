package oldLessons.javaChat.view;

import oldLessons.javaChat.model.Server;
import oldLessons.javaChat.model.SessionData;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;

public class StartingFrame extends JFrame {

    private JTextField name;
    private enum Mode{ SERVER_ONLY, SERVER_CLIENT, CLIENT_ONLY }
    private Mode mode = Mode.CLIENT_ONLY;
    private String ip = "localhost";
    private JTextField portField;


    public StartingFrame(){
        setTitle("Java Chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(350,150,150,230);
        setLayout(new BorderLayout());
        setBackground(new Color(200,140,220));
//        setResizable(false);
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("    I just want chatting!    ", getChatTab());
        tabs.addTab("    Server/Client options    ", getOptions());
        tabs.addTab("     Connection settings    ", getSettings());
        add(tabs);
        JButton ok = new JButton("Confirm.");
        ok.addActionListener(e -> confirm());
        add(ok, BorderLayout.SOUTH);
        setVisible(true);
    }

    private JPanel getChatTab(){
        JPanel chat = new JPanel();
        chat.add(new JLabel("Enter your name:"));
        name = new JTextField(16);
        name.grabFocus();
        name.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
                if (str == null) return;
                if ((getLength() + str.length()) <= 16) super.insertString(offset, str, attr);
            }
        });
        name.addActionListener(e -> confirm());
        chat.add(name);
        chat.add(new JLabel(new ImageIcon(StartingFrame.class.getResource("/old_lessons/chatting.jpg"))));
        return chat;
    }

    private JPanel getOptions(){
        JPanel options = new JPanel();
        options.setLayout(new GridLayout(4,1));
        options.add(new JLabel("   Select launching mode:"));
        ButtonGroup ops = new ButtonGroup();
        JRadioButton serverOnly = new JRadioButton("Server only");
        ops.add(serverOnly);
        serverOnly.addActionListener(e -> mode = Mode.SERVER_ONLY);
        options.add(serverOnly);
        JRadioButton serverClient = new JRadioButton("Server+client");
        ops.add(serverClient);
        serverClient.addActionListener(e -> mode = Mode.SERVER_CLIENT);
        options.add(serverClient);
        JRadioButton clientOnly = new JRadioButton("Client only");
        ops.add(clientOnly);
        clientOnly.addActionListener(e -> mode = Mode.CLIENT_ONLY);
        options.add(clientOnly);
        return options;
    }

    private JPanel getSettings(){
        JPanel settings = new JPanel();
        settings.setLayout(new GridLayout(4,1));
        // IP field
        JPanel ip = new JPanel();
        ip.add(new JLabel("server IP:"));
        JTextField ipField = new JTextField(11);
        ip.add(ipField);
        settings.add(ip);
        // port field
        JPanel port = new JPanel();
        port.setLayout(null);
        JLabel p = new JLabel(" port:");
        p.setBounds(0,4,45,20);
        port.add(p);
        portField = new JTextField(4);
        portField.setBounds(67,4,50,20);
        port.add(portField);
        settings.add(port);
        // encryption
        settings.add(new JLabel(" Encryption key:"));
        JTextField encryption = new JTextField(4);
        settings.add(encryption);
        return settings;
    }

    private void confirm(){
        switch (mode) {
            case SERVER_ONLY:
                serverStart();
                break;
            case CLIENT_ONLY:
                clientStart();
                break;
            case SERVER_CLIENT:
                serverStart();
                clientStart();
                break;
        }
    }

    private void serverStart() {
        int port = 9000;
        do {
            try {
                if (!portField.getText().trim().isEmpty()) port = Integer.parseInt(portField.getText());
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null,"port must be 0~65535");
                break;
            }
        }while (0<=port&&port<=65535);
        new Server(port).start();

    }

    private void clientStart(){
        if (!name.getText().trim().isEmpty()) {
            SessionData.name = name.getText();

        }
    }

}
