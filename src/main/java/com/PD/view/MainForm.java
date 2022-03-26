package com.PD.view;

import static com.PD.controller.UberShuffleController.clickUberShuffle;

import com.PD.controller.GUIErrorController;
import com.PD.exceptions.ShuffleException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainForm {
  private JPanel rootPanel;
  private JTextField userIdTextField;
  private JTextField accessTokenTextField;
  private JTextField playlistSizeTextField;
  private JButton generateUberShufflePlaylistButton;
  private JLabel playlistSizeError;
  private static JFrame frame;

  public MainForm() {
    generateUberShufflePlaylistButton.addActionListener(new GenerateUberShufflePlaylistButtonActionListener());
    playlistSizeTextField.addKeyListener(new PlaylistSizeTextFieldKeyListener());
  }

  public static void main(String[] args) {
    frame = new JFrame("MainForm");
    frame.setContentPane(new MainForm().rootPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

  private class GenerateUberShufflePlaylistButtonActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      Thread uberShuffleThread = new Thread(() -> {
        try {
          clickUberShuffle(accessTokenTextField.getText(), userIdTextField.getText(),
              Integer.parseInt(playlistSizeTextField.getText()));
        } catch (ShuffleException ex) {
          JOptionPane.showMessageDialog(null, ex.getMessage(), "UberShuffle",
              JOptionPane.ERROR_MESSAGE);
        }
      });
      uberShuffleThread.start();
    }
  }

  private class PlaylistSizeTextFieldKeyListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) { checkPlaylistNumberError(); }
    @Override
    public void keyPressed(KeyEvent e) { checkPlaylistNumberError(); }
    @Override
    public void keyReleased(KeyEvent e) { checkPlaylistNumberError(); }
  }

  private void checkPlaylistNumberError() {
    if(GUIErrorController.isParsableNumber(playlistSizeTextField.getText())) {
      playlistSizeError.setVisible(false);
      generateUberShufflePlaylistButton.setEnabled(true);
    } else {
      playlistSizeError.setVisible(true);
      generateUberShufflePlaylistButton.setEnabled(false);
    }
    frame.pack();
  }
}
