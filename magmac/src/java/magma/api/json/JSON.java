package magma.api.json;

import magma.api.collect.List;
import magma.java.JavaList;

import java.util.ArrayList;

public class JSON {
    public static List<String> split(String input) {
        var list = new ArrayList<String>();
        var buffer = new StringBuilder();
        var depth = 0;

        int i = 0;
        while (i < input.length()) {
            var c = input.charAt(i);
            if (c == ',' && depth == 0) {
                list.add(buffer.toString());
                buffer = new StringBuilder();
            } else {
                if (c == '{' || c == '[') depth++;
                if (c == '}' || c == ']') depth--;
                buffer.append(c);
            }
            i++;
        }

        list.add(buffer.toString());
        return new JavaList<>(list);
    }
}
