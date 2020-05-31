public class ChargeShot extends Effect {
	private static double[][] rotMat = Matrix.getRotMatX(Math.PI/10);
	
	public ChargeShot (Vector position) {
		super();
		setPos(position);
		try {
			setTris(Mesh.loadFromObjFileNoTexture("Models/charge3.obj").getTris());
		} catch (Exception e) { System.out.println("Error loading ring.obj"); }
		translate(position);
	}	
	
	public void update(Game game) {
		this.translate(getPos().clone().scale(-1));
		for (Triangle tri : getTris())
			tri.setVerts(new Vector[] {Matrix.multMatVec(rotMat, tri.getVert1()), 
					Matrix.multMatVec(rotMat, tri.getVert2()), 
					Matrix.multMatVec(rotMat, tri.getVert3())});
		translate(game.getPlayerShip().getPos());
		setPos(game.getPlayerShip().getPos());
	}
}
