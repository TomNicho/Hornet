import src.brain.Brain;
import src.brain.Neural;
import src.file.formats.PGM;

public class AITest {
    public static void main(String[] args) {
        PGM image = new PGM("./input/test.pgm");
        Brain brain = new Brain();
        float[] pixels = image.getInvertedPixelRatios();

        brain.addLayer(784, 0);
        brain.addLayer(16, 1);
        brain.addLayer(16, 2);
        brain.addLayer(10, 3);

        brain.connectLayer(0, 1);
        brain.connectLayer(1, 2);
        brain.connectLayer(2, 3);

        float[] result = brain.compute(pixels);
        int max = Neural.Max(result);

        System.out.println("Result : " + max + ", Propability : " + result[max]);
    }
}