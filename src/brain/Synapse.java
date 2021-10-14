package src.brain;

public class Synapse {
    public Integer id;
    public Float cost;
    public Neuron start, end;
    
    public Synapse(Integer id, Float cost, Neuron start, Neuron end) {
        this.id = id;
        this.cost = cost;
        this.start = start;
        this.end = end;
    }
}
