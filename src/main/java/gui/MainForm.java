package gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.beans.PropertyChangeListener;

/**
 * Created with IntelliJ IDEA.
 * User: subhash
 * Date: 3/24/13
 * Time: 4:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class MainForm {
    private JPanel mainPanel;
    private JTextField txtDownloadURL;
    private JButton btnDownload;
    private JProgressBar progressBar;
    private JLabel lblHeading;
    private JLabel lblName;
    private JTextField downloadURLTextField;

    public MainForm() {

        txtDownloadURL.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent mouseEvent) {
                txtDownloadURL.setText("");
            }
        });

        btnDownload.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        progressBar.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                progressBar.setValue(5);

            }
        });

        mainPanel.addComponentListener(new ComponentAdapter() {
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

    }
}
