import java.util.Arrays;
import java.util.Random;

public class EnemyA extends Enemy {

	public EnemyA (Vector pos, int dist) {
		super(pos, dist);
		setPos(pos);
		try {
			setTris(Mesh.loadFromObjFile("Models/Ship Model 4.obj", "Textures/Ship Model 4 Map.png").getTris());
		} catch (Exception e) { System.out.println("Error loading smallbulletsphere2.obj"); }
		translate(pos);
	}

}
