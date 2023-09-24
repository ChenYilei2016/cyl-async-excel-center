package cn.chenyilei.center.spi.compiler.support;

import cn.chenyilei.center.spi.ExtensionFactory;
import cn.chenyilei.center.spi.ExtensionLoader;
import cn.chenyilei.center.spi.compiler.Compiler;
import cn.chenyilei.center.spi.kernel.URL;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author chenyilei
 * @date 2023/09/24 16:14
 */
public class JavassistCompilerTest {

    @Test
    public void testCode(){
        System.err.println("1");

        Compiler defaultExtension = ExtensionLoader.getExtensionLoader(Compiler.class).getDefaultExtension();

        System.err.println(defaultExtension);

        defaultExtension = ExtensionLoader.getExtensionLoader(Compiler.class).getAdaptiveExtension();

        System.err.println(defaultExtension);
    }


    @Test
    public void activate(){
        URL url = new URL(new HashMap<>());
        List<Compiler> activateExtension = ExtensionLoader.getExtensionLoader(Compiler.class)
                .getActivateExtension(url, "");
        System.err.println(activateExtension);

        for (Compiler compiler : activateExtension) {

        }
    }
}