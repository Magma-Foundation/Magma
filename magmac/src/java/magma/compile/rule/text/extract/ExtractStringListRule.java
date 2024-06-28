package magma.compile.rule.text.extract;

import magma.api.collect.stream.JoiningCollector;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.Error_;
import magma.compile.attribute.Attribute;
import magma.compile.attribute.StringListAttribute;
import magma.compile.rule.Node;
import magma.java.JavaOptionals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ExtractStringListRule extends ExtractRule {
    protected final String delimiter;

    public ExtractStringListRule(String key, String delimiter) {
        super(key);
        this.delimiter = delimiter;
    }

    @Override
    protected Optional<String> fromAttribute(Node attribute) {
        return JavaOptionals.toNative(attribute.findStringList(key).map(this::join));
    }

    private String join(magma.api.collect.List<String> list) {
        return list.stream().collect(new JoiningCollector(delimiter)).orElse("");
    }

    @Override
    protected Result<Attribute, Error_> toAttribute(String content) {
        var list = split(content);
        for (String format : list) {
            var qualified = qualify(format);
            if (qualified.isPresent()) {
                return new Err<>(qualified.get());
            }
        }

        return new Ok<>(new StringListAttribute(list));
    }

    private List<String> split(String content) {
        List<String> result = new ArrayList<>();
        int start = 0;

        while (true) {
            var end = content.indexOf(delimiter, start);
            if (end == -1) break;
            result.add(content.substring(start, end));
            start = end + delimiter.length();
        }

        result.add(content.substring(start));
        return result.stream()
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .toList();
    }


    protected abstract Optional<Error_> qualify(String child);
}
