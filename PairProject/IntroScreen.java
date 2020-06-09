import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class IntroScreen extends JPanel {
	private int SCREEN_WIDTH, SCREEN_HEIGHT;
	private JButton startGameButton;
	
	public IntroScreen (GameFrame gameFrame, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
		this.SCREEN_WIDTH = SCREEN_WIDTH;
		this.SCREEN_HEIGHT = SCREEN_HEIGHT;
		this.setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		
		this.setLayout(null);
		startGameButton = new JButton("START GAME");
		startGameButton.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent event) {
				gameFrame.startLevel(new EnemyLevel1());
			}
		});
		startGameButton.setSize(new Dimension(SCREEN_WIDTH/5, SCREEN_HEIGHT/20));
		startGameButton.setLocation(SCREEN_WIDTH/2-SCREEN_WIDTH/5/2, 4*SCREEN_HEIGHT/5-SCREEN_HEIGHT/20/2);
		this.add(startGameButton);
	}
	public void paintComponent (Graphics g) {
		try {
			g.drawImage(ImageIO.read(new File("Textures/CoverImage.png")), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
		} catch (Exception e) {}
	}
}
