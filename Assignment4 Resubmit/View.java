
//Name: Edgar Alcocer
//Date: March 10 2023
//Assignment Description: Build a map editor for a four-room video game with a link sprite.
//This is the view which is the frame that is shown to the user it moves with in combianation with the controls.
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;
import java.awt.Font;
import java.util.Iterator;

class View extends JPanel {
	BufferedImage tileImage;
	Model model;
	int scrollPosX;
	int scrollPosY;
	int MAX_SCROLL_X = 700;
	int MAX_SCROLL_Y = 500;
	boolean printToScreen;

	// View Constructor
	View(Controller c, Model m) {
		printToScreen = false;
		c.setView(this);
		this.model = m;
		this.tileImage = loadImage("images/tile.jpg");
	}

	public static BufferedImage loadImage(String filename) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(filename));// loads the image into memor itdoesnt draw it
			System.out.println(filename + " is loaded");
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.out.println("Couldn't find " + filename);
			System.exit(1);
		}
		return image;
	}

	// draw the Tiles
	public void paintComponent(Graphics g) { // this will draw the image
		g.setColor(new Color(128, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		model.link.drawLink(g, scrollPosX, scrollPosY);

		for (Iterator<Tile> tileIterator = model.tiles.iterator(); tileIterator.hasNext();) {
			Tile tile = tileIterator.next();
			g.drawImage(tileImage, tile.x - scrollPosX, tile.y - scrollPosY, tile.width, tile.height, null);
		}

		if (printToScreen) {
			g.setColor(new Color(255, 165, 0));
			g.setFont(new Font("default", Font.BOLD, 20));
			g.drawString("Edit Mode", 575, 450);
			// g.drawString("Edit Mode", this.getWidth() / 2, this.getHeight() / 2);
		}
	}
}