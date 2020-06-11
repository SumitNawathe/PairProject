import java.awt.Graphics;

public abstract class EnemyLevel extends Level {
	private int ringCounter;
	private int difficulty;

	public void initializeGame(GraphicsPanel graphicsPanel, int difficulty) {
		this.difficulty=difficulty;
	}

	public void spawnRings(GraphicsPanel graphicsPanel) {
		ringCounter++;
		if (ringCounter == 150+150*difficulty) {
			AgilityRing ring = new AgilityRing(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+300, (int)(15*Math.random()), (int)(15*Math.random())));
			graphicsPanel.getRingList().add(ring);
			graphicsPanel.getMeshList().add(ring);
			ringCounter = 0;
		}
	}

	public double determineScore (GraphicsPanel graphicsPanel) { return graphicsPanel.getPlayerShip().getHealth(); }

	public void draw(GraphicsPanel graphicsPanel, Graphics g) {}
}
