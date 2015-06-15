package org.adventure.random;

import java.util.Collection;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public class RandomCollection<E> {
    private final NavigableMap<Double, E> map = new TreeMap<Double, E>();
    private double total = 0;

    public RandomCollection() {
    }

    public RandomCollection(Collection<E> collection) {
    	for (E e : collection) {
			add(1, e);
		}
    }

    public RandomCollection<E> add(double weight, E result) {
        if (weight > 0) {
        	total += weight;
        	map.put(total, result);
        }
        return this;
    }

    public E next() {
        double value = ThreadLocalRandom.current().nextDouble() * total;
        return map.ceilingEntry(value).getValue();
    }
}
