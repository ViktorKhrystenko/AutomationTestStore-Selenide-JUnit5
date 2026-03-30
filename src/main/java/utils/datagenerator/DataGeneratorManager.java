package utils.datagenerator;

public class DataGeneratorManager {
    private static ThreadLocal<DataGenerator> threadLocalDataGenerator = new ThreadLocal<>();


    public static DataGenerator getDataGenerator() {
        return threadLocalDataGenerator.get();
    }

    public static void setDataGenerator(DataGenerator dataGenerator) {
        threadLocalDataGenerator.set(dataGenerator);
    }

    public static void clear() {
        threadLocalDataGenerator.remove();
    }
}
