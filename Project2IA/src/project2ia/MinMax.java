package project2ia;

import java.util.Vector;

public class MinMax {

    private Node root;
    private int maxDepth;
    private Vector<Node> tree = new Vector();

    public MinMax(Node root, int maxDepth) {
        this.root = root;
        root.setFather(null);
        root.setType(TypeNodeMinMax.MAX);
        this.maxDepth = maxDepth;
        tree.add(root);
        System.out.println("World Node: ");
        root.getWorld().printWorld();
    }
    
    public Node max(Vector<Node> vector) {
        Node max = vector.get(0);
        for(int position = 1; position<vector.size(); position++) {
            if(vector.get(position).getHeuristic()> max.getHeuristic())
                max = vector.get(position);
        }
        return max;
    }
    
    public Node min(Vector<Node> vector) {
        Node min = vector.get(0);
        for(int position = 1; position<vector.size(); position++) {
            if(vector.get(position).getHeuristic() < min.getHeuristic())
                min = vector.get(position);
        }
        return min;
    }
    
    public Node minMaxDecision() {
        this.buildTree();
        this.calculateInitialHerusticDeepestNodes();
        this.caculateHeuristicRestNodes();
        Node decision = this.max(this.root.getChildren());
        return decision;
    }
    
    public void buildTree() {
        int position = 0;
        System.out.println("Max Depth: " + this.maxDepth);
        
        while(this.tree.get(position).getDepth() < this.maxDepth) {
            System.out.println("Current depth, building tree: "+ this.tree.get(position).getDepth());
            
            Vector<Node> children;
            if(this.tree.get(position).getDepth() % 2 == 0)
                children = this.tree.get(position).possibleMoves(TypePlayer.MACHINE);
            else
                children = this.tree.get(position).possibleMoves(TypePlayer.USER);
            
            for(int i = 0; i < children.size() ; i++) {
                tree.add(children.get(i));
            }
            position++;
        }
    }
    
    public void calculateInitialHerusticDeepestNodes() {
        for (int position = 0; position < this.tree.size() ; position++) {
            if(this.tree.get(position).getDepth() == this.maxDepth) {
                this.tree.get(position).setHeuristic(this.tree.get(position).getMachinePoints() - this.tree.get(position).getHumanPoints());
                this.tree.get(position).getFather().getChildren().add(this.tree.get(position));
            }
        }
    }
    
    public void caculateHeuristicRestNodes() {
        int currentDepth = this.maxDepth - 1;
        while (currentDepth > 0) {
            for (int position = 0; position < this.tree.size(); position++) {
                if(this.tree.get(position).getDepth() == currentDepth) {
                    if(this.tree.get(position).getType().equals(TypeNodeMinMax.MIN)) {
                        this.tree.get(position).getFather().getChildren().add(this.tree.get(position));
                        this.tree.get(position).setHeuristic(this.min(this.tree.get(position).getChildren()).getHeuristic());
                    } else if(this.tree.get(position).getType().equals(TypeNodeMinMax.MAX)) {
                        this.tree.get(position).getFather().getChildren().add(this.tree.get(position));
                        this.tree.get(position).setHeuristic(this.max(this.tree.get(position).getChildren()).getHeuristic());
                    }
                }
            }
            currentDepth-=1;
        }
    }

    public Coordinate move() {
        Node nodeMove = minMaxDecision();
        Coordinate coordinateAfterMove = new Coordinate();
        for (int i = 0; i < nodeMove.getWorld().getMatrix().length; i++) {
            for (int j = 0; j < nodeMove.getWorld().getMatrix()[i].length; j++) {
                if (nodeMove.getWorld().getMatrix()[i][j] == 2) {
                    coordinateAfterMove.setX(i);
                    coordinateAfterMove.setY(j);
                }
            }
        }
        return coordinateAfterMove;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public Vector<Node> getTree() {
        return tree;
    }

    public void setTree(Vector<Node> tree) {
        this.tree = tree;
    }

}
