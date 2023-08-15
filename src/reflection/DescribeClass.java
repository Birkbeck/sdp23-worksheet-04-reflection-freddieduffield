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

        printClassInfo(klass);

        System.out.print("{ ");
        System.out.println();

        System.out.println("    // fields -----------------------");
        Arrays.stream(klass.getFields()).forEach(DescribeClass::printFieldInfo);
        System.out.println("    // constructors -----------------------");
        Arrays.stream(klass.getConstructors()).forEach(DescribeClass::printConstructorInfo);
        System.out.println("    // methods -----------------------");
        Arrays.stream(klass.getMethods()).forEach(DescribeClass::printMethodInfo);

        System.out.print("}");
    }

    static void printClassInfo(Class<?> klass) {
        System.out.print(getModifiers(klass) + " ");
        if (klass.isInterface()) {
            System.out.print("interface ");
        } else {
            System.out.print("class ");
        }
        System.out.print(getFinalValue(klass.getName()) + " ");
    }


    static void printConstructorInfo(Constructor<?> constructor) {
        System.out.print("    ");
        System.out.print(getModifiers(constructor) + " ");
        System.out.print(getFinalValue(constructor.getName()) + " ");
        System.out.print(getParams(constructor) + " ");
        System.out.println();
    }

    static void printFieldInfo(Field field) {
        System.out.print("    ");
        System.out.print(getModifiers(field) + " ");
        System.out.print(getFinalValue(field.getType().getName()) + " ");
        System.out.print(getFinalValue(field.getName()));
        System.out.println();
    }


    static void printMethodInfo(Method method) {
        System.out.print("    ");
        System.out.print(getModifiers(method) + " ");
        System.out.print(getFinalValue( method.getReturnType().toString() ) + " ");
        System.out.print(method.getName() + " ");
        System.out.print(getParams(method));
        System.out.println();
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

