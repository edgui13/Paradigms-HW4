
//Name: Edgar Alcocer
//Date: March 10 2023
//Assignment Description: Build a map editor for a four-room video game with a link sprite.
//This is the controller which takes care of all the key functionality
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Iterator;

class Controller implements ActionListener, MouseListener, KeyListener {

	View view;
	Model model;
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	boolean editMode;

	Controller() {
	}

	public void actionPerformed(ActionEvent e) {
	}

	// Set destination for the image to go w/ the mouse
	public void mousePressed(MouseEvent e) {
		if (editMode) {
			int mousex = e.getX();
			int mousey = e.getY();
			int x = mousex - mousex % 50 + view.scrollPosX;
			int y = mousey - mousey % 50 + view.scrollPosY;

			// check if a tile already exists at thte clicked position
			boolean tileExists = false;
			for (Tile tile : model.tiles) {
				if (tile.removeTile(x, y)) {
					model.tiles.remove(tile);
					tileExists = true;
					break;
				}
			}
			// If no tile exists, add a new tile at the clicked position
			if (!tileExists) {
				model.addTile(x, y);
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getY() < 100) {
			System.out.println("break here");
		}
	}

	void setView(View v) {
		view = v;
	}

	Controller(Model m) {
		model = m;
		Json loadFile = Json.load("map.json");
		model.unmarshal(loadFile);
		System.out.println("Map is loaded");
		editMode = false;
	}

	// Read the key that is pressed to move the image or close the window
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (!editMode) {
					keyLeft = true;
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (!editMode) {
					keyRight = true;
				}
				break;
			case KeyEvent.VK_UP:
				if (!editMode) {
					keyUp = true;
				}
				break;
			case KeyEvent.VK_DOWN:
				if (!editMode) {
					keyDown = true;
				}
				break;
			case KeyEvent.VK_A:
				if (editMode) {
					System.out.println("Moving camera left");
					if (view.scrollPosX > 0) {
						model.link.x -= 700;
					}
				}
				break;
			case KeyEvent.VK_D:
				if (editMode) {
					System.out.println("Moving camera right");
					if (view.scrollPosX < view.MAX_SCROLL_X) {
						model.link.x += 700;
					}
				}
				break;
			case KeyEvent.VK_W:
				if (editMode) {
					System.out.println("Moving camera up");
					if (view.scrollPosY > 0) {
						model.link.y -= 500;
					}
				}
				break;
			case KeyEvent.VK_X:
				if (editMode) {
					System.out.println("Moving camera down");
					if (view.scrollPosY < view.MAX_SCROLL_Y) {
						model.link.y += 500;
					}
				}
				break;
		}
	}

	// Move the image after reading the key
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (!editMode) {
					keyLeft = false;
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (!editMode) {
					keyRight = false;
				}
				break;
			case KeyEvent.VK_UP:
				if (!editMode) {
					keyUp = false;
				}
				break;
			case KeyEvent.VK_DOWN:
				if (!editMode) {
					keyDown = false;
				}
				break;
			case KeyEvent.VK_Q:
				System.exit(0); // will quit your program's execution if pressing q/Q.
				break;
			case KeyEvent.VK_ESCAPE:
				System.exit(0); // will quit your program's execution if pressing esc.
				break;
			case KeyEvent.VK_E:
				editMode = !editMode;
				// this will toggle my boolean variable
				view.printToScreen = !view.printToScreen;
				if (editMode) {
					System.out.println("Entering Edit Mode");
				} else {
					System.out.println("Exiting Edit Mode");
				}
				break;
			case KeyEvent.VK_S:
				if (editMode) {
					Json saveFile = model.marshal();
					saveFile.save("map.json");
					System.out.println("Map is Saved!");
				}
				break;
			case KeyEvent.VK_L:
				if (editMode) {
					Json loadFile = Json.load("map.json");
					model.unmarshal(loadFile);
					System.out.println("Map is loaded");
				}
				break;
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	void update() {
		model.link.prevy = model.link.y;
		view.scrollPosX = 700 * ((model.link.x + model.link.width / 2) / 700);
		view.scrollPosY = 500 * ((model.link.y + model.link.height / 2) / 500);
		if (keyRight) {
			model.link.x += Link.speed;
		}
		if (keyLeft) {
			model.link.x -= Link.speed;
		}
		if (keyRight || keyLeft) {
			for (Iterator<Tile> tileIterator = model.tiles.iterator(); tileIterator.hasNext();) {
				Tile t = tileIterator.next();
				if (model.collisionDetection(t)) {
					model.link.getOutOfTile(t);
				}
			}
		}

		model.link.prevx = model.link.x;
		if (keyDown) {
			model.link.y += Link.speed;
		}
		if (keyUp) {
			model.link.y -= Link.speed;
		}
		if (keyDown || keyUp) {
			for (Iterator<Tile> tileIterator = model.tiles.iterator(); tileIterator.hasNext();) {
				Tile t = tileIterator.next();
				if (model.collisionDetection(t)) {
					model.link.getOutOfTile(t);
				}
			}
		}
		model.link.updateImageNum(keyUp, keyDown, keyLeft, keyRight);
	}
}
