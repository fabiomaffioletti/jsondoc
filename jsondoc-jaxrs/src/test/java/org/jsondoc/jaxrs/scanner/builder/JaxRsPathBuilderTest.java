package org.jsondoc.jaxrs.scanner.builder;

import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.Path;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author Arne Bosien
 */
public class JaxRsPathBuilderTest {
    @Test
    public void testBuildPath_1() throws NoSuchMethodException {
        Method empty = Jax.class.getMethod("empty");
        Set<String> path = JaxRsPathBuilder.buildPath(empty);
        Assert.assertTrue(path.contains("/root"));
    }

    @Test
    public void testBuildPath_2() throws NoSuchMethodException {
        Method empty = Jax.class.getMethod("slash");
        Set<String> path = JaxRsPathBuilder.buildPath(empty);
        Assert.assertTrue(path.contains("/root/"));
    }

    @Test
    public void testBuildPath_3() throws NoSuchMethodException {
        Method empty = Jax.class.getMethod("name");
        Set<String> path = JaxRsPathBuilder.buildPath(empty);
        Assert.assertTrue(path.contains("/root/hello"));
    }

    @Test
    public void testBuildPath_4() throws NoSuchMethodException {
        Method empty = Jax.class.getMethod("name2");
        Set<String> path = JaxRsPathBuilder.buildPath(empty);
        Assert.assertTrue(path.contains("/root/hello"));
    }

    @Path("root")
    public class Jax {
        @Path("")
        public void empty() {
        }

        @Path("/")
        public void slash() {
        }

        @Path("/hello")
        public void name() {
        }

        @Path("hello")
        public void name2() {
        }
    }
}