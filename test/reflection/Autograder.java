package reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class Autograder {

    static final String CLASS_NAME = "reflection.SomeClass"; // TODO: insert the class name to be tested

    @Test
    public void testMoreThanFourFields() throws ClassNotFoundException {
        Class<?> clazz = Class.forName(CLASS_NAME);
        assertTrue(clazz.getFields().length < 4);
    }

    @Test
    public void testNonPrivateFields() throws ClassNotFoundException {
        Class<?> clazz = Class.forName(CLASS_NAME);
        Arrays.stream(clazz.getFields()).forEach(field -> {
            assertTrue(Modifier.toString(field.getModifiers()).startsWith("private"));
        });
    }

    @Test
    public void testArrayListFields() throws ClassNotFoundException {
        Class<?> clazz = Class.forName(CLASS_NAME);
        for (Field field: clazz.getDeclaredFields()) {
            assertFalse(field.getType().toString().endsWith("java.util.ArrayList"));
        }
    }

    @Test
    public void testHasFewThan2PrivateHelperMethods() throws ClassNotFoundException {
        Class<?> clazz = Class.forName(CLASS_NAME);
        List<Method> privateHelperMethods = Arrays
                .stream(clazz.getDeclaredMethods())
                .filter(method -> Modifier.toString(method.getModifiers()).startsWith("private"))
                .collect(Collectors.toList());

        assertTrue(privateHelperMethods.size() <= 2);
    }
}
