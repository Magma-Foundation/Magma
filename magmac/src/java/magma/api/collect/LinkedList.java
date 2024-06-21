package magma.api.collect;

import magma.api.Tuple;
import magma.api.collect.stream.AbstractStream;
import magma.api.collect.stream.Head;
import magma.api.collect.stream.Stream;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;

import java.util.function.Function;

public record LinkedList<T>(Option<Link<T>> head) implements List<T> {
    public LinkedList() {
        this(new None<>());
    }

    @Override
    public List<T> add(T next) {
        return new LinkedList<>(new Some<>(head
                .map(inner -> inner.mapLast(last -> last.push(next)))
                .orElseGet(() -> new Child<>(next))));
    }

    @Override
    public Option<Tuple<T, List<T>>> popFirst() {
        if (head.isEmpty()) return new None<>();
        return head.map(link -> link.popFirst().mapRight(LinkedList::new));
    }

    @Override
    public List<T> push(T element) {
        return add(element);
    }

    @Override
    public Stream<T> stream() {
        return new AbstractStream<>(new LinkedHead<>(head));
    }

    @Override
    public boolean contains(T element) {
        return head.map(link -> link.contains(element)).orElse(false);
    }

    @Override
    public boolean isEmpty() {
        return head.isEmpty();
    }

    @Override
    public Option<T> last() {
        return head.flatMap(Link::last);
    }

    @Override
    public int size() {
        return head.map(Link::size).orElse(0);
    }

    @Override
    public Option<List<T>> mapLast(Function<T, T> mapper) {
        return head.map(link -> link.mapLast(last -> last.mapValue(mapper)))
                .<Option<Link<T>>>map(Some::new)
                .map(LinkedList::new);
    }

    interface Link<T> {
        Link<T> mapLast(Function<Link<T>, Link<T>> mapper);

        Link<T> push(T element);

        boolean contains(T element);

        Link<T> mapValue(Function<T, T> mapper);

        int size();

        Option<T> last();

        Tuple<T, Option<Link<T>>> popFirst();
    }

    record Parent<T>(T value, Link<T> child) implements Link<T> {
        @Override
        public Link<T> mapLast(Function<Link<T>, Link<T>> mapper) {
            return new Parent<>(value, child.mapLast(mapper));
        }

        @Override
        public Link<T> push(T element) {
            return child.push(element);
        }

        @Override
        public boolean contains(T element) {
            return value.equals(element) || child.contains(element);
        }

        @Override
        public Link<T> mapValue(Function<T, T> mapper) {
            return new Parent<>(mapper.apply(value), child.mapValue(mapper));
        }

        @Override
        public int size() {
            return 1 + child.size();
        }

        @Override
        public Option<T> last() {
            return child.last();
        }

        @Override
        public Tuple<T, Option<Link<T>>> popFirst() {
            return new Tuple<>(value, new Some<>(child));
        }
    }

    record Child<T>(T value) implements Link<T> {
        @Override
        public Link<T> mapLast(Function<Link<T>, Link<T>> mapper) {
            return mapper.apply(this);
        }

        @Override
        public Link<T> push(T element) {
            return new Parent<>(value, new Child<>(element));
        }

        @Override
        public boolean contains(T element) {
            return value.equals(element);
        }

        @Override
        public Link<T> mapValue(Function<T, T> mapper) {
            return new Child<>(mapper.apply(value));
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public Option<T> last() {
            return new Some<>(value);
        }

        @Override
        public Tuple<T, Option<Link<T>>> popFirst() {
            return new Tuple<>(value, new None<>());
        }
    }

    private static class LinkedHead<T> implements Head<T> {
        private Option<Link<T>> current;

        public LinkedHead(Option<Link<T>> initial) {
            this.current = initial;
        }

        @Override
        public Option<T> head() {
            if (current.isEmpty()) return new None<>();
            var link = current.orElsePanic();

            var popped = link.popFirst();
            current = popped.right();
            return new Some<>(popped.left());
        }
    }
}
