//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.collmall.model;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentHashMultiSet<E> {
    private final transient ConcurrentMap<E, AtomicInteger> counterMap = new ConcurrentHashMap();

    public ConcurrentHashMultiSet() {
    }

    public int add(E element) {
        if (element == null) {
            return 0;
        } else {
            AtomicInteger existingCounter = (AtomicInteger)this.counterMap.get(element);
            if (existingCounter == null) {
                AtomicInteger newCounter = new AtomicInteger();
                existingCounter = (AtomicInteger)this.counterMap.putIfAbsent(element, newCounter);
                if (existingCounter == null) {
                    existingCounter = newCounter;
                }
            }

            return existingCounter.incrementAndGet();
        }
    }

    public Set<E> elementSet() {
        return this.counterMap.keySet();
    }

    public int count(E element) {
        if (element == null) {
            return 0;
        } else {
            AtomicInteger existingCounter = (AtomicInteger)this.counterMap.get(element);
            return existingCounter == null ? 0 : existingCounter.get();
        }
    }
}
