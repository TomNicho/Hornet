package src.brain;

public class Neural {
    public static int Max(float[] values) {
        float max = values[0];
        int result = 0;

        for (int i = 1; i < values.length; i++) {
            if (values[i] > max) {
                max = values[i];
                result = i;
            }
        }

        return result;
    } 

    public static int Min(float[] values) {
        float min = values[0];
        int result = 0;

        for (int i = 1; i < values.length; i++) {
            if (values[i] < min) {
                min = values[i];
                result = i;
            }
        }

        return result;
    }     
    
    public static float[] Percentages(float[] inputs) {
        float[] percent = new float[inputs.length];
        float max = inputs[Max(inputs)];
        float min = inputs[Min(inputs)];

        for (int i = 0; i < inputs.length; i++) {
            percent[i] = (inputs[i] - min) / (max - min);
        }

        return percent;
    }
}