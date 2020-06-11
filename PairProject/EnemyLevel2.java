import java.awt.Graphics;

public class EnemyLevel2 extends EnemyLevel {
	private int difficulty;
	public EnemyLevel2 () { this.setLEVEL_NUM(4); }
	
	public void initializeGame(GraphicsPanel graphicsPanel, int difficulty) {
		this.difficulty=difficulty;
	}

	public boolean update(GraphicsPanel graphicsPanel) {
		spawnRings(graphicsPanel);
		if (getProgressState()==0&&graphicsPanel.getPlayerShip().getPos().getX()>10) {
			incrementProgressState();
			EnemyC enemy = new EnemyC(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 15, -10), 10, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==1&&graphicsPanel.getPlayerShip().getPos().getX()>200) {
			incrementProgressState();
			Enemy enemy = new EnemyC(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, -15, -15), 10, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, -15, 15), 10, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==2&&graphicsPanel.getPlayerShip().getPos().getX()>400) {
			incrementProgressState();
			Enemy enemy = new EnemyC(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 0), 10, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==3&&graphicsPanel.getPlayerShip().getPos().getX()>600) {
			incrementProgressState();
			Enemy enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 15), 20, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyC(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, -15), 20, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 15, 0), 20, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyC(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, -15, 0), 20, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==4&&graphicsPanel.getPlayerShip().getPos().getX()>800) {
			incrementProgressState();
			Enemy enemy = new EnemyC(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 0), 30, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyC(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 0), 30, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyC(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 0), 30, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==5&&graphicsPanel.getPlayerShip().getPos().getX()>1000) {
			incrementProgressState();
			Enemy enemy = new EnemyC(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 0), 30, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyC(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 0), 30, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyC(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 0), 30, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		}
		return getProgressState()==6 && graphicsPanel.getEnemyShips().size()==0;
	}

}
