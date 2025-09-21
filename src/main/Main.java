package main;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {

        JFrame window = new JFrame();

        ImageIcon icon = new ImageIcon(Main.class.getResource("/sprites/icon.png"));
        window.setIconImage(icon.getImage());

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Dreamscape");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}