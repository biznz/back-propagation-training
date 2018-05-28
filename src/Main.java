import java.util.HashMap;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author biznz
 */
public class Main {
    
    private static int inputUnits;
    private static int hiddenUnits;
    private static int num_hidden_layers;
    private static int outputUnits;
    
    public static void main(String[] args){
        Network ANN = new Network();
        
        Layer layer = new Layer("input");
        for(int i=0; i<inputUnits; i++){
            Unit unit = new Unit("input_"+i);
            layer.addUnit(unit);
        }
        ANN.setInput(layer);
        
        for(int a=0;a<num_hidden_layers;a++){
            Layer hidden = new Layer("hidden"+a);
            for(int s=0; s<hiddenUnits;s++){
                Unit unit = new Unit("hidden_"+s);
                hidden.addUnit(unit);
            }
            ANN.addLayer(hidden);
        }
        
        Layer output = new Layer("output");
        for(int h=0;h<outputUnits;h++){
            Unit unit = new Unit("output");
            output.addUnit(unit);
        }
        ANN.setOutput(output);
    }
    
    private static Network back_prop_learning(Set<Example> examples,Network network){
        HashMap<String,Double> delta = new HashMap<String,Double>();
        do{
            //for each weight in network
            for(Link l:network.getLinks()){
                //set a random value for the weight
                l.setWeight(randomNumber());
            }
            for(Example ex:examples){
            /* Propagate the inputs forward to compute the outputs */
                for(Unit node: network.getInputLayer().getUnits()){
                
                }
                //on each of the layers on the list of layers
                while(network.getListIterator().hasNext()){
                    for(Unit unit: network.getListIterator().next().getUnits()){
                        unit.setIn(unit.weighted_sum_of_inputs());
                        unit.setA(network.g(unit.getIn()));
                    }
                }
                /* Propagate deltas backward from output layer to input layer */
                for(Unit j: network.getOutputLayer().getUnits()){
                    // calculate the delta for node unit
                    //double errorj = d_g(j.getIn())*
                    double errorj = 0;
                    delta.put(j.getRef(), errorj);
                    // in sum the error associated with that output unit
                }
                //on each of the layers on the list of layers    
                while(network.getListIterator().hasPrevious()){
                    // propagate the weights throughout the corresponding units
                    for(Unit i: network.getListIterator().previous().getUnits()){
                        // according to a propagation rule
                        double errori = network.d_g(i.getIn())*i.weighted_error(delta);
                        delta.put(i.getRef(),errori);
                    }
                }
                // for each weight in the network
                /* Update every weight in network using deltas */
                for(Link l:network.getLinks()){
                    //calculate new weight
                    double alpha=0.0;
                    double weightij = l.getWeight()+ alpha * l.getUpstream().getA() 
                            * delta.get(l.getDownstream().getRef());
                    l.setWeight(weightij);
                }
            }
            
        }while(training_error()!=0.05);
        return network;
    }
    
    /** 
     * used to set random values on the weights
     * @return a random int between -1 and 1 
     */
    
    private static int randomNumber(){
        double rand = Math.random();
        if(rand<0.33)
            return -1;
        else if (rand<=0.66)
            return 0;
        else{
            return 1;
        }
    }
    
    /**
     * training error
     * @return an error on the data
     */
    private static double training_error(){
        return 0;
    }
    
}
