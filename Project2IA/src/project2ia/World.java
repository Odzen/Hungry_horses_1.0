package project2ia;

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

    public void setWorld(int[][] world) {
        this.world = world;
    }

    public void setWorld(World newWorld) {
        for (int i = 0; i < newWorld.getWorld().length; i++) {
            for (int j = 0; j < newWorld.getWorld()[i].length; j++) {
                this.world[i][j] = newWorld.getWorld()[i][j];
            }
        }
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
