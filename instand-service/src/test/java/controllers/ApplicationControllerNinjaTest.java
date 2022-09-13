package controllers;

import ninja.NinjaTest;
import ninja.Result;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ApplicationControllerNinjaTest extends NinjaTest {

    @Test
    public void testConfigShouldRenderStageWithBeta() throws Exception {
        ApplicationController controller = getInjector().getInstance(ApplicationController.class);
        Result config = controller.config();
        Map renderable = (Map)config.getRenderable();
        assertThat(renderable.get("stage"), is("beta")); // NinjaTest runs under "test" mode, which should map to "beta" stage
    }
}