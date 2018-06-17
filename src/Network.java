
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author biznz
 * feed-forward network has connections only in one direction—that is, it forms a
 * directed acyclic graph.
 * 
 */

public class Network {
    private List<Layer> L; // list of layers in the network
    private Layer input; // the input layer on the network
    private Layer output; // the output layer on the network
    private Set<Link> links; // set of all links in the network
    private ListIterator<Layer> listIterator;
    
    public Network(Layer in,Layer out){
        L = new LinkedList<Layer>();
        this.links = new HashSet<Link>();
        this.listIterator = L.listIterator();
        this.input = in;
        this.output = out;
    }
    
    public Network(){
        L = new LinkedList<Layer>();
        this.links = new HashSet<Link>();
        this.listIterator = L.listIterator();
    }
    
    
    /**
     * 
     * logistic sigmoid activation function on a neuron
     * @param weighted_sum the weighted sum on all upstream links on the unit
     * @return double resulting of the function
     * 
     * 
     * on a logistic function the term
     * sigmoid perceptron is sometimes used
     * 
     */
    
    protected double g(double weighted_sum){
        double g = 1 / ( 1 + Math.exp( -1*weighted_sum ));
        return g;
    }
    
    /**
     * 
     * @param weighted_sum the weighted sum on all upstream links on the unit
     * @return double resulting of the function
     */
    protected double d_g(double weighted_sum){
        double Dg = this.g(weighted_sum);
        double result = Dg * (1-Dg);
        return result;
    }
    
    /**
     * 
     * @return a list of layers between input and output layers
     */

    public List<Layer> getLayers() {
        return L;
    }
    
    /**
     * appends a layer to the end of a list
     * @param l 
     */
    public void addLayer(Layer l){
        if(this.L.isEmpty()){
            System.out.println("L is empty");
            this.attachNeurons(this.input,l);
        }
        else{
            System.out.println("L is not empty");
            this.attachNeurons(this.L.get(this.L.size()-1), l);
        }
        this.L.add(l);
    }
    
    /**
     * adds the link to the set
     * @param l a link to add
     */
    public void addLink(Link l){
        this.links.add(l);
    }
    /**
     * creates links between neurons in last layer on the list
     * and provided
     * @param l 
     */
    public void attachNeurons(Layer previous,Layer next){
        for(Unit i: previous.getUnits()){
            for(Unit j: next.getUnits()){
                Link link = new Link(i,j);
                this.addLink(link);
            }
        }
    }

    /**
     * 
     * @return the set of links belonging to the network
     */
    public Set<Link> getLinks() {
        return links;
    }

    /**
     * 
     * @return the input layer for the network
     */
    public Layer getInputLayer() {
        return input;
    }

    
    /**
     * 
     * @return the output layer of the network
     */
    public Layer getOutputLayer() {
        return output;
    }

    /**
     * 
     * @return the list iterator for the layer list
     */
    public ListIterator<Layer> getListIterator() {
        return listIterator;
    }

    /**
     * sets the network input layer
     * @param input a layer
     */
    public void setInput(Layer input) {
        this.input = input;
    }

    /**
     * sets the network output layer
     * @param output a layer
     */
    public void setOutput(Layer output) {
        this.output = output;
        this.attachNeurons(this.L.get(this.L.size()-1), output);
    }

    @Override
    public String toString() {
        String result=""+this.getInputLayer()+"\n";
        for(Layer l: this.getLayers()){
            result+=l+"\n";
        }
        result+=this.getOutputLayer()+"\n";
        return "Network{" +  result+'}';
    }
    
    
    
}
