package src.brain;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class Brain {
    public Dictionary<Integer, Membrane> membranes;  

    public Brain() {
        this.membranes = new Hashtable<>();
    }

    public void addLayer(Integer size, Integer id) {
        List<Neuron> neurons = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            neurons.add(new Neuron(i, (float) Math.random(), new ArrayList<Synapse>()));
        }

        membranes.put(id, new Membrane(id, neurons));
    }

    public void connectLayer(Integer start, Integer end) {
        Membrane layerStart = membranes.get(start);
        Membrane layerEnd = membranes.get(end);
        int count = 0;

        for (Neuron n : layerStart.neurons) {
            for (Neuron m : layerEnd.neurons) {
                n.synapses.add(new Synapse(count, (float) Math.random(), n, m));
            }
        }

        layerStart.connectedMembrane = layerEnd;
    }

    public float[] compute(float[] inputs) {
        if (inputs.length == this.membranes.get(0).neurons.size()) {
            return recursive_compute(inputs, this.membranes.get(0));
        } else {
            return null;
        }
    }

    private float[] recursive_compute(float[] inputs, Membrane membrane) {

        if (membrane.connectedMembrane != null) {
            float[][] outputs = membrane.compute(inputs);
            float[] averages = new float[outputs.length];

            for (int i = 0; i < outputs.length; i++) {
                averages[i] = average_float(outputs[i]);
            }

            return recursive_compute(averages, membrane.connectedMembrane);
        } else {
            return inputs;
        }

    }

    private float average_float(float[] inputs) {
        float total = 0f;

        for (int i = 0; i < inputs.length; i++) {
            total += inputs[i];
        }

        return total / inputs.length;
    }

    public Integer[] size() {
        Integer[] sizes = {0,0,0};
        int m_size = 0, n_size = 0, s_size = 0;

        Enumeration<Integer> keys = membranes.keys();
    
        while (keys.hasMoreElements()) {
            Integer[] m_sizes = membranes.get(keys.nextElement()).size();
            s_size += m_sizes[1];
            n_size += m_sizes[0];
            m_size++;
        }

        sizes[0] = m_size;
        sizes[1] = n_size;
        sizes[2] = s_size;

        return sizes;
    }
}
