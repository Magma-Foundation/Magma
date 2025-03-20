export interface Option<T> {
    orElse(other: T): T;

    map<R>(mapper: (value : T) => R): Option<R>;
}

export function None<T>(): Option<T> {
    return {
        map<R>(): Option<R> {
            return None();
        },
        orElse(other: T): T {
            return other;
        }
    }
}

export function Some<T>(value: T): Option<T> {
    return {
        map<R>(mapper: (value : T) => R): Option<R> {
            return Some(mapper(value));
        },
        orElse(other: T): T {
            return value;
        }
    }
}

export interface Map_<K, V> {
    apply(key: K): Option<V>;

    with(key: K, value: V): Option<Map_<K, V>>;
}

function MutableMap<K, V>(map: Map<K, V> = new Map<K, V>()): Map_<K, V> {
    return {
        apply(key: K): Option<V> {
            const value = map.get(key);
            return value ? Some(value) : None();
        },
        with(key: K, value: V): Option<Map_<K, V>> {
            if (map.has(key)) return None<Map_<K, V>>();
            map.set(key, value);
            return Some<Map_<K, V>>(this);
        }
    }
}

export let env = MutableMap<string, string>();
for (const entry of Object.entries(process.env)) {
    const key = entry[0];
    const value = entry[1];
    if (value) {
        env = env.with(key, value).orElse(env);
    }
}


