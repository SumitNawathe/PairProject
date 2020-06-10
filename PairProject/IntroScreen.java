import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class IntroScreen extends JPanel {
	private int SCREEN_WIDTH, SCREEN_HEIGHT;
	private JButton startSave1, startSave2, startSave3;
	
	public IntroScreen (GameFrame gameFrame, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
		this.SCREEN_WIDTH = SCREEN_WIDTH;
		this.SCREEN_HEIGHT = SCREEN_HEIGHT;
		this.setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		
		this.setLayout(null);
		startSave1 = new JButton("START GAME 1");
		startSave1.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent event) {
//				gameFrame.startLevel(new EnemyLevel1());
				gameFrame.goToLevelSelectScreen("SaveFiles/SaveFile1.txt");
			}
		});
		startSave1.setSize(new Dimension(SCREEN_WIDTH/5, SCREEN_HEIGHT/20));
		startSave1.setLocation(SCREEN_WIDTH/4-SCREEN_WIDTH/5/2, 4*SCREEN_HEIGHT/5-SCREEN_HEIGHT/20/2);
		this.add(startSave1);
		
		startSave2 = new JButton("START GAME 2");
		startSave2.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent event) {
//				gameFrame.startLevel(new EnemyLevel1());
				gameFrame.goToLevelSelectScreen("SaveFiles/SaveFile2.txt");
			}
		});
		startSave2.setSize(new Dimension(SCREEN_WIDTH/5, SCREEN_HEIGHT/20));
		startSave2.setLocation(SCREEN_WIDTH/2-SCREEN_WIDTH/5/2, 4*SCREEN_HEIGHT/5-SCREEN_HEIGHT/20/2);
		this.add(startSave2);
		
		startSave3 = new JButton("START GAME 3");
		startSave3.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent event) {
//				gameFrame.startLevel(new EnemyLevel1());
				gameFrame.goToLevelSelectScreen("SaveFiles/SaveFile3.txt");
			}
		});
		startSave3.setSize(new Dimension(SCREEN_WIDTH/5, SCREEN_HEIGHT/20));
		startSave3.setLocation(3*SCREEN_WIDTH/4-SCREEN_WIDTH/5/2, 4*SCREEN_HEIGHT/5-SCREEN_HEIGHT/20/2);
		this.add(startSave3);
	}
	public void paintComponent (Graphics g) {
		try {
			g.drawImage(ImageIO.read(new File("Textures/CoverImage.png")), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
		} catch (Exception e) {}
	}
}
