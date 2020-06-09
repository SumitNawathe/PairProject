public class Bullet extends Mesh {
	private double collisionRadius;
	private Vector pos;
	private Vector vel;
	public Vector getPos () { return pos; }
	public double getCollisionRadius () { return collisionRadius; }
	
	
	public Bullet (Vector pos, Vector vel, double collisionRadius) {
		super();
		this.pos = pos;
		this.vel = vel;
		this.collisionRadius = collisionRadius;
		try {
			setTris(Mesh.loadFromObjFile("Models/bullet.obj", "Textures/bullet map.png").getTris());
		} catch (Exception e) { System.out.println("Error loading smallbulletsphere2.obj"); }
		for (Triangle tri : getTris())
			tri.setVerts(new Vector[] {tri.getVert1().scale(collisionRadius/0.3), 
					tri.getVert2().scale(collisionRadius/0.3), 
					tri.getVert3().scale(collisionRadius/0.3)});
		translate(pos);
	}
	
	public void update () {
		translate(vel);
		pos = pos.plus(vel);
	}
}
