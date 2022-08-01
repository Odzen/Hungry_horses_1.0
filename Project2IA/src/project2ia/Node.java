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
                // rest total qty of items by 1
            } else if (childNode.getWorld().getMatrix()[x][y] == 4) {
                childNode.setMachinePoints(this.getMachinePoints() + 1);
                // rest total qty of items by 1
            } else if (childNode.getWorld().getMatrix()[x][y] == 5) {
                childNode.setMachinePoints(this.getMachinePoints() + 5);
                // rest total qty of items by 1
            }
        } else {
            if (childNode.getWorld().getMatrix()[x][y] == 3) {
                childNode.setHumanPoints(this.getHumanPoints() + 3);
                // rest total qty of items by 1
            } else if (childNode.getWorld().getMatrix()[x][y] == 4) {
                childNode.setHumanPoints(this.getHumanPoints() + 1);
                // rest total qty of items by 1
            } else if (childNode.getWorld().getMatrix()[x][y] == 5) {
                childNode.setHumanPoints(this.getHumanPoints() + 5);
                // rest total qty of items by 1
            }
        }

        childNode.getWorld().getMatrix()[x][y] = horse;

        childNode.setDepth(this.getDepth() + 1);

        if (this.getType().equals(TypeNodeMinMax.MAX)) {
            childNode.setType(TypeNodeMinMax.MIN);
        } else if (this.getType().equals(TypeNodeMinMax.MIN)) {
            childNode.setType(TypeNodeMinMax.MAX);
        }

        return childNode;
    }

    public Vector<Node> possibleMoves(TypePlayer playerType) {
        Coordinate playerPosition = new Coordinate();
        System.out.println("Possible Moves Machine");
        this.getWorld().printWorld();

        if (playerType.equals(TypePlayer.USER)) {
            for (int i = 0; i < this.getWorld().getWidth(); i++) {
                for (int j = 0; j < this.getWorld().getHeight(); j++) {
                    if (this.getWorld().getMatrix()[i][j] == 1) {
                        playerPosition.setX(i);
                        playerPosition.setY(j);
                        System.out.println("USER:" + i + "," + j);
                    }
                }
            }
        } else if (playerType.equals(TypePlayer.MACHINE)) {
            for (int i = 0; i < this.getWorld().getWidth(); i++) {
                for (int j = 0; j < this.getWorld().getHeight(); j++) {
                    if (this.getWorld().getMatrix()[i][j] == 2) {
                        playerPosition.setX(i);
                        playerPosition.setY(j);
                        System.out.println("MACHINE:" + i + "," + j);
                    }
                }
            }
        }

        Vector<Node> possibleMoves = new Vector();

        int twoStepsRight = playerPosition.getX() + 2;
        int twoStepsDown = playerPosition.getY() + 2;
        int twoStepsUp = playerPosition.getY() - 2;
        int twoStepsLeft = playerPosition.getX() - 2;
        int oneStepRight = playerPosition.getX() + 1;
        int oneStepDown = playerPosition.getY() + 1;
        int oneStepUp = playerPosition.getY() - 1;
        int oneStepLeft = playerPosition.getX() - 1;

        if (twoStepsRight < this.getWorld().getWidth() && oneStepUp >= 0 && (this.getWorld().isThereAnyHorse(twoStepsRight, oneStepUp) == false)) {
            possibleMoves.add(this.addChild(twoStepsRight, oneStepUp, playerType));
        }
        if (twoStepsRight < this.getWorld().getWidth() && oneStepDown < this.getWorld().getHeight() && (this.getWorld().isThereAnyHorse(twoStepsRight, oneStepDown) == false)) {
            possibleMoves.add(this.addChild(twoStepsRight, oneStepDown, playerType));
        }
        if (twoStepsDown < this.getWorld().getHeight() && oneStepLeft >= 0 && (this.getWorld().isThereAnyHorse(oneStepLeft, twoStepsDown) == false)) {
            possibleMoves.add(this.addChild(oneStepLeft, twoStepsDown, playerType));
        }
        if (twoStepsDown < this.getWorld().getHeight() && oneStepRight < this.getWorld().getWidth() && (this.getWorld().isThereAnyHorse(oneStepRight, twoStepsDown) == false)) {
            possibleMoves.add(this.addChild(oneStepRight, twoStepsDown, playerType));
        }
        if (twoStepsUp >= 0 && oneStepLeft >= 0 && (this.getWorld().isThereAnyHorse(oneStepLeft, twoStepsUp) == false)) {
            possibleMoves.add(this.addChild(oneStepLeft, twoStepsUp, playerType));
        }
        if (twoStepsUp >= 0 && oneStepRight < this.getWorld().getWidth() && (this.getWorld().isThereAnyHorse(oneStepRight, twoStepsUp) == false)) {
            possibleMoves.add(this.addChild(oneStepRight, twoStepsUp, playerType));
        }
        if (twoStepsLeft >= 0 && oneStepUp >= 0 && (this.getWorld().isThereAnyHorse(twoStepsLeft, oneStepUp) == false)) {
            possibleMoves.add(this.addChild(twoStepsLeft, oneStepUp, playerType));
        }
        if (twoStepsLeft >= 0 && oneStepDown < this.getWorld().getHeight() && (this.getWorld().isThereAnyHorse(twoStepsLeft, oneStepDown) == false)) {
            possibleMoves.add(this.addChild(twoStepsLeft, oneStepDown, playerType));
        }

        return possibleMoves;

    }

    public TypeNodeMinMax getType() {
        return type;
    }

    public void setType(TypeNodeMinMax type) {
        this.type = type;
    }

    public int getDepth() {
        return this.depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getHeuristic() {
        return this.heuristic;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    public int getMachinePoints() {
        return machinePoints;
    }

    public void setMachinePoints(int machinePoints) {
        this.machinePoints = machinePoints;
    }

    public int getHumanPoints() {
        return this.humanPoints;
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
        return this.father;
    }

    public World getWorld() {
        return this.world;
    }

    public void setWorld(World newWorld) {
        for (int row = 0; row < newWorld.getMatrix().length; row++) {
            for (int column = 0; column < newWorld.getMatrix().length; column++) {
                this.world.getMatrix()[row][column] = newWorld.getMatrix()[row][column];
            }
        }
    }
}
