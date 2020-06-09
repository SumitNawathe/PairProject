import java.util.*;
import java.awt.*;

public class LevelBoss extends Level {
	private BossFrame bossFrame;
	private ArrayList<BossWeakness> bossWeaknessList = new ArrayList<BossWeakness>();
	private double totalHealth;
	private Explosion explosion;
	
	public void initializeGame(Game game) {		
		bossFrame = new BossFrame(game);
		game.getEnemyShips().add(bossFrame);
		game.getMeshList().add(bossFrame);
		
//		EnemyA enemy = new EnemyA(new Vector(game.getPlayerShip().getPos().getX()+100, 0, 0), 10, 0);
//		game.getEnemyShips().add(enemy);
//		game.getMeshList().add(enemy);
		
		for (int i = 0; i < 4; i++) {
			BossWeakness bossWeakness = new BossWeakness(bossFrame, i);
			game.getEnemyShips().add(bossWeakness);
			game.getMeshList().add(bossWeakness);
			bossWeaknessList.add(bossWeakness);
		}
		
//		EnemyA enemy = new EnemyA(new Vector(game.getPlayerShip().getPos().getX()+100, 0, 0), 10, 0);
//		game.getEnemyShips().add(enemy);
//		game.getMeshList().add(enemy);
	}

	public boolean update(Game game) {
		totalHealth = 0;
		for (BossWeakness bossWeakness : bossWeaknessList)
			totalHealth += bossWeakness.getHealth();
		
		if (totalHealth <= 0 && explosion == null) {
			explosion = new Explosion(bossFrame.getPos(), 40, 1);
			game.getMeshList().add(explosion);
		}
		
		if (explosion != null)
			if (!explosion.update(bossFrame.getPos())) {
				System.out.println("clear");
				bossFrame.getTris().clear();
			}
		
		return false;
	}
	
	public void draw (Game game, Graphics g) {
		double scaleX=(double)game.SCREEN_WIDTH/1200;
		double scaleY=(double)game.SCREEN_HEIGHT/900;
		
		g.setColor(new Color(255, 0,255));
		g.setFont(new Font ("TimesRoman", Font.BOLD, (int)(30*scaleX)));
		g.drawString("BOSS HEALTH", (int)(5*scaleX), (int)(30*scaleY));
		
		g.drawRect((int) (230*scaleX), (int) (5*scaleY), (int) (950*scaleX), (int) (30*scaleY));
		g.fillRect((int) (230*scaleX), (int) (5*scaleY), (int) (950*scaleX * totalHealth/(4.0*BossWeakness.MAX_HEALTH)), (int) (30*scaleY));
		
//		g.drawRect(780, 830, (int)(400*scaleX), (int)(30*scaleY));
//		g.fillRect((int) ((780+4*(100-playerShip.getHealth()))*scaleX), (int)(830*scaleY), (int) ((4*playerShip.getHealth())*scaleX), (int)(30*scaleY));
	}
}
