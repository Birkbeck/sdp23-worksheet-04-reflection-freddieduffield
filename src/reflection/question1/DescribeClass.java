package reflection.question1;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DescribeClass {
    public static void main(String[] args) {
        if (args.length > 0) {
            System.out.println(new InterfaceConstructor(args[0]));
        }


    }

    private static class InterfaceConstructor {
        private final static String INDENT = "\t";
        private Class<?> c;

        public InterfaceConstructor(String className) {
            try {
                this.c = Class.forName(className);
            } catch (ClassNotFoundException e) {
                System.err.println("Unknown classname " + className + " supplied to the interface constructor");
            }
        }


        public String getFieldInformation() {
//            interface or class, modifiers, constructors, methods, fields
            if (c.getDeclaredFields().length == 0) return null;

            List<String> output = new ArrayList<>(Arrays.asList(INDENT + "// Field information", ""));
            for (Field fld : c.getDeclaredFields()) {
                output.add(joinArraysRemovingNullOutput(new String[] {
                        INDENT,
                        Modifier.toString(fld.getModifiers()),
                        String.valueOf(fld.getGenericType()),
                        fld.getName(),
                        ";"
                }, " "));
            }
            return String.join("\n", output);
        }

        private String joinArraysRemovingNullOutput(String[] strings, String delimiter) {
            return Arrays.stream(strings)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(delimiter));
        }

        @Override
        public String toString() {
            return getFieldInformation();
        }
    }
}
