
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author biznz
 * 
 * Neural networks are composed of nodes or units (see Figure 18.19) connected by directed
links.
 */

public class Unit {
    private String ref; //a reference of the unit
    private List<Link> links; //a list of links
    private double in; //result of the input function
    private double a; //result of activation function over the input function
    
    /**
     * Unit object constructor
     * @param ref 
     */
    public Unit(String ref){
        this.ref = ref;
        this.links = new ArrayList<Link>();
        this.in = 0;
        this.a = 0;
    }

    /**
     * toString method on the unit
     * @return a string to be printed with the activation function of the unit
     */
    @Override
    public String toString() {
        String layer="";
        if(this.ref.contains("input")){
            layer="in ";
        }
        if(this.ref.contains("output")){
            layer=" out";
        }
        if(this.ref.contains("hidden")){
            layer=" hi ";
        }
        return layer+" Unit a: "+this.getA();
    }
    
    
    
    /**
     * adds a link to the link list of the unit ( from this unit or to )
     * @param l a link to be added
     */
    public void addLink(Link l){
        this.links.add(l);
    }
    
    /**
     * function sums the product of weights and activation function
     * value on previous node
     * @return returns the sum of the weights of all input links
     */
    
    protected double weighted_sum_of_inputs(){
        double inj=0;
        for(Link l:links){
            if(l.getDownstream().equals(this)){
                inj+=l.getWeight()*l.getUpstream().getA();
            }
        }
        return inj;
    }

    /**
     * 
     * @param delta hashmap of errors on network units
     * @return the weighted error sum
     */
    protected double weighted_error(HashMap<String,Double> delta){
        double weighted_error = 0.0;
        for(Link l:this.links){
            String ref = l.getDownstream().getRef();
            weighted_error = l.getWeight() * delta.get(ref);
        }
        return weighted_error;
    }
    
    /**
     * 
     * @param in value assigned to the 
     * input result of the unit
     */
    public void setIn(double in) {
        this.in = in;
    }

    /**
     * 
     * @param a value returned by the activation function
     */
    
    public void setA(double a) {
        this.a = a;
    }

    /**
     * 
     * @return the input result of the unit
     */
    public double getIn() {
        return in;
    }

    /**
     * 
     * @return the a result of the unit
     */
    public double getA() {
        return a;
    }

    /**
     * 
     * @return a String reference of the unit
     */
    public String getRef() {
        return ref;
    }
    
    /**
     * sets a String reference for the unit
     * @param ref a string to name the unit with
     */
    public void setRef(String ref){
        this.ref = ref;
    }
    /**
     * 
     * @return an hashCode of the object
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.ref);
        return hash;
    }

    /**
     * 
     * @param obj an object to be compared with this
     * @return a boolean true if boths objects have an equal reference
     *                      false otherwise
     */
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Unit other = (Unit) obj;
        if (!Objects.equals(this.ref, other.ref)) {
            return false;
        }
        return true;
    }
    
    
}
