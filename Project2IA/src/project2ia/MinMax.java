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
        Node decision = this.max(this.root.getChildren());
        return decision;
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
