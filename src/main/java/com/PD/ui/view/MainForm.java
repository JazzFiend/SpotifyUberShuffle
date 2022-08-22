package com.PD.ui.view;

import com.PD.exceptions.ShuffleException;
import com.PD.ui.controller.AccessTokenController;
import com.PD.ui.controller.GUIErrorController;
import com.PD.ui.controller.UberShuffleController;
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

  private UberShuffleController shuffleController;
  private AccessTokenController tokenController;

  public MainForm(UberShuffleController shuffleController, AccessTokenController tokenController) {
    generateUberShufflePlaylistButton.addActionListener(new GenerateUberShufflePlaylistButtonActionListener());
    playlistSizeTextField.addKeyListener(new PlaylistSizeTextFieldKeyListener());
    this.shuffleController = shuffleController;
    this.tokenController = tokenController;
  }

  private class GenerateUberShufflePlaylistButtonActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      Thread uberShuffleThread = new Thread(() -> {
        try {
          tokenController.setNewAccessToken(accessTokenTextField.getText());
          shuffleController.clickUberShuffle(userIdTextField.getText(), Integer.parseInt(playlistSizeTextField.getText()));
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

  public void startUi() {
    setFrame();
    frame.setContentPane(this.rootPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

  private static void setFrame() {
    frame = new JFrame("MainForm");
  }
}
