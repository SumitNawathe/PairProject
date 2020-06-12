import java.awt.Graphics;

public class EnemyLevel3 extends EnemyLevel {
	private int difficulty;
	private double planetSpawnThreshhold = 2500;
	public EnemyLevel3 () { this.setLEVEL_NUM(5); }

	public void initializeGame(GraphicsPanel graphicsPanel, int difficulty) {
		try {
			graphicsPanel.getMeshList().add(Mesh.loadFromObjFile("Models/Planet 3.obj", "Textures/Planet 3 Map.png").translate(new Vector(2500, 500, -500)));
		} catch (Exception e) {}
		this.difficulty=difficulty;
	}
	
	public boolean update(GraphicsPanel graphicsPanel) {
		spawnRings(graphicsPanel);
		if (getProgressState()==0&&graphicsPanel.getPlayerShip().getPos().getX()>10) {
			incrementProgressState();
			Enemy enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 0), 50, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 0), 50, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 0), 50, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==1&&graphicsPanel.getPlayerShip().getPos().getX()>200) {
			incrementProgressState();
			Enemy enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, -15, -15), 10, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, -15, 15), 10, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==2&&graphicsPanel.getPlayerShip().getPos().getX()>500) {
			incrementProgressState();
			Enemy enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 0), 10, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==3&&graphicsPanel.getPlayerShip().getPos().getX()>800) {
			incrementProgressState();
			Enemy enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 15), 20, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, -15), 20, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 15, 0), 20, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, -15, 0), 20, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==4&&graphicsPanel.getPlayerShip().getPos().getX()>1100) {
			incrementProgressState();
			Enemy enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 10, 0), 30, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, -10), 30, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 0), 30, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==5&&graphicsPanel.getPlayerShip().getPos().getX()>1400) {
			incrementProgressState();
			Enemy enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 0), 50, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 0), 50, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 0), 50, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		}
		
		if (graphicsPanel.getPlayerShip().getPos().getX() > planetSpawnThreshhold) {
			System.out.println("adding planet");
			System.out.println("Models/Planet " + ((int) (5*Math.random())+ 1) + ".obj");
			try {
				double theta = 2.0 * Math.PI *Math.random();
				graphicsPanel.getMeshList().add(Mesh.loadFromObjFile("Models/Planet " + ((int) (5*Math.random())+ 1) + ".obj", "Textures/Planet " + ((int) (5*Math.random())+ 1) + " Map.png").translate(new Vector(planetSpawnThreshhold+2500, 600*Math.cos(theta), 600*Math.sin(theta))));
			} catch (Exception e) { System.out.println("exception loading planet"); }
			planetSpawnThreshhold += 2500;
		}
		
		return getProgressState()==6 && graphicsPanel.getEnemyShips().size()==0;
	}

}
