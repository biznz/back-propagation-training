/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * A link from unit i to unit j serves to propagate 
 * the activation ai from unit i to unit j
 * models synapses of brains
 * @author biznz
 * 
 */

public class Link {
    private Unit upstream; //connected input unit ( neuron )
    private Unit downstream; //connected output unit ( neuron )
    private double weight; //numerical weight determining the strength and sign of a connection

    /**
     * object constructor 
     * @param start
     * @param end 
     */
    public Link(Unit start,Unit end){
        this.upstream = start;
        this.downstream = end;
        this.weight = 0;
    }
    
    /**
     * 
     * @return the object upstream node (input)
     */
    
    public Unit getUpstream() {
        return upstream;
    }

    /**
     * 
     * @return the object downstream node (output)
     */
    public Unit getDownstream() {
        return downstream;
    }

    /**
     * 
     * @return the weight of the object
     */
    
    public double getWeight() {
        return weight;
    }

    /**
     * 
     * @param upstream a unit to be the upstream node
     */
    
    public void setUpstream(Unit upstream) {
        this.upstream = upstream;
    }

    /**
     * 
     * @param downstream a unit to be the downstream node
     */
    
    public void setDownstream(Unit downstream) {
        this.downstream = downstream;
    }

    /**
     * 
     * @param weight sets the weight on the node
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    
    
}
