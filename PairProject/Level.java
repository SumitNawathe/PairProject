import java.awt.*;

public abstract class Level {
	private int progressState;
	private int LEVEL_NUM;
	public int getLEVEL_NUM () { return LEVEL_NUM; }
	public void setLEVEL_NUM (int num) { LEVEL_NUM = num; }
	public int getProgressState () { return progressState; }
	public void incrementProgressState () { progressState++; }
	public abstract void initializeGame (GraphicsPanel graphicsPanel);
	public abstract boolean update (GraphicsPanel graphicsPanel); // true if game has been won
	public abstract void draw (GraphicsPanel graphicsPanel, Graphics g);
	public abstract double determineScore (GraphicsPanel graphicsPanel);
}
