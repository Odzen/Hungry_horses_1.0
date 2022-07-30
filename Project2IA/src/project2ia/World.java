package project2ia;

/*
1 -> Player 1 = User
2 -> Player 2 = Machine
3 -> Flower
4 -> Grass
5 -> Apple
*/
public class World {

    private int width = 0;
    private int height = 0;
    private int[][] world;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.world = new int[width][height];
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int[][] getWorld() {
        return world;
    }

    public void setWorld(int[][] newWorld) {
        this.world = newWorld;
    }
    
    //public World randomWorld() {}
    
    // Cosindering that 1 -> Player horse, and 2 -> Machine horse
    public boolean isThereAnyHorse(int x, int y) {
        if(this.getWorld()[x][y] == 1 || this.getWorld()[x][y] == 2)
            return true;
        else
            return false;
    }

    public void printWorld() {
        for (int[] x : this.world) {
            for (int y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }
}
