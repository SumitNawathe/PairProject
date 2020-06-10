
public class EnemyC extends Enemy{
	public EnemyC (Vector pos, int dist, int ai) {
		super(pos, dist, ai);
		setPos(pos);
		try {
			setTris(Mesh.loadFromObjFile("Models/Ship Model 2.obj", "Textures/Ship Model 2 Map.png").getTris());
//			setTris(Mesh.loadFromObjFile("Models/BossFrame1.obj", "Textures/BorgImage2.png").getTris());
		} catch (Exception e) { System.out.println("Error loading smallbulletsphere2.obj"); }
		translate(pos);
	}
}