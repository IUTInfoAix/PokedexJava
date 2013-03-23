package fr.univaix.iut.pokebattle;

import com.gistlabs.mechanize.document.node.Node;

/**
 * User: nedjar
 * Date: 23/03/13
 * Time: 16:14
 */
public class Parser {
    int parseInt(Node node) {
        String value = parseString(node);
        if (value.equals("—") || value.isEmpty())
            return 0;
        return Integer.parseInt(value);
    }

    String parseString(Node node) {
        return node.getValue().replaceAll("\\*|%", "");
    }

    <T extends Enum<T>> T parseEnum(Node node, Class<T> tClass) {
        String value = parseString(node).toUpperCase();
        if (value.equals("???"))
            value = "UNKNOWN";
        return T.valueOf(tClass, value);
    }
}
