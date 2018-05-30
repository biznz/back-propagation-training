import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
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
    
    private static int inputUnits = 4;
    private static int hiddenUnits = 4;
    private static int num_hidden_layers = 1;
    private static int outputUnits = 1;
    private static double lambda;
    private static double sampleError = 0.5;
    private static Set<Example> examples;
    
    public static void main(String[] args){
        
        readInput(); // read the examples from the file
        Network ANN = new Network();
        
        Layer layer = new Layer("input");
        for(int i=0; i<inputUnits; i++){
            Unit unit = new Unit("input_"+i);
            layer.addUnit(unit);
        }
        ANN.setInput(layer);
        System.out.println(ANN.getInputLayer().toString());
        
        for(int a=0;a<num_hidden_layers;a++){
            Layer hidden = new Layer("hidden"+a);
            for(int s=0; s<hiddenUnits;s++){
                Unit unit = new Unit("hidden_"+s);
                hidden.addUnit(unit);
            }
            ANN.addLayer(hidden);
            System.out.println(hidden.toString());
        }
        
        Layer output = new Layer("output");
        for(int h=0;h<outputUnits;h++){
            Unit unit = new Unit("output");
            output.addUnit(unit);
        }
        ANN.setOutput(output);
        System.out.println(ANN.getOutputLayer().toString());
        back_prop_learning(examples, ANN); // calls the artificial neural network method
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
                System.out.println(ex);
            /* Propagate the inputs forward to compute the outputs */
                int s = 0;
                for(Unit node: network.getInputLayer().getUnits()){
                    s++;
                    int value = ex.getInput()[s];
                    node.setA(value);
                    System.out.println(node);
                }
                //on each of the layers on the list of layers
                while(network.getListIterator().hasNext()){
                    Layer temp = network.getListIterator().next();
                    for(Unit unit: temp.getUnits()){
                        unit.setIn(unit.weighted_sum_of_inputs());
                        unit.setA(network.g(unit.getIn()));
                        System.out.println(unit);
                    }
                }
                /* Propagate deltas backward from output layer to input layer */
                for(Unit j: network.getOutputLayer().getUnits()){
                    // calculate the delta for node unit
                    double errorj = network.d_g(j.getIn())* ( ex.getOutput() -j.getA()); 
                    //double errorj = 0;
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
                        System.out.println(i);
                    }
                }
                // for each weight in the network
                /* Update every weight in network using deltas */
                for(Link l:network.getLinks()){
                    //calculate new weight
                    double alpha=0.0;
                    double weightij = l.getWeight()+ lambda * l.getUpstream().getA() 
                            * delta.get(l.getDownstream().getRef());
                    l.setWeight(weightij);
                    System.out.println(l);
                }
            }
            
        }while(training_error()> 0.05);
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
        
        return sampleError;
    }
    
    private static void readInput(){
        System.out.println("Please insert a file to read");
        Scanner scanner = new Scanner(System.in);
        String filePath="";
        String exampleLine="";
        examples = new HashSet<Example>();
        if(scanner.hasNextLine()){
            filePath = scanner.nextLine();
        }
        try{
            BufferedReader in = new BufferedReader(new FileReader(new File(filePath)));
            while((exampleLine=in.readLine())!=null){
                Example ex = new Example(exampleLine);
                examples.add(ex);
            }
        }
        catch(Exception ex){
            System.out.println("File not found");
        }
    }
    
}
