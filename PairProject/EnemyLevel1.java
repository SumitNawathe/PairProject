import java.awt.Graphics;

public class EnemyLevel1 extends EnemyLevel {
	private int difficulty;
	private double planetSpawnThreshhold = 2500;
	public void initializeGame(GraphicsPanel graphicsPanel, int difficulty) {
//		try {
//			mars = Mesh.loadFromObjFile("Models/Mars.obj", "Textures/Mars Map.png").translate(new Vector(2100, 600, -600));
//		} catch (Exception e) {}
//		graphicsPanel.getMeshList().add(mars);
		try {
			graphicsPanel.getMeshList().add(Mesh.loadFromObjFile("Models/Planet 1.obj", "Textures/Planet 1 Map.png").translate(new Vector(2500, -500, 500)));
		} catch (Exception e) {}
		this.difficulty=difficulty;
	}

	public EnemyLevel1 () { super(); setLEVEL_NUM(2); }
	
	public boolean update(GraphicsPanel graphicsPanel) {
		spawnRings(graphicsPanel);		
		if (getProgressState()==0&&graphicsPanel.getEnemyShips().size()==0&&graphicsPanel.getPlayerShip().getPos().getX()>10) {
			incrementProgressState();
			EnemyA enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, -10, -10), 10, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==1&&graphicsPanel.getEnemyShips().size()==0) {
			incrementProgressState();
			EnemyA enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 10, 10), 10, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==2&&graphicsPanel.getEnemyShips().size()==0) {
			incrementProgressState();
			EnemyA enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, -10, 10), 10, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==3&&graphicsPanel.getEnemyShips().size()==0) {
			incrementProgressState();
			EnemyA enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 15), 20, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, -15), 20, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==4&&graphicsPanel.getEnemyShips().size()==0) {
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
		
		if (graphicsPanel.getPlayerShip().getPos().getX() > planetSpawnThreshhold) {
			System.out.println("adding planet");
			System.out.println("Models/Planet " + ((int) (5*Math.random())+ 1) + ".obj");
			try {
				double theta = 2.0 * Math.PI *Math.random();
				graphicsPanel.getMeshList().add(Mesh.loadFromObjFile("Models/Planet " + ((int) (5*Math.random())+ 1) + ".obj", "Textures/Planet " + ((int) (5*Math.random())+ 1) + " Map.png").translate(new Vector(planetSpawnThreshhold+2500, 600*Math.cos(theta), 600*Math.sin(theta))));
			} catch (Exception e) { System.out.println("exception loading planet"); }
			planetSpawnThreshhold += 2500;
		}
		
		return getProgressState()==5 && graphicsPanel.getEnemyShips().size()==0;
	}

}
