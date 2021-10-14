package src.file.formats;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PGM extends File {
    private Integer width, height, max;
    private String tag;
    private Byte[] pixels;

    public PGM(String path) {
        super(path);
    }

    public int read() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(this));
            String header = "", t_width = "", t_height = "", t_scale = "", t_pixel = "";
            boolean first = true, comment = false;
            boolean[] position = {true, false, false, false, false};
            List<Byte> t_pixels = new ArrayList<Byte>();
            int l, success = 1;

            while ((l = br.read()) != -1) {
                char c = (char) l;

                //\0 == New Line Always
                //Space == New Line except from comments
                if (c == ' ' || l == 10) {
                    if (first) {
                        success = 0;
                        break;
                    }

                    if (!comment || l == 10) {
                        first = true;
                        comment = false;

                        if (!position[4]) {
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
                                Integer shift = Integer.parseInt(t_pixel) - 128;
                                t_pixels.add(Byte.parseByte(shift.toString()));
                                t_pixel = "";
                            }
                        }

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

            if (success == 1) {
                Integer shift = Integer.parseInt(t_pixel) - 128;
                t_pixels.add(Byte.parseByte(shift.toString()));

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
}
