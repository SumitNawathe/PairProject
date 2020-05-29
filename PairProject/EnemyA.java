public class EnemyA extends SpaceShip {	
	public EnemyA (Vector pos) {
		super();
		setPos(pos);
		try {
			setTris(Mesh.loadFromObjFile("Models/Ship Model 3.obj", "Textures/Ship Model 3 Map.png").getTris());
		} catch (Exception e) { System.out.println("Error loading smallbulletsphere2.obj"); }
		translate(pos);
		
		setCollisionRadius(2);
	}
}
