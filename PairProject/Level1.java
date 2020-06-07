public class Level1 extends Level {
	public void initializeGame(Game game) {
		game.getRingList().add(new AgilityRing(new Vector(5, 0, -5)));
		game.getRingList().add(new AgilityRing(new Vector(25, 0, -5)));
		game.getRingList().add(new AgilityRing(new Vector(45, 0, -5)));
		game.getRingList().add(new AgilityRing(new Vector(65, 0, -5)));
		game.getRingList().add(new AgilityRing(new Vector(85, 0, -5)));
		game.getRingList().add(new AgilityRing(new Vector(105, 0, -5)));
		game.getRingList().add(new AgilityRing(new Vector(125, 0, -5)));
		game.getRingList().add(new AgilityRing(new Vector(5, 0, 5)));
	}

	public boolean update(Game game) {
		if (getProgressState() == 0 && game.getPlayerShip().getPos().getX() > 50) {
			incrementProgressState();
			EnemyA enemy = new EnemyA(new Vector(game.getPlayerShip().getPos().getX()+100, 0, 0));
			game.getEnemyShips().add(enemy);
			game.getMeshList().add(enemy);
		}
		return false;
	}
}
