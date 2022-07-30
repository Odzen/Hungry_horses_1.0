package project2ia;

/*
1 -> Player 1 = User
2 -> Player 2 = Machine
3 -> Flower
4 -> Grass
5 -> Apple
*/
public class World {
    
    static final int QTY_GRASS = 14;
    static final int QTY_FLOWERS = 5;
    static final int QTY_APPLES = 2;
    
    private int totalQtyItems;
    private int width;
    private int height;
    private int[][] matrix;

    public World(int width, int height) {
        this.totalQtyItems = QTY_GRASS + QTY_FLOWERS + QTY_APPLES;
        this.width = width;
        this.height = height;
        this.matrix = new int[width][height];
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

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getTotalQtyItems() {
        return totalQtyItems;
    }

    public void setTotalQtyItems(int totalQtyItems) {
        this.totalQtyItems = totalQtyItems;
    }
    
    
    
    //public World randomWorld() {}
    
    // Cosindering that 1 -> Player horse, and 2 -> Machine horse
    public boolean isThereAnyHorse(int x, int y) {
        if(this.getMatrix()[x][y] == 1 || this.getMatrix()[x][y] == 2)
            return true;
        else
            return false;
    }

    public void printWorld() {
        for (int[] x : this.matrix) {
            for (int y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }
}
