package com.PD.view;

import static com.PD.controller.UberShuffleController.clickUberShuffle;

import com.PD.exceptions.ShuffleException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainForm {
  public MainForm() {
    generateUberShufflePlaylistButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Thread uberShuffleThread = new Thread() {
          public void run() {
            try {
              clickUberShuffle(accessTokenTextField.getText(), userIdTextField.getText(),
                  Integer.parseInt(playlistSizeTextField.getText()));
            } catch (ShuffleException ex) {
              JOptionPane.showMessageDialog(null, ex.getMessage(), "UberShuffle",
                  JOptionPane.ERROR_MESSAGE);
            }
          }
        };
        uberShuffleThread.start();
      }
    });
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("MainForm");
    frame.setContentPane(new MainForm().rootPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

  private JPanel rootPanel;
  private JTextField userIdTextField;
  private JTextField accessTokenTextField;
  private JTextField playlistSizeTextField;
  private JButton generateUberShufflePlaylistButton;
}
