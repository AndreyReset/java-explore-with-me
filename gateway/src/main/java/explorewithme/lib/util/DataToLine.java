package explorewithme.lib.util;

public class DataToLine {

    public static <T> String arrToLine(T[] data) {
        StringBuilder str = new StringBuilder("");
        if (data != null) {
            for (T el : data) {
                str.append(el);
                str.append(",");
            }
            return str.substring(0, str.toString().length() - 1);
        }
        return str.toString();
    }

    public static <T> String varToLine(T data) {
        StringBuilder str = new StringBuilder("");
        if (data != null) str.append(data);
        return str.toString();
    }
}
