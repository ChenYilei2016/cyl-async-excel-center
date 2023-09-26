package cn.chenyilei.aec.spi;

public interface LoadingStrategy {
    String directory();

    default boolean preferExtensionClassLoader() {
        return false;
    }

    default String[] excludedPackages() {
        return null;
    }
}
