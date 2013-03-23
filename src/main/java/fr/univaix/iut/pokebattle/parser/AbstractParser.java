package fr.univaix.iut.pokebattle.parser;

import com.gistlabs.mechanize.document.node.Node;

public abstract class AbstractParser<T> implements Parser<T> {
    static int parseInt(Node node) {
        String value = parseString(node);
        if (value.equals("—") || value.isEmpty())
            return 0;
        return Integer.parseInt(value);
    }

    static String parseString(Node node) {
        return node.getValue().replaceAll("\\*|%", "");
    }

    static <T extends Enum<T>> T parseEnum(Node node, Class<T> tClass) {
        String value = parseString(node).toUpperCase();
        if (value.equals("???"))
            value = "UNKNOWN";
        return T.valueOf(tClass, value);
    }
}
