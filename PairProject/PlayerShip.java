public class PlayerShip extends Mesh {
	private Vector playerPos = new Vector(0, 0, 0);
	private double yAngle = 0, xAngle = 0;
	
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
}
