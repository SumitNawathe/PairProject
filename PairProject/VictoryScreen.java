import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class VictoryScreen extends JPanel {
	private int SCREEN_WIDTH, SCREEN_HEIGHT;
	private double scaleX, scaleY;
	private JButton back;
 File clearSave; VictoryScreen (GameFrame gameFrame, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
		this.SCREEN_WIDTH = SCREEN_WIDTH;
		this.SCREEN_HEIGHT = SCREEN_HEIGHT;
		scaleX=SCREEN_WIDTH/1200.0;
		scaleY=SCREEN_HEIGHT/900.0;
		this.setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setLayout(null);
		
		back=new JButton("BACK");
		back.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent event) {
				gameFrame.goToIntroScreen();
			}
		});
		this.add(back);
		back.setSize(new Dimension(3*SCREEN_WIDTH/21, SCREEN_HEIGHT/21));
		back.setLocation(9*SCREEN_WIDTH/21-(int)(20*scaleX), 17*SCREEN_HEIGHT/21+(int)(15*scaleY));
	}

	public void paintComponent (Graphics g) {
		try {
			g.drawImage(ImageIO.read(new File("Textures/Ship Victory.png")), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
			g.drawImage(ImageIO.read(new File("Textures/victorysplash.png")), (int)(310*scaleX), 0, (int)(620*scaleX), (int)(175*scaleY), null);
		} catch (Exception e) {}
	}
}
