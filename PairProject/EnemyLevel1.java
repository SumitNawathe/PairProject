import java.awt.Graphics;

public class EnemyLevel1 extends Level {
	private Mesh mars;
	private int difficulty;
	private int ringCounter;
	public void initializeGame(GraphicsPanel graphicsPanel, int difficulty) {
		try {
			mars = Mesh.loadFromObjFile("Models/Mars.obj", "Textures/Mars Map.png").translate(new Vector(2100, 600, -600));
		} catch (Exception e) {}
		graphicsPanel.getMeshList().add(mars);
		this.difficulty=difficulty;
	}

	public EnemyLevel1 () { setLEVEL_NUM(1); }
	
	public boolean update(GraphicsPanel graphicsPanel) {
		ringCounter++;
		if (ringCounter == 300) {
			AgilityRing ring = new AgilityRing(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+300, (int)(30*Math.random()), (int)(30*Math.random())));
			graphicsPanel.getRingList().add(ring);
			graphicsPanel.getMeshList().add(ring);
			ringCounter = 0;
		}
		
		if (getProgressState()==0&&graphicsPanel.getPlayerShip().getPos().getX()>10) {
			incrementProgressState();
			EnemyA enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, -10, -10), 10, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==1&&graphicsPanel.getPlayerShip().getPos().getX()>250) {
			incrementProgressState();
			EnemyA enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 10, 10), 10, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==2&&graphicsPanel.getPlayerShip().getPos().getX()>500) {
			incrementProgressState();
			EnemyA enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, -10, 10), 10, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==3&&graphicsPanel.getPlayerShip().getPos().getX()>750) {
			incrementProgressState();
			EnemyA enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 15), 20, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, -15), 20, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==4&&graphicsPanel.getPlayerShip().getPos().getX()>1000) {
			incrementProgressState();
			EnemyA enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 15, 0), 30, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, -10, -15), 30, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, -10, 15), 30, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		}
		return getProgressState()==5 && graphicsPanel.getEnemyShips().size()==0;
	}

	public double determineScore (GraphicsPanel graphicsPanel) {
		return graphicsPanel.getPlayerShip().getHealth();
	}
	
	public void draw(GraphicsPanel graphicsPanel, Graphics g) {}

}
