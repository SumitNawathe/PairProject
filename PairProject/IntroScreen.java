import java.awt.*;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

public class IntroScreen extends JPanel {
	private int SCREEN_WIDTH, SCREEN_HEIGHT;
	private JButton startGameButton;
	
	public IntroScreen (GameFrame gameFrame, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
		this.SCREEN_WIDTH = SCREEN_WIDTH;
		this.SCREEN_HEIGHT = SCREEN_HEIGHT;
		this.setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		
		startGameButton = new JButton("START GAME");
	}
	public void paintComponent (Graphics g) {
		try {
			g.drawImage(ImageIO.read(new File("Textures/CoverImage1.png")), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
		} catch (Exception e) {}
	}
}
