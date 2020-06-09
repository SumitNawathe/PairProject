import java.awt.*;

public abstract class Level {
	private int progressState;
	public int getProgressState () { return progressState; }
	public void incrementProgressState () { progressState++; }
	public abstract void initializeGame (GraphicsPanel graphicsPanel);
	public abstract boolean update (GraphicsPanel graphicsPanel); // true if game has been won
	public abstract void draw (GraphicsPanel graphicsPanel, Graphics g);
}
