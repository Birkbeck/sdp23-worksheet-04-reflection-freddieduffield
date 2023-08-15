package reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DescribeClass {
    public DescribeClass() {
    }

    public static void main(String[] args) throws ClassNotFoundException {
        if (args.length != 1)
            System.out.println("Usage: enter a class name"); // TODO: describe how to use the utility

        Class<?> klass = Class.forName(args[0]);
        System.out.println("--------------------------------------");
        System.out.print(getModifiers(klass) + " ");
        if (klass.isInterface()) {
            System.out.print("interface ");
        } else {
            System.out.print("class ");
        }
        System.out.print(getFinalValue(klass.getName()));
        System.out.print(" { ");
        System.out.println();

        System.out.println("fields: ");
        for (Field anno : klass.getDeclaredFields()) {
            System.out.print(getModifiers(anno) + " ");
            System.out.print(getFinalValue(anno.getType().getName()) + " ");
            System.out.print(getFinalValue(anno.getName()));
            System.out.println();
        }

        System.out.println("Constructors: ");
        for (Executable constru : klass.getConstructors()) {
            System.out.print(getModifiers(constru) + " ");
            System.out.print(getFinalValue(constru.getName()));
            System.out.print(getParams(constru) + " ");
            System.out.println();
        }

        System.out.println("Methods: ");
        for (Method meth : klass.getMethods()) {
            System.out.print(getModifiers(meth));
            System.out.print(" ");
            System.out.print(getFinalValue( meth.getReturnType().toString() ) + " ");
            System.out.print(meth.getName());
            System.out.print(getParams(meth));
            System.out.println();
        }

    }

    static String getFinalValue(String input) {
        int lastDotIndex = input.lastIndexOf(".");
        return input.substring(lastDotIndex + 1);
    }

    static String getModifiers(Class<?> input) {
        return Modifier.toString(input.getModifiers());
    }

    static String getModifiers(Member input) {
        return Modifier.toString(input.getModifiers());
    }

    static String getParams(Executable input) {
        return Arrays.stream(input.getParameterTypes())
                .map(Class::getName)
                .map(DescribeClass::getFinalValue)
                .collect(Collectors.joining(", ", "(", ")"));
    }
}
