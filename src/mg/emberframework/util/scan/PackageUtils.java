package mg.emberframework.util.scan;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import mg.emberframework.util.io.FileUtils;

public class PackageUtils {
    private PackageUtils() {
    }

    public static List<Method> getClassMethodsWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        List<Method> methods = new ArrayList<>();
        Method[] classMethods = clazz.getDeclaredMethods();

        for (Method method : classMethods) {
            if (method.isAnnotationPresent(annotation)) {
                methods.add(method);
            }
        }

        return methods;
    }

    public static List<Class<?>> getClasses(String packageName) throws ClassNotFoundException, IOException {
        List<Class<?>> classes = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');

        URL resource = classLoader.getResource(path);

        if (resource == null) {
            return classes;
        }

        File packageDir = new File(resource.getFile().replace("%20", " "));
        
        for (File file : packageDir.listFiles()) {
            if (file.isDirectory()) {
                classes.addAll(PackageUtils.getClasses(packageName + "." + file.getName()));
            } else {
                String className = packageName + "." + FileUtils.getSimpleFileName(file.getName(), "class");
                classes.add(Class.forName(className));
            }
        }
        
        return classes;
    }

    public static List<Class<?>> getClassesWithAnnotation(String packageName, Class<? extends Annotation> annotationClass) throws ClassNotFoundException, IOException {
        List<Class<?>> classes = getClasses(packageName);

        ArrayList<Class<?>> result = new ArrayList<>();
        
        for(Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(annotationClass)) {
                result.add(clazz);
            }
        }

        return result;
    }

}
