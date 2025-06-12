package org.example.learningsystem.core.multitenancy.db.util;

import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Map.Entry;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ConverterUtils {

    public static <T, V> Map<Object, Object> toObjectsMap(Map<T, V> map) {
        return map.entrySet()
                .stream()
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }
}
