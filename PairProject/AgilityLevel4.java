import java.util.*;
import java.awt.*;

public class AgilityLevel4 extends Level {
	Mesh sun, mars, quasar;
	ArrayList<AgilityRing> set1, set2, set3;
	private int difficulty;
	
	public AgilityLevel4 () { super(); setLEVEL_NUM(6); }
	
	public void initializeGame(GraphicsPanel graphicsPanel, int difficulty) {
		System.out.println("diff: "+difficulty);
		this.difficulty=difficulty;
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
			sun = Mesh.loadFromObjFile("Models/Sun.obj", "Textures/Sun Map.png").translate(new Vector(1000, -500, -500));
			mars = Mesh.loadFromObjFile("Models/Mars.obj", "Textures/Mars Map.png").translate(new Vector(1600, 500, 500));
			quasar = Mesh.loadFromObjFile("Models/Quasar.obj", "Textures/Quasar Map.png").translate(new Vector(5400, 700, -700));
		} catch (Exception e) {}
		
//		ArrayList<AgilityRing> ringList = game.getRingList();
		set1 = new ArrayList<AgilityRing>();
		set2 = new ArrayList<AgilityRing>();
		set3 = new ArrayList<AgilityRing>();
		
		for (int i = 0; i < 10; i++)
			set1.add(new AgilityRing(new Vector(80+10*i, -4*(i-5), -4*(i-5))));
		for (int i = 0; i < 10; i++)
			set1.add(new AgilityRing(new Vector(180+10*i, -20+4*i, -20)));
		for (int i = 0; i < 10; i++)
			set1.add(new AgilityRing(new Vector(280+10*i, -4*(i-5), 4*(i-5))));
		for (int i = 0; i < 10; i++)
			set1.add(new AgilityRing(new Vector(380+10*i, -20+4*i, 20)));
		
//		try {
//			game.getMeshList().add(Mesh.loadFromObjFile("Models/moon2.obj", "Textures/Moon_Bump_2K.png").translate(new Vector(600, -300, -300)));
//		} catch (Exception e) {}
		
		for (int i = 0; i < 10; i++)
			set2.add(new AgilityRing(new Vector(700+10*i, -4*(i-5), -4*(i-5))));
		for (int i = 0; i < 10; i++)
			set2.add(new AgilityRing(new Vector(800+10*i, -20, -20+4*i)));
		for (int i = 0; i < 5; i++)
			set2.add(new AgilityRing(new Vector(900+20*i, -10+10*Math.random(), 10+10*Math.random())));
		for (int i = 0; i < 5; i++)
			set2.add(new AgilityRing(new Vector(1020+20*i, 10+10*Math.random(), 10+10*Math.random())));
		for (int i = 0; i < 5; i++)
			set2.add(new AgilityRing(new Vector(1140+20*i, 10+10*Math.random(), -10+10*Math.random())));
		for (int i = 0; i < 5; i++)
			set2.add(new AgilityRing(new Vector(1250+20*i, -10+10*Math.random(), -10+10*Math.random())));
		
//		try {
//			game.getMeshList().add(Mesh.loadFromObjFile("Models/mars1.obj", "Textures/Mars_Diffuse_2K.png").translate(new Vector(1400, 500, 500)));
//		} catch (Exception e) {}
		
		for (int theta1 = 0; theta1 < 24; theta1++)
			set3.add(new AgilityRing(new Vector(1600+30*theta1, 10*Math.cos(3*theta1*Math.PI/6), 10*Math.sin(2*theta1*Math.PI/6))));
		
//		try {
//			game.getMeshList().add(Mesh.loadFromObjFile("Models/earth2.obj", "Textures/Earth_Diffuse_2K.png").translate(new Vector(2100, 600, -600)));
//		} catch (Exception e) {}
		
		graphicsPanel.getMeshList().add(sun);
		graphicsPanel.getMeshList().add(mars);
		graphicsPanel.getMeshList().add(quasar);
		graphicsPanel.getRingList().addAll(set1);
	}

	public boolean update(GraphicsPanel graphicsPanel) {
		if (getProgressState() == 0 && graphicsPanel.getPlayerShip().getPos().getX() > 500) {
			graphicsPanel.getMeshList().removeAll(set1);
			graphicsPanel.getRingList().removeAll(set1);
			graphicsPanel.getMeshList().addAll(set2);
			graphicsPanel.getRingList().addAll(set2);
			
			incrementProgressState();
			EnemyC enemy = new EnemyC(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+100, 20*Math.random(), 20*Math.random()), 10, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState() == 1 && graphicsPanel.getPlayerShip().getPos().getX() > 1400) {
			graphicsPanel.getMeshList().removeAll(set2);
			graphicsPanel.getRingList().removeAll(set2);
			graphicsPanel.getMeshList().addAll(set3);
			graphicsPanel.getRingList().addAll(set3);			
			incrementProgressState();
			EnemyC enemy = new EnemyC(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+100, 20*Math.random(), 20*Math.random()), 20, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		}
		
//		if ((getProgressState() == 0 && game.getPlayerShip().getPos().getX() > 400) || (getProgressState() == 1 && game.getPlayerShip().getPos().getX() > 1040)) {
//			incrementProgressState();
//			EnemyA enemy = new EnemyA(new Vector(game.getPlayerShip().getPos().getX()+100, 0, 0));
//			game.getEnemyShips().add(enemy);
//			game.getMeshList().add(enemy);
//		}
		return graphicsPanel.getPlayerShip().getPos().getX()>2320;
	}
	
	public double determineScore (GraphicsPanel graphicsPanel) {
		return 100.0*(1.0 - graphicsPanel.getRingList().size()/104.0);
	}
	
	public void draw (GraphicsPanel graphicsPanel, Graphics g) {}
}
