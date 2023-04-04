//Name: Edgar Alcocer
//Date: March 10 2023
//Assignment Description: Build a map editor for a four-room video game with a link sprite.
//This is the tile class that is being used to map out the video game model.
public class Tile {
    // member variables for horizontal and vertical position
    int x;
    int y;
    // class variables for width and height
    int width = 50;
    int height = 50;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Tile (x,y) = (" + x + ", " + y + "), w = " + width + ", h = " + height;
    }

    public boolean removeTile(int x, int y) {
        if (x >= this.x && x <= this.x + this.width / 2 && y >= this.y && y <= this.y + this.height / 2) {
            // System.out.println("Removed Tile");
            return true;
        } else {
            return false;
        }
    }

    // marshaling
    Json marshal() {
        Json ob = Json.newObject();
        ob.add("tile_x", x);
        ob.add("tile_y", y);
        return ob;
    }

    // unmarshaling
    public Tile(Json ob) {
        // System.out.println("Tiles is being called");
        x = (int) ob.getLong("tile_x");
        y = (int) ob.getLong("tile_y");

    }
}
