import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;

import javax.imageio.*;
import javax.swing.*;

public class LevelSelectScreen extends JPanel {
	private int SCREEN_WIDTH, SCREEN_HEIGHT;
	private double scaleX, scaleY;
	private String SAVEDATA_LOCATION;
	private GameFrame gameFrame;
	private ArrayList<LevelOption> levelOptionList;
	private ArrayList<String> introTexts;
	private Image currentImage;
	private String currentLevelIntroText;
	private Level currentLevel;
	private double currentScore;
	private JButton startButton, backToIntroScreen;
	LevelSelectScreen levelSelectScreen;
	private String saveFileLocation;
	private int currentDifficulty;
	private int abilityState;
	private boolean canChangeAbilities;
	private JButton useCannonShot, useMultiShot;

	public LevelSelectScreen (GameFrame gameFrame, int SCREEN_WIDTH, int SCREEN_HEIGHT, String SAVEDATA_LOCATION) {
		levelSelectScreen = this;
		this.SCREEN_WIDTH = SCREEN_WIDTH;
		this.SCREEN_HEIGHT = SCREEN_HEIGHT;
		scaleX=SCREEN_WIDTH/1200.0;
		scaleY=SCREEN_HEIGHT/900.0;
		this.SAVEDATA_LOCATION = SAVEDATA_LOCATION;
		try {
			currentDifficulty=Integer.parseInt(new String(new byte[] {Files.readAllBytes(new File(SAVEDATA_LOCATION).toPath())[11]}));
		} catch (Exception e) {System.out.println("Failed to set difficulty.");}
		this.setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setLayout(null);

		levelOptionList = new ArrayList<LevelOption>();
		try {
			BufferedReader file = new BufferedReader(new FileReader(SAVEDATA_LOCATION));
			file.readLine();
			StringTokenizer st = new StringTokenizer(file.readLine());
			levelOptionList.add(new LevelOption(new AgilityLevel1(), "Textures/MarsImage1.jpg", "Welcome, recruit! To serve in the legendary Arwing squadron you must first pass this training course in the orbit of Mars. "
					+ "Your supervisor will provide you with instructions. Good luck!", SCREEN_WIDTH/10, SCREEN_HEIGHT/2, Boolean.parseBoolean(st.nextToken()), Double.parseDouble(st.nextToken())));

			st = new StringTokenizer(file.readLine());
			levelOptionList.add(new LevelOption(new AgilityLevel2(), "Textures/nebulaimage1.jpg", "Agility 2.", 1*SCREEN_WIDTH/7, 3*SCREEN_HEIGHT/5, Boolean.parseBoolean(st.nextToken()), Double.parseDouble(st.nextToken())));
			
			st = new StringTokenizer(file.readLine());
			levelOptionList.add(new LevelOption(new EnemyLevel1(), "Textures/SaturnImage1.jpg", "Enemy 1.", 3*SCREEN_WIDTH/7, 4*SCREEN_HEIGHT/5, Boolean.parseBoolean(st.nextToken()), Double.parseDouble(st.nextToken())));

			st = new StringTokenizer(file.readLine());
			levelOptionList.add(new LevelOption(new AgilityLevel3(), "Textures/strangeplanet2.jpg", "Agility 3.", 6*SCREEN_WIDTH/11, 3*SCREEN_HEIGHT/5, Boolean.parseBoolean(st.nextToken()), Double.parseDouble(st.nextToken())));
			
			st = new StringTokenizer(file.readLine());
			levelOptionList.add(new LevelOption(new EnemyLevel2(), "Textures/strangeplanet.jpg", "Enemy 2.", 7*SCREEN_WIDTH/11, 1*SCREEN_HEIGHT/5, Boolean.parseBoolean(st.nextToken()), Double.parseDouble(st.nextToken())));
			
			st = new StringTokenizer(file.readLine());
			levelOptionList.add(new LevelOption(new EnemyLevel3(), "Textures/strangeplanet4.jpg", "Enemy 3.", 5*SCREEN_WIDTH/11, 2*SCREEN_HEIGHT/5, Boolean.parseBoolean(st.nextToken()), Double.parseDouble(st.nextToken())));
			
			st = new StringTokenizer(file.readLine());
			levelOptionList.add(new LevelOption(new AgilityLevel4(), "Textures/strangeplanet3.jpg", "Agiliy 4.", SCREEN_WIDTH/2, 1*SCREEN_HEIGHT/6, Boolean.parseBoolean(st.nextToken()), Double.parseDouble(st.nextToken())));
			
			st = new StringTokenizer(file.readLine());
			levelOptionList.add(new LevelOption(new EnemyLevel4(), "Textures/whitedwarf.jpg", "Enemy 4.", 3*SCREEN_WIDTH/11, SCREEN_HEIGHT/6, Boolean.parseBoolean(st.nextToken()), Double.parseDouble(st.nextToken())));
			
			st = new StringTokenizer(file.readLine());
			levelOptionList.add(new LevelOption(new EnemyLevel4(), "Textures/strangeplanet5.jpg", "Enemy 5.", 2*SCREEN_WIDTH/11, 2*SCREEN_HEIGHT/5, Boolean.parseBoolean(st.nextToken()), Double.parseDouble(st.nextToken())));
			
			st = new StringTokenizer(file.readLine());
			levelOptionList.add(new LevelOption(new LevelBoss(), "Textures/BlackHolePhoto1.jpg", "The FitnessGram™ Pacer Test is a multistage aerobic capacity test that progressively gets more difficult as it continues. The 20 meter pacer test will begin in 30 seconds. Line up at the start. The running speed starts slowly, but gets faster each minute after you hear this signal. [beep] A single lap should be completed each time you hear this sound. [ding] Remember to run in a straight line, and run as long as possible. The second time you fail to complete a lap before the sound, your test is over. The test will begin on the word start. On your mark, get ready, start.", 4*SCREEN_WIDTH/11, SCREEN_HEIGHT/2, Boolean.parseBoolean(st.nextToken()),  Double.parseDouble(st.nextToken())));		file.close();
		
			System.out.println(levelOptionList.size());
		} catch (Exception e) { System.out.println(e); }
		this.addMouseListener(new MouseListener () {
			public void mousePressed (MouseEvent event) {
				for (int i = 0; i < levelOptionList.size(); i++) {
					LevelOption levelOption = levelOptionList.get(i);
					if (levelOption.clickedOnLevel(event.getX(), event.getY()) && ((i == 0) || (i != 0 && levelOptionList.get(i-1).SAVEDATA_COMPLETED))) {
						currentImage = levelOption.getImage();
						currentLevelIntroText = levelOption.getLevelIntroText();
						currentLevel = levelOption.getLevel();
						currentScore = levelOption.getSAVEDATA_HEALTH();
						introTexts=BreakString.breakText(currentLevelIntroText);
						levelSelectScreen.repaint();
						break;
					}
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
				System.out.println(currentLevelIntroText);
				if (currentLevel.getLEVEL_NUM()==0)
					gameFrame.goToInstructionScreen(currentDifficulty, abilityState, levelOptionList.get(1).SAVEDATA_COMPLETED);
				else if (currentLevel.getLEVEL_NUM()==1)
					gameFrame.goToEnemyInstructionScreen(currentDifficulty, abilityState, levelOptionList.get(1).SAVEDATA_COMPLETED);
				else	
					gameFrame.startLevel(currentLevel, currentDifficulty, abilityState, levelOptionList.get(1).SAVEDATA_COMPLETED);
			}
		});
		startButton.setSize(new Dimension(4*SCREEN_WIDTH/21, 2*SCREEN_HEIGHT/21));
		startButton.setLocation(16*SCREEN_WIDTH/21, 17*SCREEN_HEIGHT/21);
		this.add(startButton);

		backToIntroScreen = new JButton("BACK");
		backToIntroScreen.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent event) {
				gameFrame.goToIntroScreen();
			}
		});
		backToIntroScreen.setSize(new Dimension(2*SCREEN_WIDTH/21, 1*SCREEN_HEIGHT/21));
		backToIntroScreen.setLocation(0, 0);
		this.add(backToIntroScreen);

		if (levelOptionList.size() > 0) {
			currentImage = levelOptionList.get(0).getImage();
			currentLevelIntroText = levelOptionList.get(0).getLevelIntroText();
			currentLevel = levelOptionList.get(0).getLevel();
			currentScore = levelOptionList.get(0).getSAVEDATA_HEALTH();
		}
		introTexts=BreakString.breakText(currentLevelIntroText);
		
		if (levelOptionList.get(2).SAVEDATA_COMPLETED)
			abilityState = 1;
		if (levelOptionList.get(3).SAVEDATA_COMPLETED) {
			abilityState = 2;
			addAbilityButtons();
		}
	}
	
	public void addAbilityButtons () {
		useCannonShot = new JButton("Use Cannon Shot");
		useCannonShot.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent event) {
				useCannonShot.setBackground(Color.green);
				useMultiShot.setBackground(Color.gray);
				abilityState = 1;
			}
		});
		useCannonShot.setBackground(Color.green);
		useCannonShot.setSize(new Dimension(3*SCREEN_WIDTH/21, 1*SCREEN_HEIGHT/21));
		useCannonShot.setLocation(12*SCREEN_WIDTH/21, 0);
		levelSelectScreen.add(useCannonShot);
		
		useMultiShot = new JButton("Use MultiShot");
		useMultiShot.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent event) {
				useMultiShot.setBackground(Color.green);
				useCannonShot.setBackground(Color.gray);
				abilityState = 2;
			}
		});
		useMultiShot.setBackground(Color.gray);
		useMultiShot.setSize(new Dimension(3*SCREEN_WIDTH/21, 1*SCREEN_HEIGHT/21));
		useMultiShot.setLocation(12*SCREEN_WIDTH/21, 1*SCREEN_HEIGHT/21);
		levelSelectScreen.add(useMultiShot);
	}
	
	public void updateSAVEDATA (int levelNum, boolean completed, double health) {
		try {
			Scanner scan=new Scanner(new File(SAVEDATA_LOCATION));
			String first=scan.nextLine();
			scan.close();
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(SAVEDATA_LOCATION)));
			out.write(first+"\n");
			for (int i = 0; i < levelOptionList.size(); i++) {
				if (i == levelNum) {
					if (levelOptionList.get(i).getSAVEDATA_COMPLETED() || completed) {
						if (completed && levelNum == 1 && !canChangeAbilities)
							abilityState = 1;
						else if (completed && levelNum == 3 && !canChangeAbilities) {
							abilityState = 2;
							canChangeAbilities = true;
							addAbilityButtons();
						}
						
						completed = true;
						health = Math.max(health, levelOptionList.get(i).getSAVEDATA_HEALTH());
						out.write(completed + " " + health + "\n");
						levelOptionList.get(i).setSAVEDATA_COMPLETED(completed);
						levelOptionList.get(i).setSAVEDATA_HEALTH(health);
					} else {
						health = 0.0;
						out.write(completed + " " + health + "\n");
						levelOptionList.get(i).setSAVEDATA_COMPLETED(completed);
						levelOptionList.get(i).setSAVEDATA_HEALTH(health);
					}
				} else {
					out.write(levelOptionList.get(i).getSAVEDATA_COMPLETED() + " " + levelOptionList.get(i).getSAVEDATA_HEALTH() + "\n");
				}
			}
			out.close();
		} catch (Exception e) {System.out.println("Updating save data failed. "+SAVEDATA_LOCATION);}
	}

	public void paintComponent (Graphics g1) {
		Graphics2D g=(Graphics2D) g1;
		try {
			g.drawImage(ImageIO.read(new File("Textures/GalaxyImage1.jpg")), 0, 0, 5*SCREEN_WIDTH/7, SCREEN_HEIGHT, null);
		} catch (Exception e) {}

		g.setColor(Color.LIGHT_GRAY);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(5*SCREEN_WIDTH/7, 0,  2*SCREEN_WIDTH/7, SCREEN_HEIGHT);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(5*SCREEN_WIDTH/7+4, 0+5,  2*SCREEN_WIDTH/7-14, SCREEN_HEIGHT-39);

		//		g.setColor(Color.RED);
		for (int i=0;i<levelOptionList.size();i++) {
			LevelOption levelOption=levelOptionList.get(i);
			if (levelOption.SAVEDATA_COMPLETED&&i!=levelOptionList.size()) {
				g.setColor(Color.BLACK);
				g.setStroke(new BasicStroke(6));
				if (i != levelOptionList.size()-1)
					g.drawLine(levelOption.getX0()+LevelOption.SQUARE_SIZE/2, levelOption.getY0()+LevelOption.SQUARE_SIZE/2, 
							levelOptionList.get(i+1).getX0()+LevelOption.SQUARE_SIZE/2, levelOptionList.get(i+1).getY0()+LevelOption.SQUARE_SIZE/2);
				g.setColor(Color.GREEN);
				g.fillRect(levelOption.getX0(), levelOption.getY0(), LevelOption.SQUARE_SIZE, LevelOption.SQUARE_SIZE);
			} else {
				g.setColor(Color.RED);
				g.fillRect(levelOption.getX0(), levelOption.getY0(), LevelOption.SQUARE_SIZE, LevelOption.SQUARE_SIZE);
				break;
			}
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
			//System.out.println(currentScore);
			g.drawString("High Score: "+(int)(10*currentScore),17*SCREEN_WIDTH/21-(int)(20*scaleX), 7*SCREEN_HEIGHT/21+(int)(12*34*scaleY));
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
		public boolean clickedOnLevel (int x, int y) {
			return (x0 < x && x < x0+SQUARE_SIZE) && (y0 < y && y < y0+SQUARE_SIZE);
		}
		public int getX0 () { return x0; }
		public int getY0 () { return y0; }
		public boolean getSAVEDATA_COMPLETED () { return SAVEDATA_COMPLETED; }
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