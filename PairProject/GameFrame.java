import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame{
	private IntroScreen introScreen;
	private SavePurgatory purgatory;
	private LevelSelectScreen levelSelectScreen;
	private InstructionScreen instructionScreen;
	private GraphicsPanel graphicsPanel;
	int SCREEN_WIDTH = 1220, SCREEN_HEIGHT = 900;
	String CURRENT_SAVEDATA_LOCATION;
	
	public GameFrame () {
		int width = SCREEN_WIDTH;
		//TODO: Delete: Credit to https://stackoverflow.com/questions/44490655/how-to-maintain-the-aspect-ratio-of-a-jframe for this.
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		if (width > gd.getDisplayMode().getWidth())
		    width = gd.getDisplayMode().getWidth();
		while (width*3/4 > gd.getDisplayMode().getHeight())
		    width = (int) (width - width*0.1);
		width-=10;
		SCREEN_WIDTH=width;
		SCREEN_HEIGHT=width*3/4;
		
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setFocusable(true);
		this.setBounds(this.getBounds().x, this.getBounds().y, width, width*3/4);
		this.setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		
//		graphicsPanel = new GraphicsPanel(this, new Level1(), SCREEN_WIDTH, SCREEN_HEIGHT);
//		this.getContentPane().add(graphicsPanel);
//		this.pack();
//		this.setVisible(true);
//		startLevel(new LevelBoss());
		
		introScreen = new IntroScreen(this, SCREEN_WIDTH, SCREEN_HEIGHT);
//		levelSelectScreen = new LevelSelectScreen(this, SCREEN_WIDTH, SCREEN_HEIGHT, "SaveFiles/SaveFile2.txt");
		
		goToIntroScreen();
	}
	
	public void updateSAVEDATA (int levelNum, boolean completed, double health) {
		levelSelectScreen.updateSAVEDATA(levelNum, completed, health);
	}
	
	public void goToIntroScreen () {
		this.getContentPane().removeAll();
		introScreen = new IntroScreen(this, SCREEN_WIDTH, SCREEN_HEIGHT);
		this.getContentPane().add(introScreen);
		this.pack();
		this.revalidate();
		this.repaint();
		this.setVisible(true);
	}
	
	public void goToPurgatory (int save) {
		this.getContentPane().removeAll();
		this.getContentPane().add(new SavePurgatory(this, SCREEN_WIDTH, SCREEN_HEIGHT, save));
		this.pack();
		this.revalidate();
		this.repaint();
		this.setVisible(true);
	}
	
	public void goToDifficultyScreen(int save) {
		this.getContentPane().removeAll();
		this.getContentPane().add(new DifficultyScreen(this, SCREEN_WIDTH, SCREEN_HEIGHT, save));
		this.pack();
		this.revalidate();
		this.repaint();
		this.setVisible(true);
	}
	
	public void goToLevelSelectScreen (String SAVEDATA_LOCATION) {
		this.getContentPane().removeAll();
		levelSelectScreen = new LevelSelectScreen(this, SCREEN_WIDTH, SCREEN_HEIGHT, SAVEDATA_LOCATION);
		CURRENT_SAVEDATA_LOCATION = SAVEDATA_LOCATION;
		this.getContentPane().add(levelSelectScreen);
		levelSelectScreen.repaint();
		this.pack();
		this.revalidate();
		this.repaint();
        this.setVisible(true);
	}
	
	public void goToInstructionScreen (int difficulty, int abilityState) {
		this.getContentPane().removeAll();
		instructionScreen = new InstructionScreen(this, SCREEN_WIDTH, SCREEN_HEIGHT, difficulty, abilityState);
		this.getContentPane().add(instructionScreen);
		this.pack();
		this.revalidate();
		this.repaint();
        this.setVisible(true);
	}
	
	public void startLevel (Level level, int difficulty, int abilityState) {
		this.getContentPane().removeAll();
		graphicsPanel = new GraphicsPanel(this, level, SCREEN_WIDTH, SCREEN_HEIGHT, difficulty, abilityState);
		this.getContentPane().add(graphicsPanel);
		this.pack();
		this.revalidate();
		this.repaint();
		this.setVisible(true);
	}
	
	public static void main (String[] args) {
		GameFrame gameFrame = new GameFrame();
	}
}
