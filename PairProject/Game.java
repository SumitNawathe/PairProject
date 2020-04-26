import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Game extends JPanel {
	JFrame frame;
	ArrayList<Mesh> meshList;
	final int SCREEN_WIDTH = 1200, SCREEN_HEIGHT = 900;
	
	public Game () {
		meshList = new ArrayList<Mesh>();
		meshList.add(new MeshCube());
		
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setFocusable(true);
		this.setBackground(Color.black);
		this.setMinimumSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
//		g.setColor(Color.red);
//		g.fillRect(10, 10, 100, 50);
		for (Mesh mesh : meshList) {
			for (Triangle tri : mesh.getTris()) {
				
			}
		}
	}
	
	public static void main (String[] args) {
		Game game = new Game();
	}
}
