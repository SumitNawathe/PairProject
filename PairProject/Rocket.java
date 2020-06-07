public class Rocket extends Effect {
	private static double[][] rotMat = Matrix.getRotMatX(Math.PI/10);
	
	public Rocket (Vector pos) {
		super();
		setPos(pos);
		try {
			setTris(Mesh.loadFromObjFileNoTexture("Models/rocket2.obj").getTris());
		} catch (Exception e) { System.out.println("Error loading rocket2.obj"); }
		translate(pos);
	}
	
	public void update(Game game) {
		this.translate(getPos().clone().scale(-1));
		for (Triangle tri : getTris())
			tri.setVerts(new Vector[] {Matrix.multMatVec(rotMat, tri.getVert1()), 
					Matrix.multMatVec(rotMat, tri.getVert2()), 
					Matrix.multMatVec(rotMat, tri.getVert3())});
		this.translate(game.getPlayerShip().getPos().clone());
		setPos(game.getPlayerShip().getPos());
	}
}
