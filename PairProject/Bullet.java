public class Bullet extends Mesh {
	private Vector pos;
	private Vector vel = new Vector(1, 0, 0);
	public Vector getPos () { return pos; }
	
	public Bullet (Vector pos) {
		super();
		this.pos = pos;
		try {
			setTris(Mesh.loadFromObjFileNoTexture("Models/smallbulletsphere2.obj").getTris());
		} catch (Exception e) { System.out.println("Error loading smallbulletsphere2.obj"); }
		translate(pos);
	}
	
	public void update () {
		translate(vel);
		pos = pos.plus(vel);
	}
}
