public class PlayerShip extends SpaceShip {
	private double health = 100, energy = 100;;
	private int horizAngleState, vertAngleState;
	private Vector playerVel = new Vector(0.1, 0, 0);
	
	public void decreaseHealth (double amt) { health -= amt; }
	public double getHealth () { return health; }
	public void decreaseEnergy (double amt) { energy -= amt; }
	public double getEnergy () { return energy; }
	
	public void moveShipTo (Vector pos) {
		translate(pos.minus(getPos()));
		setPos(pos);
	}
//	public Vector getPlayerPos () { return playerPos; }
	
	public PlayerShip (Vector offset) {
		super();
		setPos(offset);
		try {
			setTris(Mesh.loadFromObjFile("Models/ShipModel1.obj", "Textures/Ship Model 1 Map.png").getTris());
		} catch (Exception e) { System.out.println("Error loading ShipModel2.obj"); }
		translate(offset);
		System.out.println(this.getTris().get(0).getTexture());
		setCollisionRadius(2.5);
	}
	
	public void destroy (Game game) {}
	
	public void update (Game game) {
		update(0, 0, 0);
	}
	
	public void update (double horiz, double vert, double speed) {
		double[][] rotMat = Matrix.getIdentityMatrix();
		if ((horiz > 0 && horizAngleState < 15) || (horiz == 0 && horizAngleState < 0)) {
			horizAngleState++;
			rotMat = Matrix.mulMatMat(rotMat, Matrix.getRotMatX(-Math.PI/50));
		} else if ((horiz < 0 && horizAngleState > -15) || (horiz == 0 && horizAngleState > 0)) {
			horizAngleState--;
			rotMat = Matrix.mulMatMat(rotMat, Matrix.getRotMatX(Math.PI/50));
		}
		//NOTE: cannot have horiz and vert rotation simultaneously, otherwise breaks
//		if ((vert > 0 && vertAngleState < 15) || (vert == 0 && vertAngleState < 0)) {
//			vertAngleState++;
//			rotMat = Matrix.mulMatMat(rotMat, Matrix.getRotMatZ(Math.PI/50));
//		} else if ((vert < 0 && vertAngleState > -15) || (vert == 0 && vertAngleState > 0)) {
//			vertAngleState--;
//			rotMat = Matrix.mulMatMat(rotMat, Matrix.getRotMatZ(-Math.PI/50));
//		}
		translate(getPos().clone().scale(-1));
		for (Triangle tri : getTris())
			tri.setVerts(new Vector[] {Matrix.multMatVec(rotMat, tri.getVert1()), 
					Matrix.multMatVec(rotMat, tri.getVert2()), 
					Matrix.multMatVec(rotMat, tri.getVert3())});
		translate(getPos());
		
//		System.out.println("playerVel: " + playerVel);
		if ((playerVel.getX() >= 0.3 && speed == 1) || (playerVel.getX() <= -0.3 && speed == -1))
			speed = 0;
		else if (speed == 0 && playerVel.getX() > 0)
			speed = -0.25;
		else if (speed == 0 && playerVel.getX() < 0)
			speed = 0.25;
		
		if ((playerVel.getZ() <= -0.4 && horiz == 1) || (playerVel.getZ() >= 0.4 && horiz == -1))
			horiz = 0;
		else if (horiz == 0 && playerVel.getZ() < 0)
			horiz = Math.max(-0.25, playerVel.getZ());
		else if (horiz == 0 && playerVel.getZ() > 0)
			horiz = Math.min(0.25, playerVel.getZ());
		
		if ((playerVel.getY() >= 0.4 && vert == 1) || (playerVel.getY() <= -0.4 && vert == -1))
			vert = 0;
		else if (vert == 0 && playerVel.getY() > 0)
			vert = Math.max(-0.25, -playerVel.getY());
		else if (vert == 0 && playerVel.getY() < 0)
			vert = Math.min(0.25, -playerVel.getY());
		
		playerVel = playerVel.plus(new Vector(speed/30.0, vert/20.0, -horiz/20.0));
		if ((getPos().getY() >= 17 && playerVel.getY() > 0) || (getPos().getY() <= -17 && playerVel.getY() < 0))
			playerVel = new Vector(playerVel.getX(), 0, playerVel.getZ());
		if ((getPos().getZ() >= 19 && playerVel.getZ() > 0) || (getPos().getZ() <= -19 && playerVel.getZ() < 0))
			playerVel = new Vector(playerVel.getX(), playerVel.getY(), 0);
		moveShipTo(getPos().plus(playerVel));
	}
}
