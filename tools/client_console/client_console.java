import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jack on 6/14/2016.
 */
public class client_console extends JFrame {
    private JTextField cc_input;
    private JButton cc_send;
    private JTextArea cc_display;
    private JPanel clientconsole_panel;

    public client_console() {

        setContentPane(clientconsole_panel);

        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cc_send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cc_input.getText() != null) {
                    cc_display.append(cc_input.getText() + "\n");
                    cc_input.setText(null);
                }
            }
        });
        setVisible(true);
        cc_input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cc_input.getText() != null) {
                    cc_display.append(cc_input.getText() + "\n");
                    cc_input.setText(null);
                }
            }
        });
    }
}
