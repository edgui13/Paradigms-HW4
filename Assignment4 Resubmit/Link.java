
//Name: Edgar Alcocer
//Date: March 10 2023
//Assignment Description: Build a map editor for a four-room video game with a link sprite.
//this is the link class that draw itself and get out of tile if neccessary
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Link {
    int x;
    int y;
    int prevx;
    int prevy;
    // class variables for width and height
    // final static int width = 60;
    // final static int height = 60;
    int width = 50;
    int height = 50;
    static BufferedImage linkImage;
    BufferedImage[] linkImages;
    final int NUM_IMAGES = 50;
    final int MAX_IMAGES = 13;
    int currentImage;
    int currentImageTicker = 0;
    final static double speed = 10;

    public Link(int x, int y) {
        this.x = x;
        this.y = y;
        if (linkImage == null)
            linkImage = View.loadImage("images/link1.png");
        linkImages = new BufferedImage[NUM_IMAGES];
        for (int i = 0; i < NUM_IMAGES; i++) {
            linkImages[i] = View.loadImage("images/link" + (i + 1) + ".png");
        }
        currentImage = 0;
    }

    @Override
    public String toString() {
        return "Link (x,y) = (" + x + ", " + y + "), w = " + width + ", h = " + height;
    }

    void drawLink(Graphics g, int scrollX, int scrollY) {
        g.drawImage(this.linkImages[currentImage], x - scrollX, y - scrollY, width, height, null);
    }

    void update() {

    }

    public void updateImageNum(boolean keyUp, boolean keyDown, boolean keyLeft, boolean keyRight) {
        boolean keyy = keyUp != keyDown;
        boolean keyx = keyRight != keyLeft;
        if (!keyy && !keyx)
            return;
        currentImageTicker++;
        if (keyx) {
            if (keyRight) {
                currentImageTicker %= 6;
                currentImage = currentImageTicker + 30;
            } else {
                currentImageTicker %= 12;
                currentImage = currentImageTicker + 13;
            }
        } else {
            if (keyDown) {
                currentImageTicker %= 12;
                currentImage = currentImageTicker + 0;
            } else {
                currentImageTicker %= 11;
                currentImage = currentImageTicker + 39;
            }
        }
    }

    public void setPreviousCoordinates() {
        prevx = x;
        prevy = y;
    }

    public void getOutOfTile(Tile t) {
        int differenceX = 0;
        int differenceY = 0;
        if (x + width > t.x && prevx + width <= t.x) { // right of link
            differenceX = (t.x - width) - x;
        } else if (x < t.x + t.width && prevx >= t.x + t.width) { // left of link
            differenceX = (t.x + t.width) - x;
        }
        x += differenceX;
        if (y < t.y + t.height && prevy >= t.y + t.height) { // top of link
            differenceY = (t.y + t.height) - y;
        } else if (y + height > t.y && prevy + height <= t.y) { // bottom of link
            differenceY = (t.y - height) - y;
        }
        y += differenceY;
    }
}
