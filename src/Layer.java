
import java.util.HashSet;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author biznz
 * 
 * Feed-forward networks are usually arranged in layers, such that each unit receives input
only from units in the immediately preceding layer.
 */

public class Layer {
    private Set<Unit> units; //set of units beloging to the layer object
    private String label; //a label for the layer

    public Layer(String ref){
        this.units = new HashSet<Unit>();
        this.label = ref;
    }
    
    /**
     * 
     * @return set of units in the layer
     */
    public Set<Unit> getUnits() {
        return units;
    }

    /**
     * 
     * @return the label on the layer
     */
    public String getLabel() {
        return label;
    }

    /**
     * 
     * @param units sets the set of units belonging to the layer
     */
    public void setUnits(Set<Unit> units) {
        this.units = units;
    }

    /**
     * 
     * @param label sets the label on the layer
     */
    public void setLabel(String label) {
        this.label = label;
    }
    
    /**
     * adds a unit to the layer
     * @param u a unit to be added to the layer
     */
    public void addUnit(Unit u){
        this.units.add(u);
    }
    
}
