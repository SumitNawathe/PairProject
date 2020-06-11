import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;

import javax.imageio.*;
import javax.swing.*;

public class DifficultyScreen extends JPanel {
	private int SCREEN_WIDTH, SCREEN_HEIGHT, save;
	private double scaleX, scaleY;
	private JButton easy, med, hard;
	private File clearSave;
	private byte[] cs;

	public DifficultyScreen (GameFrame gameFrame, int SCREEN_WIDTH, int SCREEN_HEIGHT, int save) {
		this.SCREEN_WIDTH=SCREEN_WIDTH;
		this.SCREEN_HEIGHT=SCREEN_HEIGHT;
		scaleX=(double)SCREEN_WIDTH/1200;
		scaleY=(double)SCREEN_HEIGHT/900;
		this.save=save;

		setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setLayout(null);
		setBackground(Color.gray);

		String saveLocation="SaveFiles/SaveFile"+save+".txt";
		clearSave=new File("SaveFiles/ClearSave.txt");
		try {
			cs=Files.readAllBytes(clearSave.toPath());
		} catch (Exception e) {System.out.println("Failed to read Clear Save");}

		easy = new JButton("EASY");
		easy.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent event) {
				writeDifficulty(saveLocation, 0);
				gameFrame.goToLevelSelectScreen(saveLocation);
			}
		});
		add(easy);
		easy.setSize(new Dimension(4*SCREEN_WIDTH/21, 2*SCREEN_HEIGHT/21));
		easy.setLocation(3*SCREEN_WIDTH/21, 10*SCREEN_HEIGHT/21);

		med = new JButton("MEDIUM");
		med.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent event) {
				writeDifficulty(saveLocation, 1);
				gameFrame.goToLevelSelectScreen(saveLocation);
			}
		});
		add(med);
		med.setSize(new Dimension(4*SCREEN_WIDTH/21, 2*SCREEN_HEIGHT/21));
		med.setLocation((int)(8.5*SCREEN_WIDTH/21), 10*SCREEN_HEIGHT/21);

		hard = new JButton("HARD");
		hard.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent event) {
				writeDifficulty(saveLocation, 2);
				gameFrame.goToLevelSelectScreen(saveLocation);
			}
		});
		add(hard);
		hard.setSize(new Dimension(4*SCREEN_WIDTH/21, 2*SCREEN_HEIGHT/21));
		hard.setLocation(14*SCREEN_WIDTH/21, 10*SCREEN_HEIGHT/21);
	}

	public void paintComponent (Graphics g) {
		Graphics2D g2d=(Graphics2D) g;
		g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 26));
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		g2d.setColor(Color.black);
		g2d.drawString("Select Difficulty", (int)(7.7*SCREEN_WIDTH/21), 2*SCREEN_HEIGHT/21);

	}
	
	private void writeDifficulty (String saveLocation, int difficulty) {
		try {
			OutputStream os=new FileOutputStream(saveLocation);
			byte[] diff=cs.clone();
			String temp=""+difficulty;
			diff[11]=temp.getBytes()[0];
			os.write(diff);
			os.close();
		} catch (Exception e) {System.out.println("Failed to write difficulty.");}
	}
}
