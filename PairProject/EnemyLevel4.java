public class EnemyLevel4 extends EnemyLevel {
	private int difficulty;
	public EnemyLevel4 () { this.setLEVEL_NUM(7); }

	public boolean update(GraphicsPanel graphicsPanel) {
		spawnRings(graphicsPanel);
		if (getProgressState()==0&&graphicsPanel.getPlayerShip().getPos().getX()>10) {
			incrementProgressState();
			Enemy enemy = new EnemyB(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 0), 60, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==1&&graphicsPanel.getPlayerShip().getPos().getX()>200) {
			incrementProgressState();
			Enemy enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, -15, -15), 20, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, -15, 15), 20, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==2&&graphicsPanel.getPlayerShip().getPos().getX()>600) {
			incrementProgressState();
			Enemy enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 0), 20, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyB(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, -10, -10), 60, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==3&&graphicsPanel.getPlayerShip().getPos().getX()>1000) {
			incrementProgressState();
			Enemy enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 15), 30, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, -15), 10, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyB(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, -15, 0), 60, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==4&&graphicsPanel.getPlayerShip().getPos().getX()>1400) {
			incrementProgressState();
			Enemy enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 10, 0), 30, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 20, 20), 30, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyB(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, -20), 60, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyB(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 20, 0), 60, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==5&&graphicsPanel.getPlayerShip().getPos().getX()>1800) {
			incrementProgressState();
			Enemy enemy = new EnemyB(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 0), 70, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyB(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 0), 70, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyB(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 0), 70, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		}
		return getProgressState()==6 && graphicsPanel.getEnemyShips().size()==0;
	}

}
