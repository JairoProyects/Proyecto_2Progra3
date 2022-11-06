package chatClient;

import chatClient.presentation.Controller;
import chatClient.presentation.Model;
import chatClient.presentation.View;
import chatProtocol.User;
import chatServer.Service;
import chatServer.data.Data;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

public class Application {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");}
        catch (Exception ex) {};

        window = new JFrame();

        Model model= new Model();
        View view = new View();
        Controller controller =new Controller(view, model);
        window.setSize(600,500);
        window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        window.setTitle("CHAT");
        try {
            window.setIconImage((new ImageIcon(Application.class.getResource("/logo.png"))).getImage());
        } catch (Exception e) {}
        window.setContentPane(view.getPanel());
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }

    public static JFrame window;
}
