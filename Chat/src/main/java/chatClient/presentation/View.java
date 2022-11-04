package chatClient.presentation;

import chatClient.Application;
import chatProtocol.IService;
import chatProtocol.Message;
import chatProtocol.User;
import chatServer.Service;
import chatServer.data.UsuarioDao;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {
    private JPanel panel;
    private JPanel loginPanel;
    private JPanel bodyPanel;
    private JTextField id;
    private JPasswordField clave;
    private JButton login;
    private JButton finish;
    private JTextPane messages;
    private JTextField mensaje;
    private JButton post;
    private JButton logout;

    Model model;
    Controller controller;
    IService service;
    UsuarioDao usuarioDao;
    public View() {
        loginPanel.setVisible(true);
        Application.window.getRootPane().setDefaultButton(login);
        bodyPanel.setVisible(false);
        service = new Service();
        DefaultCaret caret = (DefaultCaret) messages.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        usuarioDao = new UsuarioDao();
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User u = new User(id.getText(), new String(clave.getPassword()), "");
                id.setBackground(Color.white);
                clave.setBackground(Color.white);
                try {
                    if(service.login(u) != null){
                        controller.login(u);
                        id.setText("");
                        clave.setText("");
                    } else {
                        id.setBackground(Color.red);
                        clave.setBackground(Color.red);
                        JOptionPane.showMessageDialog(null, "Usuario no registrado");
                    }
                } catch (Exception ex) {
                    id.setBackground(Color.red);
                    clave.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "Usuario no existe");
                }

//                try {
                    //usuarioDao.findByReferencia(id.getText()) != null
//                    for (int i = 0; i < usuarioDao.findAll().size(); i++) {
//                        if(usuarioDao.findAll().get(i).getId() == id.getText()){
//                            controller.login(u);
//                            id.setText("");
//                            clave.setText("");
//                        }
//                        else{
//                            id.setBackground(Color.red);
//                            clave.setBackground(Color.red);
//                        }
//                    }
//                        usuarioDao.read(id.getText());
//                    controller.login(u);
//                    System.out.println( usuarioDao.read(id.getText()).toString());
//                   if (id.getText() != null ) {
//                       controller.login(u);
//                       id.setText("");
//                       clave.setText("");
//                   }
//                   } else {
//                          id.setBackground(Color.red);
//                          clave.setBackground(Color.red);
//                          JOptionPane.showMessageDialog(null, "Usuario no existe");
//                   }
//                } catch (Exception ex) {
//                    System.out.println("Error en login");
//                    id.setBackground(Color.orange);
//                    clave.setBackground(Color.orange);
//                }
            }
        });
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.logout();
            }
        });
        finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        post.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = mensaje.getText();
                controller.post(text);
            }
        });
    }

    public void setModel(Model model) {
        this.model = model;
        model.addObserver(this);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public JPanel getPanel() {
        return panel;
    }

    String backStyle = "margin:0px; background-color:#e6e6e6;";
    String senderStyle = "background-color:#c2f0c2;margin-left:30px; margin-right:5px;margin-top:3px; padding:2px; border-radius: 25px;";
    String receiverStyle = "background-color:white; margin-left:5px; margin-right:30px; margin-top:3px; padding:2px;";

    public void update(java.util.Observable updatedModel, Object properties) {

        int prop = (int) properties;
        if (model.getCurrentUser() == null) {
            Application.window.setTitle("CHAT");
            loginPanel.setVisible(true);
            Application.window.getRootPane().setDefaultButton(login);
            bodyPanel.setVisible(false);
        } else {
            Application.window.setTitle(model.getCurrentUser().getNombre().toUpperCase());
            loginPanel.setVisible(false);
            bodyPanel.setVisible(true);
            Application.window.getRootPane().setDefaultButton(post);
            if ((prop & Model.CHAT) == Model.CHAT) {
                this.messages.setText("");
                String text = "";
                for (Message m : model.getMessages()) {
                    if (m.getSender().equals(model.getCurrentUser())) {
                        text += ("Me:" + m.getMessage() + "\n");
                    } else {
                        text += (m.getSender().getNombre() + ": " + m.getMessage() + "\n");
                     }
                }
                this.messages.setText(text);
            }
            this.mensaje.setText("");
        }
        panel.validate();
    }

}
