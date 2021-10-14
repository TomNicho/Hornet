package src.file;

public class ByteConvert {
    public static int UByteGet(Byte b) {
        return Integer.parseInt(b.toString()) + 128;
    }

    public static Byte UByteSet(int number) {
        Integer num = number - 128;
        return Byte.parseByte(num.toString());
    }
}
