import java.awt.Graphics;
import java.util.Random;

public abstract class EnemyLevel extends Level {
	private int ringCounter;
	private int difficulty;

	public void initializeGame(GraphicsPanel graphicsPanel, int difficulty) {
		this.difficulty=difficulty;
	}

	public void spawnRings(GraphicsPanel graphicsPanel) {
		ringCounter++;
		if (ringCounter == 100+100*difficulty) {
			Random rand=new Random();
			int a=rand.nextInt(15+15)-15;
			int b=rand.nextInt(15+15)-15;
			AgilityRing ring = new AgilityRing(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+300, a, b));
			graphicsPanel.getRingList().add(ring);
			graphicsPanel.getMeshList().add(ring);
			ringCounter = 0;
		}
	}

	public double determineScore (GraphicsPanel graphicsPanel) { return graphicsPanel.getPlayerShip().getHealth(); }

	public void draw(GraphicsPanel graphicsPanel, Graphics g) {}
}
