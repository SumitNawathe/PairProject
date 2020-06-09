public class BossWeakness extends SpaceShip {
	public static final double MAX_HEALTH = 100;
	private double health = MAX_HEALTH;
	private Explosion explosion;
	private double[][] rotMat;
	private BossFrame bossFrame;
	private int posNum;
	private int hitTimer;
	
	public double getHealth () { return health; }
	
	public BossWeakness (BossFrame bossFrame, int posNum) {
		setPos(bossFrame.getPos());
		try {
			setTris(Mesh.loadFromObjFile("Models/BossWeaknessModel.obj", "Textures/BossWeakness.png").getTris());
		} catch (Exception e) { System.out.println("Error loading BossWeaknessModel.obj"); }
		
		for (Triangle tri : getTris())
			tri.setVerts(new Vector[] {tri.getVert1().scale(5), 
					tri.getVert2().scale(5), 
					tri.getVert3().scale(5)});
		
		translate(bossFrame.getPos());
		setCollisionRadius(5.0);
		rotMat = Matrix.getRotMatX(10*Math.random());
		rotMat = Matrix.mulMatMat(rotMat, Matrix.getRotMatZ(10*Math.random()));
		this.posNum = posNum;
		this.bossFrame = bossFrame;
	}
	
	public void update(Game game) {
		translate(getPos().clone().scale(-1));
		for (Triangle tri : getTris())
			tri.setVerts(new Vector[] {Matrix.multMatVec(rotMat, tri.getVert1()), 
					Matrix.multMatVec(rotMat, tri.getVert2()), 
					Matrix.multMatVec(rotMat, tri.getVert3())});
		translate(bossFrame.getPos().clone().plus(new Vector(0, 5*3.5*Math.cos(posNum*Math.PI/2+bossFrame.getTheta()), 5*3.5*Math.sin(posNum*Math.PI/2+bossFrame.getTheta()))));
		setPos(bossFrame.getPos().clone().plus(new Vector(0, 5*3.5*Math.cos(posNum*Math.PI/2+bossFrame.getTheta()), 5*3.5*Math.sin(posNum*Math.PI/2+bossFrame.getTheta()))));
		
		if (explosion != null)
			if (!explosion.update(getPos())) {
				System.out.println("clear");
				this.getTris().clear();
			}
		hitTimer--;
	}
	
	public void destroy(Game game) {
		if (hitTimer < 0) {
			health -= 10;
			if (health < 0)
				health = 0;
			hitTimer = 10;
		}
		if (health <= 0 && explosion == null) {
			explosion = new Explosion(this.getPos(), 20, 10);
			game.getMeshList().add(explosion);
		}
	}
}
