import java.awt.Graphics;

public class EnemyLevel1 extends Level {
	Mesh mars;
	
	public void initializeGame(GraphicsPanel graphicsPanel) {
		try {
			mars = Mesh.loadFromObjFile("Models/mars1.obj", "Textures/Mars_Diffuse_2K.png").translate(new Vector(2100, 600, -600));
		} catch (Exception e) {}
	}

	public boolean update(GraphicsPanel graphicsPanel) {
		if (getProgressState()==0&&graphicsPanel.getPlayerShip().getPos().getX()>10) {
			incrementProgressState();
			EnemyA enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, -10, -10), 10, 0);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==1&&graphicsPanel.getPlayerShip().getPos().getX()>250) {
			incrementProgressState();
			EnemyA enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 10, 10), 20, 0);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==2&&graphicsPanel.getPlayerShip().getPos().getX()>500) {
			incrementProgressState();
			EnemyA enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, -10, 10), 20, 0);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState()==3&&graphicsPanel.getPlayerShip().getPos().getX()>750) {
			incrementProgressState();
			EnemyA enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, 15), 30, 0);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
			enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+150, 0, -15), 30, 0);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		}
		return false;
	}

	public void draw(GraphicsPanel graphicsPanel, Graphics g) {}

}
