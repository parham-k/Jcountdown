package gui.words;

import game.DictionaryChecker;
import game.LetterGenerator;
import gui.Colors;
import sounds.TimerMusic;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

class WordsRoundPanel extends JPanel {

    private JTextField[] letters;
    private int nextLetter;
    private JButton btnConsonant, btnVowel, btnUndo, btnCountdown;
    private JLabel lblTimer;
    private JPanel pnlWordCheck;
    private boolean timerIsRunning;
    private TimerMusic timerMusic;
    private LetterGenerator letterGenerator;

    WordsRoundPanel() {
        super(new BorderLayout());

        nextLetter = 0;
        timerMusic = new TimerMusic();
        letterGenerator = new LetterGenerator();

        JPanel pnlLetters = new JPanel(new GridLayout(1, 9));
        letters = new JTextField[9];
        for (int i = 0; i < 9; i++) {
            letters[i] = new JTextField();
            letters[i].setFont(new Font("Arial", Font.BOLD, 35));
            letters[i].setHorizontalAlignment(JTextField.CENTER);
            letters[i].setEditable(false);
            pnlLetters.add(letters[i]);
        }
        add(pnlLetters, BorderLayout.NORTH);

        pnlWordCheck = new JPanel();

        JTextField txtWord = new JTextField(9);
        txtWord.setFont(new Font("Arial", Font.BOLD, 35));
        txtWord.setHorizontalAlignment(JTextField.CENTER);
        DocumentFilter filter = new UppercaseDocumentFilter();
        ((AbstractDocument) txtWord.getDocument()).setDocumentFilter(filter);
        pnlWordCheck.add(txtWord);

        JButton btnCheck = new JButton("Check");
        WordsRoundPanel parent = this;
        btnCheck.addActionListener((ActionEvent e) -> {
            try {
                boolean isInDictionary = DictionaryChecker.check(txtWord.getText());
                char[] selection = new char[9];
                for (int i = 0; i < 9; i++)
                    selection[i] = letters[i].getText().charAt(0);
                boolean isValid = DictionaryChecker.hasValidLetters(txtWord.getText(), selection);
                if (isInDictionary && isValid)
                    txtWord.setBackground(Colors.LIGHT_GREEN);
                else if (isInDictionary)
                    txtWord.setBackground(Colors.LIGHT_YELLOW);
                else
                    txtWord.setBackground(Colors.LIGHT_RED);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        pnlWordCheck.add(btnCheck, BorderLayout.EAST);

        lblTimer = new JLabel("30");
        lblTimer.setFont(new Font("Arial", Font.BOLD, 64));
        lblTimer.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel pnlButtons = new JPanel(new GridLayout(1, 2));

        btnConsonant = new JButton("CONSONANT");
        btnConsonant.addActionListener((ActionEvent e) -> addConsonant());
        pnlButtons.add(btnConsonant);

        btnVowel = new JButton("VOWEL");
        btnVowel.addActionListener((ActionEvent e) -> addVowel());
        pnlButtons.add(btnVowel);

        btnUndo = new JButton("UNDO");
        btnUndo.addActionListener((ActionEvent e) -> undo());
        pnlButtons.add(btnUndo);

        JButton btnReset = new JButton("RESET");
        btnReset.addActionListener((ActionEvent e) -> reset());
        pnlButtons.add(btnReset);

        btnCountdown = new JButton("COUNTDOWN!");
        btnCountdown.addActionListener((ActionEvent e) -> countdown());
        pnlButtons.add(btnCountdown);

        add(pnlButtons, BorderLayout.SOUTH);
    }

    private void addConsonant() {
        if (nextLetter < 9)
            letters[nextLetter++].setText(letterGenerator.getConsonant() + "");
    }

    private void addVowel() {
        if (nextLetter < 9)
            letters[nextLetter++].setText(letterGenerator.getVowel() + "");
    }

    private void undo() {
        if (nextLetter > 0) {
            --nextLetter;
            letters[nextLetter].setText("");
        }
    }

    private void countdown() {
        if (!timerIsRunning && nextLetter == 9) {
            WordsRoundPanel parent = this;
            Thread counter = new Thread(() -> {
                btnConsonant.setEnabled(false);
                btnVowel.setEnabled(false);
                btnUndo.setEnabled(false);
                btnCountdown.setText("STOP");
                int secondsLeft = 30;
                timerIsRunning = true;
                timerMusic.startMusic();
                remove(pnlWordCheck);
                add(lblTimer);
                revalidate();
                repaint();
                while (secondsLeft >= 0 && timerIsRunning) {
                    if (secondsLeft > 20)
                        lblTimer.setForeground(Colors.DARK_GREEN);
                    else if (secondsLeft > 10)
                        lblTimer.setForeground(Colors.ORANGE);
                    else
                        lblTimer.setForeground(Colors.DARK_RED);
                    lblTimer.setText(secondsLeft + "");
                    --secondsLeft;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                btnCountdown.setText("COUNTDOWN!");
                timerIsRunning = false;
                if (secondsLeft <= 0) {     // if the reset button wasn't pressed
                    btnCountdown.setEnabled(false);
                    parent.add(pnlWordCheck);
                    parent.remove(lblTimer);
                    parent.revalidate();
                    parent.repaint();
                }
            });
            counter.start();
        } else if (timerIsRunning) {
            timerIsRunning = false;
            timerMusic.stopMusic();
            remove(pnlWordCheck);
            remove(lblTimer);
            revalidate();
            repaint();
        }
    }

    private void reset() {
        for (JTextField tf : letters)
            tf.setText("");
        nextLetter = 0;
        btnConsonant.setEnabled(true);
        btnVowel.setEnabled(true);
        btnUndo.setEnabled(true);
        btnCountdown.setEnabled(true);
        timerIsRunning = false;
        timerMusic.stopMusic();
        remove(pnlWordCheck);
        remove(lblTimer);
        revalidate();
        repaint();
    }

}

class UppercaseDocumentFilter extends DocumentFilter {
    public void insertString(DocumentFilter.FilterBypass fb, int offset,
                             String text, AttributeSet attr) throws BadLocationException {

        fb.insertString(offset, text.toUpperCase(), attr);
    }

    public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
                        String text, AttributeSet attrs) throws BadLocationException {

        fb.replace(offset, length, text.toUpperCase(), attrs);
    }
}