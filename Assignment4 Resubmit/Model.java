
//Name: Edgar Alcocer
//Date: March 10 2023
//Assignment Description: Build a map editor for a four-room video game with a link sprite.
//This is the model/world view of the video game.
import java.util.ArrayList;

class Model {
    ArrayList<Tile> tiles;
    Link link;
    int numCollisions = 0;

    Model() {
        tiles = new ArrayList<Tile>();
        link = new Link(100, 100);

    }

    void update() {
        link.update();
    }

    // Add tile function
    public void addTile(int x, int y) {
        Tile t = new Tile(x, y);
        tiles.add(t);
        // System.out.println("Added Tile");
    }

    public boolean collisionDetection(Tile t) {
        int linkTop = link.y;
        int linkBottom = link.y + link.height;
        int linkLeft = link.x;
        int linkRight = link.x + link.width;

        int tileTop = t.y;
        int tileBottom = t.y + t.height;
        int tileLeft = t.x;
        int tileRight = t.x + t.width;
        if (linkRight <= tileLeft) // right of Link
        {
            return false;
        }
        if (linkLeft >= tileRight) // Left of Link
        {
            return false;
        }
        if (linkBottom <= tileTop) // bottom of Link
        {
            return false;
        }
        if (linkTop >= tileBottom) // Top of Link
        {
            return false;
        }

        System.out.println("Collision Detected " + numCollisions);
        System.out.println("Colliding at x: " + t.x + " and y: " + t.y);
        numCollisions++;
        return true;
    }

    // marshal the tiles
    Json marshal() {
        Json ob = Json.newObject();
        Json tmpList = Json.newList();
        ob.add("tiles", tmpList);
        for (int i = 0; i < tiles.size(); i++) {
            tmpList.add(tiles.get(i).marshal());
        }
        return ob;
    }

    // unmarshal the tiles
    public void unmarshal(Json ob) {
        tiles.clear();
        // System.out.println("Loading the model. ArrayList is currently at " +
        // tiles.size() + "items.");
        Json tmpList = ob.get("tiles");
        for (int i = 0; i < tmpList.size(); i++)
            tiles.add(new Tile(tmpList.get(i)));
    }
}