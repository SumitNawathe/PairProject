import java.util.*;
import java.awt.*;

public class Level1 extends Level {
	Mesh moon, mars, earth;
	ArrayList<AgilityRing> set1, set2, set3;
	
	public Level1 () { setLEVEL_NUM(0); }
	
	public void initializeGame(GraphicsPanel graphicsPanel) {
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
		
		try {
			moon = Mesh.loadFromObjFile("Models/Moon.obj", "Textures/Moon Map.png").translate(new Vector(600, -300, -300));
//			moon = Mesh.loadFromObjFile("Models/Mercury.obj", "Textures/Mercury_Diffuse_1K.png").translate(new Vector(600, -300, -300));
//			moon = Mesh.loadFromObjFile("Models/venus2.obj", "Textures/Venus_Atmosphere_2K.png").translate(new Vector(600, -300, -300));
			mars = Mesh.loadFromObjFile("Models/Mars.obj", "Textures/Mars Map.png").translate(new Vector(1400, 500, 500));
			earth = Mesh.loadFromObjFile("Models/Earth.obj", "Textures/Earth Map.png").translate(new Vector(2100, 600, -600));
		} catch (Exception e) {}
		
//		ArrayList<AgilityRing> ringList = game.getRingList();
		set1 = new ArrayList<AgilityRing>();
		set2 = new ArrayList<AgilityRing>();
		set3 = new ArrayList<AgilityRing>();
		
		set1.add(new AgilityRing(new Vector(80, 0, 0)));
		set1.add(new AgilityRing(new Vector(100, 0, 0)));
		set1.add(new AgilityRing(new Vector(120, 0, 0)));
		set1.add(new AgilityRing(new Vector(140, 0, 5)));
		set1.add(new AgilityRing(new Vector(160, 0, 10)));
		set1.add(new AgilityRing(new Vector(180, 0, 15)));
		set1.add(new AgilityRing(new Vector(200, 0, 15)));
		set1.add(new AgilityRing(new Vector(220, 5, 15)));
		set1.add(new AgilityRing(new Vector(240, 10, 10)));
		set1.add(new AgilityRing(new Vector(260, 15, 5)));
		set1.add(new AgilityRing(new Vector(280, 10, 0)));
		set1.add(new AgilityRing(new Vector(300, 5, 0)));
		set1.add(new AgilityRing(new Vector(320, 0, -5)));
		set1.add(new AgilityRing(new Vector(340, -5, -10)));
		set1.add(new AgilityRing(new Vector(360, -5, -15)));
		set1.add(new AgilityRing(new Vector(380, -5, -5)));
		set1.add(new AgilityRing(new Vector(400, 0, 0)));
		
//		try {
//			game.getMeshList().add(Mesh.loadFromObjFile("Models/moon2.obj", "Textures/Moon_Bump_2K.png").translate(new Vector(600, -300, -300)));
//		} catch (Exception e) {}
		
		set2.add(new AgilityRing(new Vector(720, 0, 0)));
		set2.add(new AgilityRing(new Vector(740, -5, 0)));
		set2.add(new AgilityRing(new Vector(760, -10, -5)));
		set2.add(new AgilityRing(new Vector(780, -15, -10)));
		set2.add(new AgilityRing(new Vector(800, -15, -10)));
		set2.add(new AgilityRing(new Vector(840, 5, 5)));
		
		set2.add(new AgilityRing(new Vector(920, 0, 0)));
		set2.add(new AgilityRing(new Vector(940, 5, 0)));
		set2.add(new AgilityRing(new Vector(960, 10, 5)));
		set2.add(new AgilityRing(new Vector(980, 15, 10)));
		set2.add(new AgilityRing(new Vector(1000, 15, 10)));
		set2.add(new AgilityRing(new Vector(1040, -5, -5)));
		
//		try {
//			game.getMeshList().add(Mesh.loadFromObjFile("Models/mars1.obj", "Textures/Mars_Diffuse_2K.png").translate(new Vector(1400, 500, 500)));
//		} catch (Exception e) {}
		
		for (int theta1 = 0; theta1 < 24; theta1++)
			set3.add(new AgilityRing(new Vector(1300+20*theta1, 10*Math.cos(theta1*Math.PI/6), 10*Math.sin(theta1*Math.PI/6))));
		
//		try {
//			game.getMeshList().add(Mesh.loadFromObjFile("Models/earth2.obj", "Textures/Earth_Diffuse_2K.png").translate(new Vector(2100, 600, -600)));
//		} catch (Exception e) {}
		
		for (int i = 0; i < 5; i++)
			set3.add(new AgilityRing(new Vector(1800+20*i, 0, 0)));
		
		graphicsPanel.getMeshList().add(moon);
		graphicsPanel.getMeshList().add(mars);
		graphicsPanel.getMeshList().add(earth);
		graphicsPanel.getRingList().addAll(set1);
	}

	public boolean update(GraphicsPanel graphicsPanel) {
		if (getProgressState() == 0 && graphicsPanel.getPlayerShip().getPos().getX() > 400) {
			graphicsPanel.getMeshList().removeAll(set1);
			graphicsPanel.getRingList().removeAll(set1);
			graphicsPanel.getMeshList().addAll(set2);
			graphicsPanel.getRingList().addAll(set2);
			
			incrementProgressState();
			EnemyA enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+100, 0, 0), 10, 0);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState() == 1 && graphicsPanel.getPlayerShip().getPos().getX() > 1040) {
			graphicsPanel.getMeshList().removeAll(set2);
			graphicsPanel.getRingList().removeAll(set2);
			graphicsPanel.getMeshList().addAll(set3);
			graphicsPanel.getRingList().addAll(set3);			
			incrementProgressState();
			EnemyA enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+100, 0, 0), 20, 1);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		}
		
//		if ((getProgressState() == 0 && game.getPlayerShip().getPos().getX() > 400) || (getProgressState() == 1 && game.getPlayerShip().getPos().getX() > 1040)) {
//			incrementProgressState();
//			EnemyA enemy = new EnemyA(new Vector(game.getPlayerShip().getPos().getX()+100, 0, 0));
//			game.getEnemyShips().add(enemy);
//			game.getMeshList().add(enemy);
//		}
		return graphicsPanel.getPlayerShip().getPos().getX()>2000;
	}
	
	public double determineScore (GraphicsPanel graphicsPanel) {
		return 100.0*(1.0 - graphicsPanel.getRingList().size()/59.0);
	}
	
	public void draw (GraphicsPanel graphicsPanel, Graphics g) {}
}
