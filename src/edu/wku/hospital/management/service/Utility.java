package edu.wku.hospital.management.service;

import java.util.Collection;
import java.util.function.Predicate;

//数据结构查找方法---断言
public class Utility {
    public static <T> T findElement(Collection<T> collection, Predicate<T> predicate) {
        for (T element : collection) {
            if (predicate.test(element)) {
                //return true 则找到所需东西
                return element;
            }
        }
        return null; 
    }
}
