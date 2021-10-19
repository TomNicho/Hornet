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

    public float[][] compute(float[] inputs) {
        float[][] outputs = new float[this.connectedMembrane.neurons.size()][inputs.length];

        if (this.neurons.size() == inputs.length) {
            for (int i = 0; i < inputs.length; i++) {
                Neuron n = this.neurons.get(i);

                float[] output = n.compute(inputs[i] * n.weight);

                for (int j = 0; j < output.length; j++) {
                    outputs[j][i] = output[j];
                }
            }
        }

        return outputs;
    }
}
