import java.util.*;
import java.awt.*;

public class AgilityLevel3 extends Level {
	Mesh firevenus, moon, sun;
	ArrayList<AgilityRing> set1, set2, set3;
	private int difficulty;
	
	public AgilityLevel3 () { super(); setLEVEL_NUM(4); }
	
	public void initializeGame(GraphicsPanel graphicsPanel, int difficulty) {
		this.difficulty=difficulty;
		try {
			firevenus = Mesh.loadFromObjFile("Models/firevenus2.obj", "Textures/firevenustexture.jpg").translate(new Vector(800, -400, -400));
			moon = Mesh.loadFromObjFile("Models/Moon.obj", "Textures/Moon Map.png").translate(new Vector(1600, 300, -300));
			sun = Mesh.loadFromObjFile("Models/Sun.obj", "Textures/Sun Map.png").translate(new Vector(2900, 500, 500));
		} catch (Exception e) {}
		
//		ArrayList<AgilityRing> ringList = game.getRingList();
		set1 = new ArrayList<AgilityRing>();
		set2 = new ArrayList<AgilityRing>();
		set3 = new ArrayList<AgilityRing>();
		
		for (int i = 0; i < 20; i++) {
			set1.add(new AgilityRing(new Vector(80+50*i, 15-3*i/2.0, 20*Math.sin(i*Math.PI/2))));
		}
		
		for (int i = 0; i < 20; i++) {
			set2.add(new AgilityRing(new Vector(1300+50*i, 15*Math.sin(i*Math.PI/2), 20-2*i)));
		}
		
		for (int i = 0; i < 20; i++) {
			set3.add(new AgilityRing(new Vector(2500+30*i, 20*Math.random()*Math.sin(i*Math.PI/6), 20*Math.random()*Math.cos(i*Math.PI/6))));
		}
		
		graphicsPanel.getMeshList().add(firevenus);
		graphicsPanel.getMeshList().add(moon);
		graphicsPanel.getMeshList().add(sun);
		
		graphicsPanel.getRingList().addAll(set1);
	}

	public boolean update(GraphicsPanel graphicsPanel) {
		if (getProgressState() == 0 && graphicsPanel.getPlayerShip().getPos().getX() > 1100) {
			graphicsPanel.getMeshList().removeAll(set1);
			graphicsPanel.getRingList().removeAll(set1);
			graphicsPanel.getMeshList().addAll(set2);
			graphicsPanel.getRingList().addAll(set2);
			
			incrementProgressState();
			EnemyB enemy = new EnemyB(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+100, 20*Math.random(), 20*Math.random()), 10, 0, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState() == 1 && graphicsPanel.getPlayerShip().getPos().getX() > 2320) {
			graphicsPanel.getMeshList().removeAll(set2);
			graphicsPanel.getRingList().removeAll(set2);
			graphicsPanel.getMeshList().addAll(set3);
			graphicsPanel.getRingList().addAll(set3);			
			incrementProgressState();
			EnemyB enemy = new EnemyB(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+100, 20*Math.random(), 20*Math.random()), 20, 1, difficulty);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		}
		
//		if ((getProgressState() == 0 && game.getPlayerShip().getPos().getX() > 400) || (getProgressState() == 1 && game.getPlayerShip().getPos().getX() > 1040)) {
//			incrementProgressState();
//			EnemyA enemy = new EnemyA(new Vector(game.getPlayerShip().getPos().getX()+100, 0, 0));
//			game.getEnemyShips().add(enemy);
//			game.getMeshList().add(enemy);
//		}
		return graphicsPanel.getPlayerShip().getPos().getX()>3120;
	}
	
	public double determineScore (GraphicsPanel graphicsPanel) {
		return 100.0*(1.0 - graphicsPanel.getRingList().size()/60.0);
	}
	
	public void draw (GraphicsPanel graphicsPanel, Graphics g) {}
}
