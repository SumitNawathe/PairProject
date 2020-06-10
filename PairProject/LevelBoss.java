import java.util.*;
import java.awt.*;

public class LevelBoss extends Level {
	private BossFrame bossFrame;
	private ArrayList<BossWeakness> bossWeaknessList = new ArrayList<BossWeakness>();
	private double totalHealth;
	private Explosion explosion;
	private int ringCounter = -100;
	
	public LevelBoss () { setLEVEL_NUM(4); }
	
	public void initializeGame(GraphicsPanel graphicsPanel) {		
		bossFrame = new BossFrame(graphicsPanel);
		graphicsPanel.getEnemyShips().add(bossFrame);
		graphicsPanel.getMeshList().add(bossFrame);
		
//		EnemyA enemy = new EnemyA(new Vector(game.getPlayerShip().getPos().getX()+100, 0, 0), 10, 0);
//		game.getEnemyShips().add(enemy);
//		game.getMeshList().add(enemy);
		
		for (int i = 0; i < 4; i++) {
			BossWeakness bossWeakness = new BossWeakness(bossFrame, i);
			graphicsPanel.getEnemyShips().add(bossWeakness);
			graphicsPanel.getMeshList().add(bossWeakness);
			bossWeaknessList.add(bossWeakness);
		}
		
//		EnemyA enemy = new EnemyA(new Vector(game.getPlayerShip().getPos().getX()+100, 0, 0), 10, 0);
//		game.getEnemyShips().add(enemy);
//		game.getMeshList().add(enemy);
	}

	public boolean update(GraphicsPanel graphicsPanel) {
		totalHealth = 0;
		for (BossWeakness bossWeakness : bossWeaknessList)
			totalHealth += bossWeakness.getHealth();
		if (totalHealth <= 0 && explosion == null) {
			explosion = new Explosion(bossFrame.getPos(), 40, 1);
			graphicsPanel.getMeshList().add(explosion);
		}
		
		if (explosion != null)
			if (!explosion.update(bossFrame.getPos())) {
				System.out.println("clear");
				bossFrame.getTris().clear();
				return true;
			}
		
		ringCounter++;
		if (ringCounter == 300) {
			AgilityRing ring = new AgilityRing(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+300, 0, 0));
			graphicsPanel.getRingList().add(ring);
			graphicsPanel.getMeshList().add(ring);
			ringCounter = 0;
		}
		
		return false;
	}
	
	public double determineScore (GraphicsPanel graphicsPanel) {
		return graphicsPanel.getPlayerShip().getHealth();
	}
	
	public void draw (GraphicsPanel graphicsPanel, Graphics g) {
		double scaleX=(double)graphicsPanel.SCREEN_WIDTH/1200;
		double scaleY=(double)graphicsPanel.SCREEN_HEIGHT/900;
		
		g.setColor(new Color(255, 0,255));
		g.setFont(new Font ("TimesRoman", Font.BOLD, (int)(30*scaleX)));
		g.drawString("BOSS HEALTH", (int)(5*scaleX), (int)(30*scaleY));
		
		g.drawRect((int) (230*scaleX), (int) (5*scaleY), (int) (950*scaleX), (int) (30*scaleY));
		g.fillRect((int) (230*scaleX), (int) (5*scaleY), (int) (950*scaleX * totalHealth/(4.0*BossWeakness.MAX_HEALTH)), (int) (30*scaleY));
		
//		g.drawRect(780, 830, (int)(400*scaleX), (int)(30*scaleY));
//		g.fillRect((int) ((780+4*(100-playerShip.getHealth()))*scaleX), (int)(830*scaleY), (int) ((4*playerShip.getHealth())*scaleX), (int)(30*scaleY));
	}
}
