import java.awt.Graphics;

public class EnemyLevel2 extends Level {
	private int difficulty;
	
	public EnemyLevel2 () { super(); this.setLEVEL_NUM(3); }
	
	//TODO: Maybe have rings that show up periodically that give back a little health.
	public void initializeGame(GraphicsPanel graphicsPanel, int difficulty) {
		this.difficulty=difficulty;
	}

	public boolean update(GraphicsPanel graphicsPanel) {
		if (getProgressState()==0&&graphicsPanel.getPlayerShip().getPos().getX()>10) {
			incrementProgressState();
			EnemyC enemy = new EnemyC(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, -10, -10), 10, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
//			for (int i = 0; i < 3; i++) {
//				AgilityRing ring = new AgilityRing(new Vector())
//			}
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

	public double determineScore (GraphicsPanel graphicsPanel) { return graphicsPanel.getPlayerShip().getHealth(); }
	
	public void draw(GraphicsPanel graphicsPanel, Graphics g) {}
}
