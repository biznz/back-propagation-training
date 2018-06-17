/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author biznz
 */
public class Example {
    int[] input; // the input vector of the example
    int output; // the corresponding output value, the real values
    
    /**
     * constructor for the object
     * @param input String with input values of the network
     */
    public Example(String input){
        String[] in = input.split(" ");
        System.out.println("split length"+in.length);
        this.input = new int[in.length-1];
        this.output = Integer.parseInt(in[in.length-1]);
        //System.out.println("split length"+in.length);
        for(int i=0;i<in.length-1;i++){
            this.input[i] = Integer.parseInt(in[i]);
        }
    }

    /**
     * getter for problem input values
     * @return array of ints
     */
    public int[] getInput() {
        return input;
    }
    /**
     * getter for output value
     * @return int value
     */
    public int getOutput() {
        return output;
    }
    
    /**
     * sets the problem input values
     * @param input an array of input values
     */
    public void setInput(int[] input) {
        this.input = input;
    }
    
    /**
     * sets the problem output value
     * @param output an int output value
     */
    public void setOutput(int output) {
        this.output = output;
    }

    /**
     * to string method on the problem input
     * @return a string representing an example to be print
     */
    @Override
    public String toString() {
        return "Example{" + "input=" + inputPrinter() + ", output=" + output + '}';
    }
    
    private String inputPrinter(){
        String a="";
        for(int i=0;i<this.getInput().length;i++){
            a+=this.getInput()[i]+" ";
        }
        return a;
    }
    
}
