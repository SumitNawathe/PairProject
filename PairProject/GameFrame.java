import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame{
	private GraphicsPanel graphicsPanel;
	int SCREEN_WIDTH = 1220, SCREEN_HEIGHT = 900;
	
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
//		startLevel(new Level1());
		
		this.getContentPane().add(new IntroScreen(this, SCREEN_WIDTH, SCREEN_HEIGHT));
		this.pack();
		this.setVisible(true);
	}
	
	public void startLevel (Level level) {
		this.getContentPane().removeAll();
		graphicsPanel = new GraphicsPanel(this, level, SCREEN_WIDTH, SCREEN_HEIGHT);
		this.getContentPane().add(graphicsPanel);
		this.pack();
		this.setVisible(true);
	}
	
	public static void main (String[] args) {
		GameFrame gameFrame = new GameFrame();
	}
}
