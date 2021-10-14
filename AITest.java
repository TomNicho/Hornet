import java.util.Arrays;

import src.brain.Brain;
import src.file.formats.PGM;

public class AITest {
    public static void main(String[] args) {
        PGM image = new PGM("./input/test2.pgm");
        image.read();
    }

    public static Brain initialize() {
        Brain brain = new Brain(0);

        brain.addLayer(784, 0);
        brain.addLayer(16, 1);
        brain.addLayer(16, 2);
        brain.addLayer(10, 3);

        brain.connectLayer(0, 1);
        brain.connectLayer(1, 2);
        brain.connectLayer(2, 3);

        System.out.println(Arrays.toString(brain.size()));
        return brain;
    }
}