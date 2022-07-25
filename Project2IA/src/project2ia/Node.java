/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2ia;

import java.util.Vector;

/**
 *
 * @author Juan David
 */
public class Node {
    
    public String type;
    public int profundity;
    public int utility=0;
    public int machineValue=0;
    public int humanValue=0;
    public Node father;
    public int world[][] = new int[8][8];
    public Vector<Node> options = new Vector();
    
    public Node() { 
    }
    
    public void setFather(Node father){
        this.father=father;
    }
    
    public Node getFather(){
        return father;
    }
    
    public void setWorld(int[][] newWorld){
        for (int i=0; i<newWorld.length; i++){
            for (int j=0; j<newWorld[i].length;j++){
                world[i][j] = newWorld[i][j];
            }
        }
    }
    
    public int[][] getWorld(){
        return world; 
    }
}
