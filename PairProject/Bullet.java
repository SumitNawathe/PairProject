import java.util.*;

public class Bullet extends Mesh {
	private double collisionRadius;
	private Vector pos;
	private Vector vel;
	private boolean enemy;
	public Vector getPos () { return pos; }
	public double getCollisionRadius () { return collisionRadius; }
	public boolean getEnemy () {return enemy; }
	
	public Bullet (Vector pos, Vector vel, double collisionRadius, boolean enemy) {
		super();
		this.pos = pos;
		this.vel = vel;
		this.collisionRadius = collisionRadius;
		this.enemy = enemy;
		try {
			setTris(Mesh.loadFromObjFile("Models/bullet.obj", "Textures/bullet map.png").getTris());
		} catch (Exception e) { System.out.println("Error loading smallbulletsphere2.obj"); }
		for (Triangle tri : getTris())
			tri.setVerts(new Vector[] {tri.getVert1().scale(collisionRadius/0.3), 
					tri.getVert2().scale(collisionRadius/0.3), 
					tri.getVert3().scale(collisionRadius/0.3)});
		translate(pos);
	}
	
	public Bullet (Mesh originalBulletClone, Vector pos, Vector vel, double collisionRadius, boolean enemy) {
		super();
		this.pos = pos;
		this.vel = vel;
		this.collisionRadius = collisionRadius;
		this.enemy = enemy;
		try {
			setTris(originalBulletClone.getTris());
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
