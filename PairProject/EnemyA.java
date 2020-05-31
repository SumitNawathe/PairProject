public class EnemyA extends SpaceShip {
	private double theta;
	private double fireCounter;
	
	public EnemyA (Vector pos) {
		super();
		setPos(pos);
		try {
			setTris(Mesh.loadFromObjFile("Models/Ship Model 3.obj", "Textures/Ship Model 3 Map.png").getTris());
		} catch (Exception e) { System.out.println("Error loading smallbulletsphere2.obj"); }
		translate(pos);
		
		setCollisionRadius(2);
	}
	
	public void update (Game game) {
		this.translate(new Vector(game.getPlayerShip().getPos().getX()+10-getPos().getX(), 10*Math.cos(theta)-getPos().getY(), 10*Math.sin(theta)-getPos().getZ()));
		this.setPos(new Vector(game.getPlayerShip().getPos().getX()+10, 10*Math.cos(theta), 10*Math.sin(theta)));
		theta += Math.PI/80;
		fireCounter++;
		if (fireCounter >= 10) {
			game.fireBullet(getPos().plus(new Vector(-3, 0, 0)), new Vector(-1, 0, 0), 0.3);
			fireCounter = 0;
		}
	}
}
