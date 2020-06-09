public class BossFrame extends SpaceShip {
	private double theta;
	public double getTheta () { return theta; }
	private double[][] rotMat;
	private int fireMod;
	
	public BossFrame (Game game) {
		setPos(new Vector(game.getPlayerShip().getPos().getX()+20, 0, 0));
		try {
			setTris(Mesh.loadFromObjFile("Models/BossFrame1.obj", "Textures/BorgImage2.png").getTris());
//			setTris(Mesh.loadFromObjFile("Models/BossWeaknessModel.obj", "Textures/BossWeakness.png").getTris());
//			setTris(Mesh.loadFromObjFile("Models/Ship Model 4.obj", "Textures/Ship Model 4 Map.png").getTris());
		} catch (Exception e) { System.out.println("Error loading smallbulletsphere2.obj"); }
		
		for (Triangle tri : getTris())
			tri.setVerts(new Vector[] {tri.getVert1().scale(5), 
					tri.getVert2().scale(5), 
					tri.getVert3().scale(5)});
		
		translate(new Vector(game.getPlayerShip().getPos().getX()+20, 0, 0));
		setCollisionRadius(0.0);
		rotMat = Matrix.getRotMatX(Math.PI/2/120);
	}
	
	public void update(Game game) {
		translate(getPos().clone().scale(-1));
		for (Triangle tri : getTris())
			tri.setVerts(new Vector[] {Matrix.multMatVec(rotMat, tri.getVert1()), 
					Matrix.multMatVec(rotMat, tri.getVert2()), 
					Matrix.multMatVec(rotMat, tri.getVert3())});
		translate(new Vector(game.getPlayerShip().getPos().getX()+20, 0, 0));
		setPos(new Vector(game.getPlayerShip().getPos().getX()+20, 0, 0));
		theta += Math.PI/2/120;
		
		if (fireMod % 5 == 0) {
			int firePos = (int) (4*Math.random());
			game.fireBullet(getPos().clone().plus(new Vector(0, 5*3.5*Math.cos(firePos*Math.PI/2+theta+Math.PI/4), 5*3.5*Math.sin(firePos*Math.PI/2+theta+Math.PI/4))), new Vector(-1, 0, 0), 1.5);
		}
		fireMod++;
	}

	public void destroy(Game game) {
		
	}
}
