package javasrc.ch01_3;

import java.lang.reflect.Array;

import lib.StdOut;

public class GenericArrayFactory<T> {
    public T[] arrayOf(Class<T[]> clazz, int length){
        return clazz.cast(Array.newInstance(clazz.getComponentType(), length));
    }

    public static void main(String[] args){
        Integer[] intArray = new GenericArrayFactory<Integer>().arrayOf(Integer[].class,42);
        StdOut.println(intArray.length);
        StdOut.println(intArray.toString());
    }
}