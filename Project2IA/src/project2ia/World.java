package project2ia;
import java.util.Random;

public class World{

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
    
    public World(World world) {
        this.totalQtyItems = QTY_GRASS + QTY_FLOWERS + QTY_APPLES;
        this.width = world.getWidth();
        this.height = world.getHeight();
        this.matrix = world.getMatrix();
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

    public void randomWorld() {
        Random random = new Random();
        int itemPositionX = 0;
        int itemPositionY = 0;
        int whiteHorseX = 0;
        int whiteHorseY = 0;
        int blackHorseX = 0;
        int blackHorseY = 0;
        
        for (int row = 0; row < this.width; row++) {
            for (int column = 0; column < this.height; column++) {
                this.matrix[row][column] = 0;
            }
        }
        
        int countGrass = 0;
        while(countGrass < QTY_GRASS) {
            itemPositionX = (int) (random.nextDouble() * this.width);
            itemPositionY = (int) (random.nextDouble() * this.height);
            
            if(!(this.matrix[itemPositionX][itemPositionY] == 4)) {
                this.matrix[itemPositionX][itemPositionY] = 4;
                countGrass++;
            }
        }
        
        int countFlowers = 0;
        while(countFlowers < QTY_FLOWERS) {
            itemPositionX = (int) (random.nextDouble() * this.width);
            itemPositionY = (int) (random.nextDouble() * this.height);
            
            if(this.matrix[itemPositionX][itemPositionY] == 0) {
                this.matrix[itemPositionX][itemPositionY] = 3;
                countFlowers++;
            }
        }
        
        int countApples = 0;
        while(countApples < QTY_APPLES) {
            itemPositionX = (int) (random.nextDouble() * this.width);
            itemPositionY = (int) (random.nextDouble() * this.height);
            
            if(this.matrix[itemPositionX][itemPositionY] == 0) {
                this.matrix[itemPositionX][itemPositionY] = 5;
                countApples++;
            }
        }
        
        boolean isMissingWhiteHorse = true;
        while(isMissingWhiteHorse) {
            whiteHorseX = (int) (random.nextDouble() * this.width);
            whiteHorseY = (int) (random.nextDouble() * this.height);
            
            if(this.matrix[whiteHorseX][whiteHorseY] == 0) {
                this.matrix[whiteHorseX][whiteHorseY] = 2;
                isMissingWhiteHorse = false;
            }
        }
        
        boolean isMissingBlackHorse = true;
        while(isMissingBlackHorse) {
            blackHorseX = (int) (random.nextDouble() * this.width);
            blackHorseY = (int) (random.nextDouble() * this.height);
            
            if(this.matrix[blackHorseX][blackHorseY] == 0) {
                this.matrix[blackHorseX][blackHorseY] = 1;
                isMissingBlackHorse = false;
            }
        }                  
    }

    public boolean isThereAnyHorse(int x, int y) {
        if (this.getMatrix()[x][y] == 1 || this.getMatrix()[x][y] == 2) {
            return true;
        } else {
            return false;
        }
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
