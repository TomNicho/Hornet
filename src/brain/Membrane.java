package src.brain;

import java.util.ArrayList;
import java.util.List;

public class Membrane {
    public Integer id;
    public List<Neuron> neurons;
    public Membrane connectedMembrane;

    public Membrane(Integer id, List<Neuron> neurons) {
        this.id = id;
        this.neurons = neurons;
        this.connectedMembrane = null;
    }

    public Membrane(Integer id) {
        this.id = id;
        this.neurons = new ArrayList<>();
        this.connectedMembrane = null;
    }

    public Integer[] size() {
        Integer[] sizes = {0,0};
        
        for (Neuron n : neurons) {
            sizes[1] += n.size();
            sizes[0]++; 
        }

        return sizes;
    }
}
