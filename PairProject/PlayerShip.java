public class PlayerShip extends Mesh {
	private Vector playerPos = new Vector(0, 0, 0);
	private int horizAngleState, vertAngleState;
	private Vector playerVel = new Vector(0.1, 0, 0);
	
	public void moveShipTo (Vector pos) {
		translate(pos.minus(playerPos));
		playerPos = pos;
	}
	public Vector getPlayerPos () { return playerPos; }
	
	public PlayerShip (Vector offset) {
		super();
		this.playerPos = offset;
		try {
			setTris(Mesh.loadFromObjFile("Models/Ship Model 3.obj", "Textures/Ship Model 3 Map.png").getTris());
		} catch (Exception e) { System.out.println("Error loading ShipModel2.obj"); }
		translate(offset);
		System.out.println(this.getTris().get(0).getTexture());
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
		translate(playerPos.clone().scale(-1));
		for (Triangle tri : getTris())
			tri.setVerts(new Vector[] {Matrix.multMatVec(rotMat, tri.getVert1()), 
					Matrix.multMatVec(rotMat, tri.getVert2()), 
					Matrix.multMatVec(rotMat, tri.getVert3())});
		translate(playerPos);
		
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
			horiz = -0.25;
		else if (horiz == 0 && playerVel.getZ() > 0)
			horiz = 0.25;
		
		if ((playerVel.getY() >= 0.4 && vert == 1) || (playerVel.getY() <= -0.4 && vert == -1))
			vert = 0;
		else if (vert == 0 && playerVel.getY() > 0)
			vert = Math.max(-0.25, -playerVel.getY());
		else if (vert == 0 && playerVel.getY() < 0)
			vert = Math.min(0.25, -playerVel.getY());
		
		playerVel = playerVel.plus(new Vector(speed/30.0, vert/20.0, -horiz/20.0));
		if ((playerPos.getY() >= 17 && playerVel.getY() > 0) || (playerPos.getY() <= -17 && playerVel.getY() < 0))
			playerVel = new Vector(playerVel.getX(), 0, playerVel.getZ());
		if ((playerPos.getZ() >= 19 && playerVel.getZ() > 0) || (playerPos.getZ() <= -19 && playerVel.getZ() < 0))
			playerVel = new Vector(playerVel.getX(), playerVel.getY(), 0);
		moveShipTo(playerPos.plus(playerVel));
	}
}
