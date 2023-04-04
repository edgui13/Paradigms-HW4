
//Name: Edgar Alcocer
//Date: March 10 2023
//Assignment Description: Build a map editor for a four-room video game with a link sprite.
import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame {
	// Variables
	Model model;
	Controller controller;
	View view;

	// MAIN FUNCTION
	public static void main(String[] args) {
		Game g = new Game(); // creates a new Game object
		g.run();
	}

	// Game constructor
	public Game() {
		model = new Model();// creates a new Model object
		controller = new Controller(model);// creates a new Controller object
		view = new View(controller, model);// creates a new View object
		this.setTitle("Assingment 4 - Link's Movement");
		this.setSize(700, 500);
		// this.setUndecorated(true);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		view.addMouseListener(controller);
		this.addKeyListener(controller);
	}

	public void run() {
		while (true) {
			controller.update();
			// model.update();
			view.repaint(); // This will indirectly call View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen
			// Go to sleep for 50 milliseconds
			try {
				Thread.sleep(40);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
