package src.file.formats;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import src.file.ByteConvert;

public class PGM extends File {
    private Integer width, height, max;
    private String tag;
    private Byte[] pixels;

    public PGM(String path) {
        super(path);
        read();
    }

    private int read() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(this));
            String header = "", t_width = "", t_height = "", t_scale = "", t_pixel = "";
            boolean first = true, comment = false;
            boolean[] position = {true, false, false, false, false};
            List<Byte> t_pixels = new ArrayList<Byte>();
            int l, success = 1;

            while ((l = br.read()) != -1) {
                char c = (char) l;

                if (l == 13) {
                    continue;
                }

                //\0 == New Line Always
                //Space == New Line except from comments
                if (c == ' ' || l == 10) {
                    if (first) {
                        success = 0;
                        break;
                    }

                    if (!comment || l == 10) {
                        if (!position[4] && !comment) {
                            for (int i = 0; i < position.length; i++) {
                                if (position[i]) {
                                    if (i + 1 < position.length) {
                                        position[i] = false;
                                        position[i + 1] = true;
                                        break;
                                    } 
                                }
                            }
                        } else {
                            if (!t_pixel.equals("")) {
                                t_pixels.add(ByteConvert.UByteSet(Integer.parseInt(t_pixel)));
                                t_pixel = "";
                            }
                        }

                        first = true;
                        comment = false;

                        continue;
                    }
                }

                if (comment) {
                    continue;
                }

                if (first) {
                    first = false;

                    if (c == '#') {
                        comment = true;
                        continue;
                    }
                }

                if (position[0]) {
                    header += c;
                    continue;
                }

                if (position[1]) {
                    t_width += c;
                    continue;
                }

                if (position[2]) {
                    t_height += c;
                    continue;
                }

                if (position[3]) {
                    t_scale += c;
                    continue;
                }

                if (position[4]) {
                    t_pixel += c;
                    continue;
                }
            }

            br.close();

            t_pixels.add(ByteConvert.UByteSet(Integer.parseInt(t_pixel)));

            if (Integer.parseInt(t_height) * Integer.parseInt(t_width) != t_pixels.size()) {
                success = 0;
                return success;
            }

            if (success == 1) {
                this.tag = header;
                this.width = Integer.parseInt(t_width);
                this.height = Integer.parseInt(t_height);
                this.max = Integer.parseInt(t_scale);
                this.pixels = t_pixels.toArray(new Byte[t_pixels.size()]);
            }

            return success;
        } catch (IOException e) {
            System.err.println(e);
            return 0;
        }
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getMax() {
        return max;
    }

    public Byte[] getPixels() {
        return pixels;
    }

    public String getTag() {
        return tag;
    }

    private float getPixelRatio(int index) {
        int pixel = ByteConvert.UByteGet(pixels[index]);

        if (pixel == 0) {
            return 0;
        } else {
            return pixel / max;
        }
    }

    public float[] getPixelRatios() {
        float[] out = new float[this.pixels.length];

        for (int i = 0; i < this.pixels.length; i++) {
            out[i] = getPixelRatio(i);
        }

        return out;
    }

    public float[] getInvertedPixelRatios() {
        float[] out = new float[this.pixels.length];

        for (int i = 0; i < this.pixels.length; i++) {
            out[i] =  1f - getPixelRatio(i);
        }

        return out;
    }

    public void draw() {
        int count = 0;

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (j == 0) {
                    System.out.print("\n");
                }

                float pixel = 1f - getPixelRatio(count);
                
                if (pixel == 1) {
                    System.out.print("#");
                } else {
                    System.out.print("-");
                }

                count++;
            }
        }

        System.out.println("\n");
    }
}
