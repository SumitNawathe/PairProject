import java.util.*;

public class AgilityRing extends Mesh {
	private Vector position;
	private static double[][] rotMat = Matrix.getRotMatX(Math.PI/72);
	
	public AgilityRing (Vector position) {
		super();
		this.position = position;
		try {
			setTris(Mesh.loadFromObjFileNoTexture("Models/ring.obj").getTris());
		} catch (Exception e) { System.out.println("Error loading ring.obj"); }
		translate(position);
	}
	
	public void spin () {
		translate(position.clone().scale(-1));
		for (Triangle tri : getTris())
			tri.setVerts(new Vector[] {Matrix.multMatVec(rotMat, tri.getVert1()), 
					Matrix.multMatVec(rotMat, tri.getVert2()), 
					Matrix.multMatVec(rotMat, tri.getVert3())});
		translate(position);
	}
	
	public boolean shipCollision (PlayerShip ship) {
		if (position.clone().minus(ship.getPos()).magnitude() < (1.7 + 1.5))
			System.out.println("collision");
		return (position.clone().minus(ship.getPos()).magnitude() < (1.7 + 1.5));
	}
}
