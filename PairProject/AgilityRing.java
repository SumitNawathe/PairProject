import java.util.*;

public class AgilityRing extends Mesh {
	private Vector position;
	private static double[][] rotMat = Matrix.getRotMatX(Math.PI/72);
	private int counter;
	
	public AgilityRing (Vector position) {
		super();
		this.position = position;
		try {
			setTris(Mesh.loadFromObjFile("Models/Ring.obj", "Textures/Ring Map.png").getTris());
		} catch (Exception e) { System.out.println("Error loading ring.obj"); }
		translate(position);
	}
	
	public void spin (GraphicsPanel graphicsPanel) {
		translate(position.clone().scale(-1));
		for (Triangle tri : getTris()) {
			if (counter > 0)
				tri.setVerts(new Vector[] {tri.getVert1().scale(0.8), tri.getVert2().scale(0.8), tri.getVert3().scale(0.8)});
			else
				tri.setVerts(new Vector[] {Matrix.multMatVec(rotMat, tri.getVert1()), 
						Matrix.multMatVec(rotMat, tri.getVert2()), 
						Matrix.multMatVec(rotMat, tri.getVert3())});
		}
		if (counter > 0) {
			translate(graphicsPanel.getPlayerShip().getPos());
			position = graphicsPanel.getPlayerShip().getPos();
		} else
			translate(position);
		if (counter == 10)
			this.getTris().clear();
		if (counter > 0) {
			counter++;
		}
	}
	
	public void shipCollision (PlayerShip ship) {
		if (counter == 0 && position.clone().minus(ship.getPos()).magnitude() < (1.7 + 1.5)) {
			ship.decreaseEnergy(-5);
			System.out.println("collision");
			counter++;
		}
//		return (position.clone().minus(ship.getPos()).magnitude() < (1.7 + 1.5));
	}
}
