import java.awt.Graphics;

public class EnemyLevel1 extends Level {
	Mesh mars;
	
	public void initializeGame(Game game) {
		try {
			mars = Mesh.loadFromObjFile("Models/mars1.obj", "Textures/Mars_Diffuse_2K.png").translate(new Vector(2100, 600, -600));
		} catch (Exception e) {}
	}

	public boolean update(Game game) {
		if (game.getPlayerShip().getPos().getX()==10) {
			EnemyA enemy = new EnemyA(new Vector(game.getPlayerShip().getPos().getX()+150, 0, 0), 10, 0);
			game.getEnemyShips().add(enemy);
			game.getMeshList().add(enemy);
		} else if (game.getPlayerShip().getPos().getX()==500) {
			EnemyA enemy1 = new EnemyA(new Vector(game.getPlayerShip().getPos().getX()+150, 0, 0), 20, 0);
			game.getEnemyShips().add(enemy1);
			game.getMeshList().add(enemy1);
		}
		return false;
	}

	public void draw(Game game, Graphics g) {}

}
