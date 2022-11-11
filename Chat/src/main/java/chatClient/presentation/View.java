package chatClient.presentation;

import chatClient.Application;
import chatProtocol.IService;
import chatProtocol.Message;
import chatProtocol.User;
import chatServer.Service;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JButton Register;
    private JTable contactos;
    private JButton addContact;
    private JTextField addC;

    Model model;
    Controller controller;
    IService service;
    Message message;
    public View() {
        loginPanel.setVisible(true);
        Application.window.getRootPane().setDefaultButton(login);
        bodyPanel.setVisible(false);
        service = new Service();
        message = new Message();
        DefaultCaret caret = (DefaultCaret) messages.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
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
//                contactos.getSelectedRow(); // Me devuelve el numero de la fila seleccionada
                System.out.println(contactos.getSelectedRow());
                User receiver = controller.getModel().getUserAt(contactos.getSelectedRow());
                try {
                   if( receiver != null){
                       controller.post(text, receiver);
                    }
                   else{
                       JOptionPane.showMessageDialog(null, "Debe seleccionar un contacto de la lista de contactos!!");
                   }
                } catch (Exception ex) {
                   throw new RuntimeException(ex);
                }
            }
        });
        //REGISTER
        Register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField nombre = new JTextField("");
                Object[] fields = {
                        "Nombre:", nombre,
                };
                int option = JOptionPane.showConfirmDialog(panel, fields, id.getText(), JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        controller.register(new User(id.getText(), new String(clave.getPassword()), nombre.getText()));
                        } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        addContact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String contact = addC.getText();
                try {
                    controller.addContact(contact);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
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

        int[] cols = {TableModel.NOMBRE,TableModel.STATUS};
        contactos.setModel(new TableModel(cols, model.getUsers()));
        contactos.setRowHeight(30);

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
