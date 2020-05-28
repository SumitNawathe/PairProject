public class PlayerShip extends Mesh {
	private Vector playerPos = new Vector(0, 0, 0);
	private double yAngle = 0, xAngle = 0;
	private Vector playerVel = new Vector(0.1, 0, 0);
	
	public void moveShipTo (Vector pos) {
		translate(pos.minus(playerPos));
		playerPos = pos;
	}
	public Vector getPlayerPos () { return playerPos; }
	public double getXAngle () { return xAngle; }
	public double getYAngle () { return yAngle; }
	public void setXAngle (double x) { xAngle = x; }
	public void setYAngle (double y) { yAngle = y; }
	
	public PlayerShip (Vector offset) {
		super();
		this.playerPos = offset;
		try {
			setTris(Mesh.loadFromObjFileNoTexture("Models/ShipModel2.obj").getTris());
		} catch (Exception e) { System.out.println("Error loading ShipModel2.obj"); }
		translate(offset);
	}
	
	public void update (double horiz, double vert, double speed) {
		System.out.println("playerVel: " + playerVel);
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
		if ((playerPos.getY() >= 12 && playerVel.getY() > 0) || (playerPos.getY() <= -12 && playerVel.getY() < 0))
			playerVel = new Vector(playerVel.getX(), 0, playerVel.getZ());
		if ((playerPos.getZ() >= 14 && playerVel.getZ() > 0) || (playerPos.getZ() <= -14 && playerVel.getZ() < 0))
			playerVel = new Vector(playerVel.getX(), playerVel.getY(), 0);
		moveShipTo(playerPos.plus(playerVel));
	}
}
