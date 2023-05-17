package tv.spring;

import tv.aop.ProxyFactory;
import tv.util.CastUtil;
import tv.util.Logger;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 应用程序上下文
 *
 * @author 刘家辉
 * @date 2023/05/15
 */
@SuppressWarnings("all")
public class ApplicationContext {
    private Class configClass;
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionConcurrentHashMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();

    public ApplicationContext(Class configClass) {
        this.configClass = configClass;
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScan = CastUtil.cast(configClass.getAnnotation(ComponentScan.class));
            String[] paths = componentScan.value();
            //扫描路径
            for (String path : paths) {

                path = path.replace(".", "/");
                System.out.println(path);

                ClassLoader classLoader = ApplicationContext.class.getClassLoader();
                URL resource = classLoader.getResource(path);
                System.out.println(resource.getPath());
                assert resource != null;
                File file = new File(resource.getFile());
                if (file.isDirectory()) {
                    File[] files = file.listFiles();
                    assert files != null;
                    for (File f : files) {
                        String fileName = f.getAbsolutePath();
                        if (fileName.endsWith(".class")) {
                            String className = fileName.substring(fileName.indexOf("tv"), fileName.indexOf(".class"));
                            className = className.replace("\\", ".");
                            try {
                                Class<?> aClass = classLoader.loadClass(className);
                                if (aClass.isAnnotationPresent(Component.class)) {
                                    Component annotation = aClass.getAnnotation(Component.class);
                                    String beanName = annotation.value();

                                    if (beanName.equals("")) {
                                        beanName = Introspector.decapitalize(aClass.getSimpleName());
                                    }


                                    //BeanDefinition
                                    BeanDefinition beanDefinition = new BeanDefinition();
                                    beanDefinition.setType(aClass);
                                    if (aClass.isAnnotationPresent(Scope.class)) {
                                        Scope scope = aClass.getAnnotation(Scope.class);
                                        beanDefinition.setScope(scope.value());
                                    } else {
                                        beanDefinition.setScope("singleton");
                                    }

                                    beanDefinitionConcurrentHashMap.put(beanName, beanDefinition);
                                }
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

        }
        String[] necessaryBeanNames = {"connectionPool","proxyFactory","dao"};
         for (String beanName : necessaryBeanNames) {
             BeanDefinition beanDefinition = beanDefinitionConcurrentHashMap.get(beanName);
             Object bean = createBean(beanName, beanDefinition);
             System.out.println(beanName + " " + bean);
             singletonObjects.put(beanName, bean);
        }
        for (String beanName : beanDefinitionConcurrentHashMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionConcurrentHashMap.get(beanName);
            if (beanDefinition.getScope().equals("singleton")&&beanDefinition.getType().isAnnotationPresent(ServiceLogger.class)&&(!beanName.equals("connectionPool"))&&(!beanName.equals("proxyFactory"))) {
                Object bean = createBean(beanName, beanDefinition);
                System.out.println(beanName + " " + bean);
                singletonObjects.put(beanName, bean);
            }

        }

        for (String beanName : beanDefinitionConcurrentHashMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionConcurrentHashMap.get(beanName);
            if (beanDefinition.getScope().equals("singleton")&&!beanDefinition.getType().isAnnotationPresent(ServiceLogger.class)&&(!beanName.equals("connectionPool"))&&(!beanName.equals("proxyFactory"))) {
                Object bean = createBean(beanName, beanDefinition);
                System.out.println(beanName + " " + bean);
                singletonObjects.put(beanName, bean);
            }

        }
    }

    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        Class type = beanDefinition.getType();
        try {
            Object instance = type.getConstructor().newInstance();


            for (Field field : type.getDeclaredFields()) {
                if (field.isAnnotationPresent(AutoWired.class)) {
                    field.setAccessible(true);
                    field.set(instance, getBean(field.getName()));
                }

            }
            if(type.isAnnotationPresent(ServiceLogger.class)){
                instance=((ProxyFactory)singletonObjects.get("proxyFactory")).serviceProxy(instance);
                Logger.info("serviceProxy");
            }else if(type.isAnnotationPresent(CommonLogger.class)){
                instance=((ProxyFactory)singletonObjects.get("proxyFactory")).commonProxy(instance);
                Logger.info("commonProxy");
            }
            System.out.println(type.isAnnotationPresent(ServiceLogger.class));
            return instance;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionConcurrentHashMap.get(beanName);
        if (beanDefinition == null) {
            throw new NullPointerException();
        } else {
            String scope = beanDefinition.getScope();
            if (scope.equals("singleton")) {
                Object bean = singletonObjects.get(beanName);
                if (bean == null) {
                    Object beanTwo = createBean(beanName, beanDefinition);
                    singletonObjects.put(beanName, beanTwo);
                }
                return bean;
            } else {
                return createBean(beanName, beanDefinition);
            }
        }
    }
}
