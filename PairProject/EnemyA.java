public class EnemyA extends Enemy {
	public EnemyA (Vector pos, int dist, int ai, int difficulty) {
		super(pos, dist, ai, difficulty);
		setPos(pos);
		try {
			setTris(Mesh.loadFromObjFile("Models/Ship Model 4.obj", "Textures/Ship Model 4 Map.png").getTris());
//			setTris(Mesh.loadFromObjFile("Models/BossFrame1.obj", "Textures/BorgImage2.png").getTris());
		} catch (Exception e) { System.out.println("Error loading smallbulletsphere2.obj"); }
		translate(pos);
	}
}
