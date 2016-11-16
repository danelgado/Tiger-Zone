package ui;

import java.awt.Button;
import java.awt.Color;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class MainMenu implements ActionListener{

	public MainMenu() {
		// TODO Auto-generated constructor stub
		
	}
	//construct of class UI
	public void create() {	
		JFrame menu = new JFrame("Main Menu");
    	menu.setSize(1800,1500);
        menu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        menu.setVisible(true);
        Panel container1 = new Panel();
		container1.setSize(100, 1000);
		container1.setLayout(new BoxLayout(container1, BoxLayout.Y_AXIS));
        Button play = new Button("Play");
        Label Tiger = new Label("Tiger Zone");
        Tiger.setAlignment(Label.CENTER);
        Button quit = new Button("Quit");
        play.setSize(10, 5);
        quit.setSize(10, 5);
        play.addActionListener(this);
        container1.add(Tiger);
        container1.add(play);
        container1.add(quit);
        menu.add(container1);
	}
	
	public void actionPerformed(ActionEvent e) {
		try{

			tiger_zone();
		}catch(IOException ex){
			System.out.println("No image");
		}
	}
	
	public void tiger_zone() throws IOException{
		 JFrame myFrame = new JFrame("Tiger Zone");
	        myFrame.setSize(1800,1500);
	        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	        myFrame.setVisible(true);
	        
	        Label p1 = new Label("Player 1");
	        p1.setAlignment(Label.LEFT);
	        p1.setForeground(Color.BLACK);
	        Label s1 = new Label("Score: ");
	        s1.setAlignment(Label.LEFT);
	        s1.setForeground(Color.BLACK);
	        Label p2 = new Label("Player 2");
	        p2.setAlignment(Label.RIGHT);
	        p2.setForeground(Color.BLACK);
	        Label s2 = new Label("Score: ");
	        s2.setAlignment(Label.RIGHT);
	        s2.setForeground(Color.BLACK);
	        Panel container2 = new Panel();
	        container2.setLayout(new BoxLayout(container2, BoxLayout.Y_AXIS));
			container2.setSize(100, 1000);
	        container2.add(p1);
	        container2.add(p2);
	        container2.add(s1);
	        container2.add(s2);
	        myFrame.add(container2);

	        String path = "table.jpg";
	        File file = new File(path);
	        BufferedImage image = ImageIO.read(file);
	        JLabel label = new JLabel(new ImageIcon(image));
	        JPanel f = new JPanel();
	    
	        f.add(label);
	        f.setLocation(200,200);
	        f.setVisible(true);
	        myFrame.add(f);
	      //  myFrame.add(game_tiles.getTile());
	}

}
