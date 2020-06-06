public class EnemyA extends SpaceShip {
	private double theta;
	private double fireCounter;
	private Explosion explosion;
	
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
		if (explosion == null) {
			if (fireCounter >= 10) {
				game.fireBullet(getPos().plus(new Vector(-3, 0, 0)), new Vector(-1, 0, 0), 0.3);
				fireCounter = 0;
			}
		} else {
			if (!explosion.update(getPos())) {
				System.out.println("clear");
				this.getTris().clear();
			}
		}
	}
	
	public void destroy (Game game) {
		System.out.println("destroy");
		if (explosion == null) {
			explosion = new Explosion(this.getPos(), 10, 10);
			game.getMeshList().add(explosion);
		}
	}
}
