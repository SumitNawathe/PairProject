import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class LevelSelectScreen extends JPanel {
	private int SCREEN_WIDTH, SCREEN_HEIGHT;
	private GameFrame gameFrame;
	private ArrayList<LevelOption> levelOptionList;
	private Image currentImage;
	private String currentLevelIntroText;
	private Level currentLevel;
	private JButton startButton;
	LevelSelectScreen levelSelectScreen;
	
	public LevelSelectScreen (GameFrame gameFrame, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
		levelSelectScreen = this;
		this.SCREEN_WIDTH = SCREEN_WIDTH;
		this.SCREEN_HEIGHT = SCREEN_HEIGHT;
		this.setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setLayout(null);
		
		levelOptionList = new ArrayList<LevelOption>();
		levelOptionList.add(new LevelOption(new Level1(), "Textures/MarsImage1.jpg", "Hello.", SCREEN_WIDTH/10, SCREEN_HEIGHT/2));
		levelOptionList.add(new LevelOption(new LevelBoss(), "Textures/BlackHolePhoto1.jpg", "Hi There.", SCREEN_WIDTH/2, 4*SCREEN_HEIGHT/5));		
		
		this.addMouseListener(new MouseListener () {
			public void mousePressed (MouseEvent event) {
				for (LevelOption levelOption : levelOptionList)
					if (levelOption.clickedOnLevel(event.getX(), event.getY())) {
						currentImage = levelOption.getImage();
						currentLevelIntroText = levelOption.getLevelIntroText();
						currentLevel = levelOption.getLevel();
						levelSelectScreen.repaint();
						break;
					}
			}
			public void mouseReleased (MouseEvent event) {}
			public void mouseClicked (MouseEvent event) {}
			public void mouseExited (MouseEvent event) {}
			public void mouseEntered (MouseEvent event) {}
		});
		
		startButton = new JButton("START LEVEL");
		startButton.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent event) {
				gameFrame.startLevel(currentLevel);
			}
		});
		startButton.setSize(new Dimension(4*SCREEN_WIDTH/21, 2*SCREEN_HEIGHT/21));
		startButton.setLocation(16*SCREEN_WIDTH/21, 17*SCREEN_HEIGHT/21);
		this.add(startButton);
		
		currentImage = levelOptionList.get(0).getImage();
		currentLevelIntroText = levelOptionList.get(0).getLevelIntroText();
		currentLevel = levelOptionList.get(0).getLevel();
	}
	
	public void paintComponent (Graphics g) {
		try {
			g.drawImage(ImageIO.read(new File("Textures/GalaxyImage1.jpg")), 0, 0, 5*SCREEN_WIDTH/7, SCREEN_HEIGHT, null);
		} catch (Exception e) {}
		
		g.setColor(Color.LIGHT_GRAY);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(5*SCREEN_WIDTH/7, 0,  2*SCREEN_WIDTH/7, SCREEN_HEIGHT);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(5*SCREEN_WIDTH/7+4, 0+5,  2*SCREEN_WIDTH/7-14, SCREEN_HEIGHT-39);
		
		g.setColor(Color.RED);
		for (LevelOption levelOption : levelOptionList) {
			g.fillRect(levelOption.getX0(), levelOption.getY0(), LevelOption.SQUARE_SIZE, LevelOption.SQUARE_SIZE);
		}
		
		if (currentImage != null) {
			g.drawImage(currentImage, 16*SCREEN_WIDTH/21, 1*SCREEN_HEIGHT/21, 4*SCREEN_WIDTH/21, 5*SCREEN_HEIGHT/21, null);
		}
		if (currentLevelIntroText != null) {
			g.setColor(Color.RED);
			g.drawString(currentLevelIntroText, 16*SCREEN_WIDTH/21, 7*SCREEN_HEIGHT/21);
		}
	}
	
	private class LevelOption {
		private Level level;
		private String imagePath;
		private String levelIntroText;
		private int x0, y0;
		private static final int SQUARE_SIZE = 20;
		
		public Level getLevel () { return level; }
		public Image getImage () { try { return ImageIO.read(new File(imagePath)); } catch (Exception e) { return null; } }
		public String getLevelIntroText () { return levelIntroText; }
		public boolean clickedOnLevel (int x, int y) { return x0 < x && x < x0+SQUARE_SIZE && y0 < y && y < y0+SQUARE_SIZE; }
		public int getX0 () { return x0; }
		public int getY0 () { return y0; }
		
		public LevelOption (Level level, String imagePath, String levelIntroText, int x0, int y0) {
			this.level = level;
			this.imagePath = imagePath;
			this.levelIntroText = levelIntroText;
			this.x0 = x0;
			this.y0 = y0;
		}
	}
}
