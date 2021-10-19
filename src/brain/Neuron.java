package src.brain;

import java.util.ArrayList;
import java.util.List;

public class Neuron {
    public Integer id;
    public Float weight;
    public List<Synapse> synapses;

    public Neuron(Integer id, Float weight, List<Synapse> synapses) {
        this.id = id;
        this.weight = weight;
        this.synapses = synapses;
    }

    public Neuron(Integer id, Float weight) {
        this.id = id;
        this.weight = weight;
        this.synapses = new ArrayList<>();
    }

    public Integer size() {
        return synapses.size();
    }

    public float[] compute(float input) {
        float[] outputs = new float[this.synapses.size()];

        for (int i = 0; i < this.synapses.size(); i++) {
            outputs[i] = input * this.synapses.get(i).cost;
        }

        return outputs;
    }
}
