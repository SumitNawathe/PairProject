import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class LevelSelectScreen extends JPanel {
	private int SCREEN_WIDTH, SCREEN_HEIGHT;
	private String SAVEDATA_LOCATION;
	private GameFrame gameFrame;
	private ArrayList<LevelOption> levelOptionList;
	private ArrayList<String> introTexts;
	private Image currentImage;
	private String currentLevelIntroText;
	private Level currentLevel;
	private JButton startButton;
	LevelSelectScreen levelSelectScreen;
	private String saveFileLocation;
	
	public LevelSelectScreen (GameFrame gameFrame, int SCREEN_WIDTH, int SCREEN_HEIGHT, String SAVEDATA_LOCATION) {
		levelSelectScreen = this;
		this.SCREEN_WIDTH = SCREEN_WIDTH;
		this.SCREEN_HEIGHT = SCREEN_HEIGHT;
		this.SAVEDATA_LOCATION = SAVEDATA_LOCATION;
		this.setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setLayout(null);
		
		levelOptionList = new ArrayList<LevelOption>();
		try {
			System.out.println("1");
			BufferedReader file = new BufferedReader(new FileReader(SAVEDATA_LOCATION));
			System.out.println("2");
			StringTokenizer st = new StringTokenizer(file.readLine());
			System.out.println("3");
			levelOptionList.add(new LevelOption(new Level1(), "Textures/MarsImage1.jpg", "Welcome, recruit! To serve in the legendary Arwing squadron you must first pass this training course in the orbit of Mars. "
					+ "Your supervisor will provide you with instructions. Good luck!", SCREEN_WIDTH/10, SCREEN_HEIGHT/2, Boolean.parseBoolean(st.nextToken()), Double.parseDouble(st.nextToken())));
			System.out.println("4");
			st = new StringTokenizer(file.readLine());
			System.out.println("5");
			levelOptionList.add(new LevelOption(new LevelBoss(), "Textures/BlackHolePhoto1.jpg", "The FitnessGram™ Pacer Test is a multistage aerobic capacity test that progressively gets more difficult as it continues. The 20 meter pacer test will begin in 30 seconds. Line up at the start. The running speed starts slowly, but gets faster each minute after you hear this signal. [beep] A single lap should be completed each time you hear this sound. [ding] Remember to run in a straight line, and run as long as possible. The second time you fail to complete a lap before the sound, your test is over. The test will begin on the word start. On your mark, get ready, start.", SCREEN_WIDTH/2, 4*SCREEN_HEIGHT/5, Boolean.parseBoolean(st.nextToken()),  Double.parseDouble(st.nextToken())));		
			System.out.println("6");
			file.close();
		} catch (Exception e) { System.out.println(e); }
		this.addMouseListener(new MouseListener () {
			public void mousePressed (MouseEvent event) {
				for (LevelOption levelOption : levelOptionList)
					if (levelOption.clickedOnLevel(event.getX(), event.getY())) {
						currentImage = levelOption.getImage();
						currentLevelIntroText = levelOption.getLevelIntroText();
						currentLevel = levelOption.getLevel();
						introTexts=breakText(currentLevelIntroText);
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
		
		if (levelOptionList.size() > 0) {
			currentImage = levelOptionList.get(0).getImage();
			currentLevelIntroText = levelOptionList.get(0).getLevelIntroText();
			currentLevel = levelOptionList.get(0).getLevel();
		}
		introTexts=breakText(currentLevelIntroText);
	}
	
	private ArrayList<String> breakText(String string){
		ArrayList<String> ret=new ArrayList<String>();
		char[] text=string.toCharArray();
		while (string.length()>28) {
			boolean spaceFound=false;
			int i;
			for (i=28;i>0;i--) {
				i--;
				if (text[i]==' ') {
					break;
				}
			}
			String add=string.substring(0,i+1);
			ret.add(add);
			string=string.substring(i+1);
			text=string.toCharArray();
		}
		ret.add(string);
		return ret;
	}
	
	public void updateSAVEDATA (int levelNum, boolean completed, double health) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(SAVEDATA_LOCATION)));
			for (int i = 0; i < levelOptionList.size(); i++) {
				if (i == levelNum) {
					out.write(completed + " " + health + "\n");
					levelOptionList.get(i).setSAVEDATA_COMPLETED(completed);
					levelOptionList.get(i).setSAVEDATA_HEALTH(health);
				} else {
					out.write(levelOptionList.get(i).getSAVADATA_COMPLETED() + " " + levelOptionList.get(i).getSAVEDATA_HEALTH() + "\n");
				}
			}
			out.close();
		} catch (Exception e) {}
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
		
//		g.setColor(Color.RED);
		for (LevelOption levelOption : levelOptionList) {
			if (levelOption.SAVEDATA_COMPLETED)
				g.setColor(Color.GREEN);
			else
				g.setColor(Color.RED);
			g.fillRect(levelOption.getX0(), levelOption.getY0(), LevelOption.SQUARE_SIZE, LevelOption.SQUARE_SIZE);
		}
		
		if (currentImage != null) {
			g.drawImage(currentImage, 16*SCREEN_WIDTH/21, 1*SCREEN_HEIGHT/21, 4*SCREEN_WIDTH/21, 5*SCREEN_HEIGHT/21, null);
		}
		if (currentLevelIntroText != null) {
			g.setColor(Color.BLACK);
			g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
			for (int i=0;i<introTexts.size();i++) {
				g.drawString(introTexts.get(i), 16*SCREEN_WIDTH/21, 7*SCREEN_HEIGHT/21+12*i);
			}
		}
	}
	
	private class LevelOption {
		private boolean SAVEDATA_COMPLETED;
		private double SAVEDATA_HEALTH;
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
		public boolean getSAVADATA_COMPLETED () { return SAVEDATA_COMPLETED; }
		public void setSAVEDATA_COMPLETED (boolean completed) { SAVEDATA_COMPLETED = completed; }
		public double getSAVEDATA_HEALTH () { return SAVEDATA_HEALTH; }
		public void setSAVEDATA_HEALTH (double health) { SAVEDATA_HEALTH = health; }
		
		public LevelOption (Level level, String imagePath, String levelIntroText, int x0, int y0, boolean SAVEDATA_COMPLETED, double SAVEDATA_HEALTH) {
			this.level = level;
			this.imagePath = imagePath;
			this.levelIntroText = levelIntroText;
			this.x0 = x0;
			this.y0 = y0;
			this.SAVEDATA_COMPLETED = SAVEDATA_COMPLETED;
			this.SAVEDATA_HEALTH = SAVEDATA_HEALTH;
		}
	}
}