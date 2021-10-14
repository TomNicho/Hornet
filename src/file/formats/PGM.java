package src.file.formats;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
            String header = "", width = "", height = "", scale = "";
            boolean first = true, comment = false, nLine = false;
            boolean[] position = {true, false, false, false};
            int l, success = 1;

            while ((l = br.read()) != -1) {
                char c = (char) l;

                //\0 == New Line Always
                if (nLine || l == 13) {
                    if (first) {
                        success = 0;
                        break;
                    }

                    if (l == 10) {
                        first = true;
                        nLine = false;
                        comment = false;
                        continue;
                    } else if (l == 13) {
                        nLine = true;
                        continue;
                    }

                    nLine = false;
                }

                //Space == New Line except from comments
                if (c == ' ') {
                    if (first) {
                        success = 0;
                        break;
                    }

                    if (!comment) {
                        first = true;
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
                    continue;
                }

                if (position[2]) {
                    continue;
                }

                if (position[3]) {
                    continue;
                }
            }

            br.close();

            System.out.println(header);

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
