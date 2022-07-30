package project2ia;

import java.util.Vector;

public class Node {

    private TypeNodeMinMax type;
    private int depth;
    private int heuristic;
    private int machinePoints;
    private int humanPoints;
    private Node father;
    private World world;
    private Vector<Node> children = new Vector();

    public Node() {
        this.type = TypeNodeMinMax.UNDEFINED;
        this.depth = 0;
        this.heuristic = 0;
        this.machinePoints = 0;
        this.humanPoints = 0;
        this.world = new World(8, 8);
    }

    public Node addChild(int x, int y, TypePlayer playerType) {
        Node childNode = new Node();
        childNode.setWorld(this.getWorld());
        childNode.setFather(this);
        childNode.setHumanPoints(this.getHumanPoints());
        childNode.setMachinePoints(this.getMachinePoints());

        int horse;
        if (playerType.equals(TypePlayer.USER)) {
            horse = 1;
        } else {
            horse = 2;
        }

        for (int i = 0; i < childNode.getWorld().getMatrix().length; i++) {
            for (int j = 0; j < childNode.getWorld().getMatrix()[i].length; j++) {
                if (childNode.getWorld().getMatrix()[i][j] == horse) {
                    childNode.getWorld().getMatrix()[i][j] = 0;
                }
            }
        }

        if (playerType.equals(TypePlayer.MACHINE)) {
            if (childNode.getWorld().getMatrix()[x][y] == 3) {
                childNode.setMachinePoints(this.getMachinePoints() + 3);
            } else if (childNode.getWorld().getMatrix()[x][y] == 4) {
                childNode.setMachinePoints(this.getMachinePoints() + 1);
            } else if (childNode.getWorld().getMatrix()[x][y] == 5) {
                childNode.setMachinePoints(this.getMachinePoints() + 5);
            }
        } else {
            if (childNode.getWorld().getMatrix()[x][y] == 3) {
                childNode.setHumanPoints(this.getHumanPoints() + 3);
            } else if (childNode.getWorld().getMatrix()[x][y] == 4) {
                childNode.setHumanPoints(this.getHumanPoints() + 1);
            } else if (childNode.getWorld().getMatrix()[x][y] == 5) {
                childNode.setHumanPoints(this.getHumanPoints() + 5);
            }
        }
        
        childNode.getWorld().getMatrix()[x][y] = horse;
        
        childNode.setDepth(this.getDepth() + 1);
        
        if(this.getType().equals(TypeNodeMinMax.MAX)) {
            childNode.setType(TypeNodeMinMax.MIN);
        } else if(this.getType().equals(TypeNodeMinMax.MIN)) {
            childNode.setType(TypeNodeMinMax.MAX);
        }
        
        return childNode;
    }

    public TypeNodeMinMax getType() {
        return type;
    }

    public void setType(TypeNodeMinMax type) {
        this.type = type;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(int points) {
        this.heuristic = points;
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

    public Vector<Node> getChildren() {
        return children;
    }

    public void setChildren(Vector<Node> children) {
        this.children = children;
    }

    public void setFather(Node father) {
        this.father = father;
    }

    public Node getFather() {
        return father;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World newWorld) {
        this.world = world;
    }
}
