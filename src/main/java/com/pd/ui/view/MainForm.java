package com.pd.ui.view;

import com.pd.exceptions.ShuffleException;
import com.pd.ui.controller.AccessTokenController;
import com.pd.ui.controller.AuthorizationController;
import com.pd.ui.controller.GUIErrorController;
import com.pd.ui.controller.UberShuffleController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.NoSuchAlgorithmException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class MainForm {
  private JPanel rootPanel;
  private JTextField userIdTextField;
  private JTextField accessTokenTextField;
  private JTextField playlistSizeTextField;
  private JButton generateUberShufflePlaylistButton;
  private JLabel playlistSizeError;
  private JTextField clientIdTextField;
  private JButton sendAuthorizationButton;
  private JTextArea authUrlTextArea;
  private static JFrame frame;

  private final UberShuffleController shuffleController;
  private final AccessTokenController tokenController;
  private final AuthorizationController authorizationController;

  public MainForm(UberShuffleController shuffleController, AccessTokenController tokenController,
      AuthorizationController authorizationController) {
    if (this.rootPanel == null) {
      initUI();
    }
    generateUberShufflePlaylistButton.addActionListener(new GenerateUberShufflePlaylistButtonActionListener());
    sendAuthorizationButton.addActionListener(new SendAuthorizationButtonActionListener());
    playlistSizeTextField.addKeyListener(new PlaylistSizeTextFieldKeyListener());
    this.shuffleController = shuffleController;
    this.tokenController = tokenController;
    this.authorizationController = authorizationController;
  }

  private void initUI() {
    rootPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    gbc.insets = new Insets(2, 2, 2, 2);

    userIdTextField = new JTextField("User Id");
    userIdTextField.setPreferredSize(new Dimension(150, userIdTextField.getPreferredSize().height));
    gbc.gridx = 0;
    gbc.gridy = 0;
    rootPanel.add(userIdTextField, gbc);

    accessTokenTextField = new JTextField("AccessToken");
    accessTokenTextField.setPreferredSize(new Dimension(150, accessTokenTextField.getPreferredSize().height));
    gbc.gridy = 1;
    rootPanel.add(accessTokenTextField, gbc);

    playlistSizeTextField = new JTextField("Playlist Size");
    playlistSizeTextField.setPreferredSize(new Dimension(150, playlistSizeTextField.getPreferredSize().height));
    gbc.gridy = 2;
    rootPanel.add(playlistSizeTextField, gbc);

    playlistSizeError = new JLabel("Playlist Size must be a number.");
    playlistSizeError.setForeground(Color.RED);
    playlistSizeError.setVisible(false);
    gbc.gridy = 3;
    rootPanel.add(playlistSizeError, gbc);

    generateUberShufflePlaylistButton = new JButton("Generate UberShuffle Playlist");
    generateUberShufflePlaylistButton.setEnabled(false);
    gbc.gridy = 4;
    rootPanel.add(generateUberShufflePlaylistButton, gbc);

    clientIdTextField = new JTextField("Client Id");
    clientIdTextField.setPreferredSize(new Dimension(150, clientIdTextField.getPreferredSize().height));
    gbc.gridy = 5;
    rootPanel.add(clientIdTextField, gbc);

    authUrlTextArea = new JTextArea();
    authUrlTextArea.setEditable(false);
    authUrlTextArea.setPreferredSize(new Dimension(150, 50));
    gbc.gridy = 6;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weighty = 1.0;
    rootPanel.add(authUrlTextArea, gbc);

    sendAuthorizationButton = new JButton("Send Authorization");
    gbc.gridy = 7;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weighty = 0.0;
    rootPanel.add(sendAuthorizationButton, gbc);
  }

  private class GenerateUberShufflePlaylistButtonActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      Thread uberShuffleThread = new Thread(() -> {
        try {
          generateUberShufflePlaylistButton.setEnabled(false);
          tokenController.setNewAccessToken(accessTokenTextField.getText());
          shuffleController.clickUberShuffle(userIdTextField.getText(),
              Integer.parseInt(playlistSizeTextField.getText()));
        } catch (ShuffleException ex) {
          JOptionPane.showMessageDialog(null, ex.getMessage(), "UberShuffle",
              JOptionPane.ERROR_MESSAGE);
        } finally {
          generateUberShufflePlaylistButton.setEnabled(true);
        }
      });
      uberShuffleThread.start();
    }
  }

  private class SendAuthorizationButtonActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      Thread authorizationThread = new Thread(() -> {
        try {
          String authUri = authorizationController.clickSendAuthorization(clientIdTextField.getText());
          authUrlTextArea.setText(authUri);
          frame.pack();
        } catch (NoSuchAlgorithmException ex) {
          throw new RuntimeException(ex);
        }
      });
      authorizationThread.start();
    }
  }

  private class PlaylistSizeTextFieldKeyListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {
      checkPlaylistNumberError();
    }

    @Override
    public void keyPressed(KeyEvent e) {
      checkPlaylistNumberError();
    }

    @Override
    public void keyReleased(KeyEvent e) {
      checkPlaylistNumberError();
    }

    private void checkPlaylistNumberError() {
      if (GUIErrorController.isParsableNumber(playlistSizeTextField.getText())) {
        playlistSizeError.setVisible(false);
        generateUberShufflePlaylistButton.setEnabled(true);
      } else {
        playlistSizeError.setVisible(true);
        generateUberShufflePlaylistButton.setEnabled(false);
      }
      frame.pack();
    }
  }

  public void startUi() {
    setFrame();
    frame.setContentPane(this.rootPanel);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    // TODO: Figure out how to size this on the fly?
    frame.setPreferredSize(new Dimension(800, 300));

    authUrlTextArea.setLineWrap(true);
    authUrlTextArea.setWrapStyleWord(false);
    frame.pack();
    frame.setVisible(true);
  }

  private static void setFrame() {
    frame = new JFrame("MainForm");
  }
}
