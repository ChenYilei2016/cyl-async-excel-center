package cn.chenyilei.aec.spi.kernel.function;

/**
 * 无参数和返回的函数对象<br>MyCharUtils
 */
@FunctionalInterface
public interface VoidCallback {

    /**
     * 执行
     */
    void call();
}
