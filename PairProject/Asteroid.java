public class Asteroid extends Mesh {
	private Vector position;
	
	public Asteroid (Vector position) {
		super();
		this.position = position;
		try {
			setTris(Mesh.loadFromObjFileNoTexture("Models/asteroidsphere.obj").getTris());
		} catch (Exception e) { System.out.println("Error loading ring.obj"); }
		translate(position);
	}
	
	
}
