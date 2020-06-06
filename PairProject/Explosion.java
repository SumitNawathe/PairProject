public class Explosion extends Mesh {
	private Vector position;
	private int counter, time;
	private double size;
	
	public Explosion (Vector position, int time, double size) {
		super();
		System.out.println("creating explosion");
		this.position = position;
		this.time = time;
		this.size = size;
		try {
			setTris(Mesh.loadFromObjFile("Models/Explosion.obj", "Textures/Explosion Map 1.png").getTris());
		} catch (Exception e) { System.out.println("Error loading ring.obj"); }
		translate(position);
	}
	
	public boolean update (Vector newPos) {
		counter++;
		if (counter > time) {
			this.getTris().clear();
			return false;
		}
		translate(position.clone().scale(-1));
		if (counter < 4*time/7)
			for (Triangle tri : getTris())
				tri.setVerts(new Vector[] {tri.getVert1().scale(((double) counter+1)/counter), 
						tri.getVert2().scale(((double) counter+1)/counter), 
						tri.getVert3().scale(((double) counter+1)/counter)});
		else
			for (Triangle tri : getTris())
				tri.setVerts(new Vector[] {tri.getVert1().scale((double) counter/(counter+1)), 
						tri.getVert2().scale((double) counter/(counter+1)), 
						tri.getVert3().scale((double) counter/(counter+1))});
		translate(newPos);
		position = newPos;
		return true;
	}
}
