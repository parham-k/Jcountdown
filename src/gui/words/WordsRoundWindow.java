package gui.words;

import javax.swing.*;
import java.awt.*;

public class WordsRoundWindow extends JFrame {

    public WordsRoundWindow() {
        super("Countdown - Words Round");
        WordsRoundPanel pnl = new WordsRoundPanel();
        setLayout(new BorderLayout());
        add(pnl, BorderLayout.CENTER);
        setVisible(true);
        pack();
        setSize(getWidth(), getHeight() + 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
