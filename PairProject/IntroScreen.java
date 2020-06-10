import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

import javax.imageio.*;
import javax.swing.*;

public class IntroScreen extends JPanel {
	private int SCREEN_WIDTH, SCREEN_HEIGHT;
	private JButton startSave1, startSave2, startSave3;
	private File clearSave;
	private byte[] cs;

	public IntroScreen (GameFrame gameFrame, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
		this.SCREEN_WIDTH = SCREEN_WIDTH;
		this.SCREEN_HEIGHT = SCREEN_HEIGHT;
		this.setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setLayout(null);

		clearSave=new File("SaveFiles/ClearSave.txt");
		try {
			cs=Files.readAllBytes(clearSave.toPath());
		} catch (Exception e) {System.out.println("Failed to read Clear Save");}

		startSave1=setButton(gameFrame, compareFiles(1), 1);
		this.add(startSave1);

		startSave2=setButton(gameFrame, compareFiles(2), 2);
		this.add(startSave2);

		startSave3=setButton(gameFrame, compareFiles(3), 3);
		this.add(startSave3);
	}

	private boolean compareFiles(int save) {
		File saveFile=new File("SaveFiles/SaveFile"+save+".txt");
		try {
			byte[] fileBytes=Files.readAllBytes(saveFile.toPath());
			return Arrays.equals(cs, fileBytes);
		} catch (Exception e) {System.out.println("Failed to read Save File "+save);}
		return false;
	}

	private JButton setButton(GameFrame gameFrame, boolean compare, int save) {
		JButton ret=new JButton("GAME "+save);
		if (compare) {
			ret.addActionListener(new ActionListener () {
				public void actionPerformed (ActionEvent event) {
					gameFrame.goToLevelSelectScreen("SaveFiles/SaveFile"+save+".txt");
				}
			});	
		} else {
			ret.addActionListener(new ActionListener () {
				public void actionPerformed (ActionEvent event) {
					gameFrame.goToPurgatory(save);
				}
			});	
		}
		ret.setSize(new Dimension(SCREEN_WIDTH/5, SCREEN_HEIGHT/20));
		ret.setLocation(save*SCREEN_WIDTH/4-SCREEN_WIDTH/5/2, 4*SCREEN_HEIGHT/5-SCREEN_HEIGHT/20/2);
		return ret;
	}

	public void paintComponent (Graphics g) {
		try {
			g.drawImage(ImageIO.read(new File("Textures/CoverImage.png")), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
		} catch (Exception e) {}
	}
}