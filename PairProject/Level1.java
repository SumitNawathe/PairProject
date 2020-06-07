import java.util.*;

public class Level1 extends Level {
	public void initializeGame(Game game) {
//		game.getRingList().add(new AgilityRing(new Vector(5, 0, -5)));
//		game.getRingList().add(new AgilityRing(new Vector(25, 0, -5)));
//		game.getRingList().add(new AgilityRing(new Vector(45, 0, -5)));
//		game.getRingList().add(new AgilityRing(new Vector(65, 0, -5)));
//		game.getRingList().add(new AgilityRing(new Vector(85, 0, -5)));
//		game.getRingList().add(new AgilityRing(new Vector(105, 0, -5)));
//		game.getRingList().add(new AgilityRing(new Vector(125, 0, -5)));
//		game.getRingList().add(new AgilityRing(new Vector(5, 0, 5)));
//		try {
//			game.getMeshList().add(Mesh.loadFromObjFile("Models/moon2.obj", "Textures/Moon_Bump_2K.png").translate(new Vector(600, -300, -300)));
//			game.getMeshList().add(Mesh.loadFromObjFile("Models/mars1.obj", "Textures/Mars_Diffuse_2K.png").translate(new Vector(1500, 500, 500)));
//			game.getMeshList().add(Mesh.loadFromObjFile("Models/earth2.obj", "Textures/Earth_Diffuse_2K.png").translate(new Vector(2500, 600, -600)));
//		} catch (Exception e) {}
		
		ArrayList<AgilityRing> ringList = game.getRingList();
		
		ringList.add(new AgilityRing(new Vector(80, 0, 0)));
		ringList.add(new AgilityRing(new Vector(100, 0, 0)));
		ringList.add(new AgilityRing(new Vector(120, 0, 0)));
		ringList.add(new AgilityRing(new Vector(140, 0, 5)));
		ringList.add(new AgilityRing(new Vector(160, 0, 10)));
		ringList.add(new AgilityRing(new Vector(180, 0, 15)));
		ringList.add(new AgilityRing(new Vector(200, 0, 15)));
		ringList.add(new AgilityRing(new Vector(220, 5, 15)));
		ringList.add(new AgilityRing(new Vector(240, 10, 10)));
		ringList.add(new AgilityRing(new Vector(260, 15, 5)));
		ringList.add(new AgilityRing(new Vector(280, 10, 0)));
		ringList.add(new AgilityRing(new Vector(300, 5, 0)));
		ringList.add(new AgilityRing(new Vector(320, 0, -5)));
		ringList.add(new AgilityRing(new Vector(340, -5, -10)));
		ringList.add(new AgilityRing(new Vector(360, -5, -15)));
		ringList.add(new AgilityRing(new Vector(380, -5, -5)));
		ringList.add(new AgilityRing(new Vector(400, 0, 0)));
		
		try {
			game.getMeshList().add(Mesh.loadFromObjFile("Models/moon2.obj", "Textures/Moon_Bump_2K.png").translate(new Vector(600, -300, -300)));
		} catch (Exception e) {}
		
		ringList.add(new AgilityRing(new Vector(720, 0, 0)));
		ringList.add(new AgilityRing(new Vector(740, -5, 0)));
		ringList.add(new AgilityRing(new Vector(760, -10, -5)));
		ringList.add(new AgilityRing(new Vector(780, -15, -10)));
		ringList.add(new AgilityRing(new Vector(800, -15, -10)));
		ringList.add(new AgilityRing(new Vector(840, 5, 5)));
		
		ringList.add(new AgilityRing(new Vector(920, 0, 0)));
		ringList.add(new AgilityRing(new Vector(940, 5, 0)));
		ringList.add(new AgilityRing(new Vector(960, 10, 5)));
		ringList.add(new AgilityRing(new Vector(980, 15, 10)));
		ringList.add(new AgilityRing(new Vector(1000, 15, 10)));
		ringList.add(new AgilityRing(new Vector(1040, -5, -5)));
		
		try {
			game.getMeshList().add(Mesh.loadFromObjFile("Models/mars1.obj", "Textures/Mars_Diffuse_2K.png").translate(new Vector(1400, 500, 500)));
		} catch (Exception e) {}
		
		for (int theta1 = 0; theta1 < 24; theta1++)
			ringList.add(new AgilityRing(new Vector(1300+20*theta1, 10*Math.cos(theta1*Math.PI/6), 10*Math.sin(theta1*Math.PI/6))));
		
		try {
			game.getMeshList().add(Mesh.loadFromObjFile("Models/earth2.obj", "Textures/Earth_Diffuse_2K.png").translate(new Vector(2100, 600, -600)));
		} catch (Exception e) {}
		
		for (int i = 0; i < 5; i++)
			ringList.add(new AgilityRing(new Vector(1800+20*i, 0, 0)));
	}

	public boolean update(Game game) {
//		if (getProgressState() == 0 && game.getPlayerShip().getPos().getX() > 400) {
//			
//		}
		
		if ((getProgressState() == 0 && game.getPlayerShip().getPos().getX() > 400) || (getProgressState() == 1 && game.getPlayerShip().getPos().getX() > 1040)) {
			incrementProgressState();
			EnemyA enemy = new EnemyA(new Vector(game.getPlayerShip().getPos().getX()+100, 0, 0), 20, 1);
			game.getEnemyShips().add(enemy);
			game.getMeshList().add(enemy);
		}
		return false;
	}
}
