package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class InstantiateClass {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (args.length < 1)
            System.out.println("Usage: "); // TODO: describe how to use the utility
//        args[] = classname and rest is a list of arguments for the constructor
        Class<?> clazz = Class.forName(args[0]);
        List<String> arguments = Arrays.stream(args, 1, args.length).collect(Collectors.toList());

        Optional<Constructor<?>> matchingConstructor = Arrays
                .stream(clazz.getConstructors())
                .filter(constructor1 -> matchesArguments(constructor1.getParameterTypes(), arguments))
                .findFirst();


        if(matchingConstructor.isEmpty()) {
            System.out.println("no matching constructor found");
        } else {
            Object instantiatedClass = matchingConstructor.get().newInstance(String.join(", ", arguments));
            System.out.println(instantiatedClass.getClass().getName());
        }



    }

    static boolean matchesArguments(Class<?>[] parameterTypes, List<String> arguments) {
        if (parameterTypes.length == arguments.size()) {
            AtomicBoolean matching = new AtomicBoolean(true);

            for (int i = 0; i < parameterTypes.length; i++) {
                if (!arguments.get(i).equals(parameterTypes[i].getTypeName())) {
                    matching.set(false);
                    break;
                }
            }

            return matching.get();
        }

        return false;
    }
}
