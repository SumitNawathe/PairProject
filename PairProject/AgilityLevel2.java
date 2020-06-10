import java.util.*;
import java.awt.*;

public class AgilityLevel2 extends Level {
	Mesh mercury, venus;
	ArrayList<AgilityRing> set1, set2, set3;
	
	public AgilityLevel2 () { setLEVEL_NUM(2); }
	
	public void initializeGame(GraphicsPanel graphicsPanel) {
		
		try {
			mercury = Mesh.loadFromObjFile("Models/Mercury.obj", "Textures/Mercury_Diffuse_1K.png").translate(new Vector(600, -300, -300));
			venus = Mesh.loadFromObjFile("Models/venus2.obj", "Textures/Venus_Atmosphere_2K.png").translate(new Vector(1400, 500, 500));
		} catch (Exception e) {}
		
//		ArrayList<AgilityRing> ringList = game.getRingList();
		set1 = new ArrayList<AgilityRing>();
		set2 = new ArrayList<AgilityRing>();
		set3 = new ArrayList<AgilityRing>();
		
		for (int i = 0; i < 20; i++) {
			set1.add(new AgilityRing(new Vector(80+20*i, 10*Math.sin(i*Math.PI/10+Math.PI/2), 10*Math.cos(2*i*Math.PI/10))));
		}
		
		for (int i = 0; i < 20; i++) {
			set2.add(new AgilityRing(new Vector(700+20*i, 15*Math.cos(i*Math.PI/10+Math.PI/2), 15*Math.sin(2*i*Math.PI/10))));
		}
		
		for (int i = 0; i < 20; i++) {
			set3.add(new AgilityRing(new Vector(1300+40*i, 20*Math.random(), 20*Math.random())));
		}
		
		graphicsPanel.getMeshList().add(mercury);
		graphicsPanel.getMeshList().add(venus);
		graphicsPanel.getRingList().addAll(set1);
	}

	public boolean update(GraphicsPanel graphicsPanel) {
		if (getProgressState() == 0 && graphicsPanel.getPlayerShip().getPos().getX() > 500) {
			graphicsPanel.getMeshList().removeAll(set1);
			graphicsPanel.getRingList().removeAll(set1);
			graphicsPanel.getMeshList().addAll(set2);
			graphicsPanel.getRingList().addAll(set2);
			
			incrementProgressState();
			EnemyA enemy = new EnemyA(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+100, 0, 0), 10, 0);
			graphicsPanel.getEnemyShips().add(enemy);
			graphicsPanel.getMeshList().add(enemy);
		} else if (getProgressState() == 1 && graphicsPanel.getPlayerShip().getPos().getX() > 1100) {
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
		return graphicsPanel.getPlayerShip().getPos().getX()>1600;
	}
	
	public double determineScore (GraphicsPanel graphicsPanel) {
		return 100.0*(1.0 - graphicsPanel.getRingList().size()/60.0);
	}
	
	public void draw (GraphicsPanel graphicsPanel, Graphics g) {}
}
