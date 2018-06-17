import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
    
    private static int inputUnits = 4; // number of the input Units
    private static int hiddenUnits = 4; // number of hidden units
    private static int num_hidden_layers = 1; // number of hidden layers
    private static int outputUnits = 1; // number of output units
    private static double lambda = 0.05; // learning rate
    private static double sampleError = 0.5;// sample Error
    private static Set<Example> examples; // set of examples
    private static Network ANN;
    
    public static void main(String[] args){
        
        readInput(); // read the examples from the file
        ANN = new Network();
        
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
        
        //System.out.println(ANN.getOutputLayer().toString());
        //System.out.println(ANN.getLinks()+"\n\n");
//        System.out.println("running the learning algorithm\n");
        System.out.println(back_prop_learning(examples, ANN)); // calls the artificial neural network method
    }
    
    private static Network back_prop_learning(Set<Example> examples,Network network){
        HashMap<String,Double> delta = new HashMap<String,Double>();
        do{
            //for each weight in network
            for(Link l:network.getLinks()){
                //set a random value for the weight
                l.setWeight(randomNumber());
                System.out.println("link weight"+l);
            }
            for(Example ex:examples){
                System.out.println(ex);
                
            /* Propagate the inputs forward to compute the outputs */
                int s = 0;
                for(Unit node: network.getInputLayer().getUnits()){
                    int value = ex.getInput()[s];
                    node.setA(value);
                    System.out.println(node);
                    s++;
                }
                //on each of the layers on the list of layers
                int num = 0;
                while(network.getLayers().size()>num){
                    Layer l = network.getLayers().get(num);
                    for(Unit unit: l.getUnits()){
                        unit.setIn(unit.weighted_sum_of_inputs(ANN.getLinks()));
                        unit.setA(network.g(unit.getIn()));
                        System.out.println(unit);
                    }
                    num++;
                }
                //updating on the outputLayer
                Layer lOutput = network.getOutputLayer();
                for(Unit unit: lOutput.getUnits()){
                        unit.setIn(unit.weighted_sum_of_inputs(ANN.getLinks()));
                        unit.setA(network.g(unit.getIn()));
                        System.out.println(unit);
                    }
                
                
                /* Propagate deltas backward from output layer to input layer */
                System.out.println("\npropagating backward");
                for(Unit j: network.getOutputLayer().getUnits()){
                    // calculate the delta for node unit
                    double errorj = network.d_g(j.getIn())* ( ex.getOutput() -j.getA()); 
                    System.out.println("Δ[j] ← g  (in j ) × (y j − a j )");
                    System.out.println("Δ[j] ="+errorj);
                    System.out.println("y j ="+ex.getOutput());
                    System.out.println("a j ="+j.getA());
                    //double errorj = 0;
                    delta.put(j.getRef(), errorj);
                    // in sum the error associated with that output unit
                    System.out.println(j); // prints the ouput unit current values
                }
                //on each of the layers on the list of layers
                //Layer = L-1 to 1
                /* Propagating on hidden layers */
                int as=network.getLayers().size();
                while(as>0){
                    Layer l = network.getLayers().get(as-1);
                    //update the errors on 
                    for(Unit i: l.getUnits()){
                        double errori = network.d_g(i.getIn())*i.weighted_error(delta,ANN.getLinks());
                        delta.put(i.getRef(), errori);
                        // unit.setIn(unit.weighted_sum_of_inputs());
                        // unit.setA(network.g(unit.getIn()));
                        System.out.println(i);
                    }
                    as--;
                }
                
                /* Update every weight in network using deltas */
                System.out.println("\nupdating network weights");
                for(Link l:network.getLinks()){
                    //calculate new weight
                    double linkWeight = l.getWeight();
                    // System.out.println("axiom weigth "+linkWeight);
                    // System.out.println("upstream neuron value "+l.getUpstream().getA());
                    // System.out.println("downstream neuron value "+l.getDownstream().getRef());
                    // System.out.println("delta on the upstream neuron "+delta.get(l.getDownstream().getRef()));
                    double weightij = l.getWeight()+ lambda * l.getUpstream().getA() * delta.get(l.getDownstream().getRef());
                    l.setWeight(weightij);
                    System.out.println(l);
                }
            }
        }while(stopping_criterion(delta,examples,0.05));
        //System.out.println("------------\n\n");
        return network;
    }
    
    /** 
     * used to set random values on the weights
     * @return a random int between -1 and 1 
     */
    
    private static int randomNumber(){
        double rand = Math.random();
        if(rand<1/3.0)
            return -1;
        else if (rand<=2/3.0)
            return 0;
        else{
            return 1;
        }
    }
    
    /**
     * stopping criterion
     * @return a double on the absolute error between outputUnit value and example value
     */
    private static boolean stopping_criterion(HashMap<String,Double> delta,Set<Example> examples,double learning_rate){
        for(Example ex:examples){
            if(!(ex.getOutput()-delta.get("output")<learning_rate)){
                return false;
            }
        }
        return true;
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
