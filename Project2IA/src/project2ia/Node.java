package project2ia;

import java.util.Vector;

public class Node {
    
    private String type;
    private int depth;
    private int utility;
    private int machinePoints;
    private int humanPoints;
    private Node father;
    private World world;
    private Vector<Node> options = new Vector();
    
    public Node() {
        this.type = "";
        this.depth = 0;
        this.utility = 0;
        this.machinePoints = 0;
        this.humanPoints = 0;
        this.world = new World(8,8);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getUtility() {
        return utility;
    }

    public void setUtility(int utility) {
        this.utility = utility;
    }

    public int getMachinePoints() {
        return machinePoints;
    }

    public void setMachinePoints(int machinePoints) {
        this.machinePoints = machinePoints;
    }

    public int getHumanPoints() {
        return humanPoints;
    }

    public void setHumanPoints(int humanPoints) {
        this.humanPoints = humanPoints;
    }

    public Vector<Node> getOptions() {
        return options;
    }

    public void setOptions(Vector<Node> options) {
        this.options = options;
    }
    
    public void setFather(Node father){
        this.father=father;
    }
    
    public Node getFather(){
        return father;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World newWorld) {
        this.world = world;
    }
}
