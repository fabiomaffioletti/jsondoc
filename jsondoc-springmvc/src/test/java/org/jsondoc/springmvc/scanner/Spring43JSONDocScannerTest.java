package org.jsondoc.springmvc.scanner;

import org.junit.Test;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class Spring43JSONDocScannerTest {


    class Controller {
        @GetMapping
        public void get() {

        }

        @PostMapping
        public void post() {

        }

        @PutMapping
        public void put() {

        }

        @DeleteMapping
        public void delete() {

        }

        @PatchMapping
        public void path() {

        }

        @RequestMapping
        public void requestMapping() {

        }
    }

    @Test
    public void jsondocMethods() throws Exception {
        Spring43JSONDocScanner scanner = new Spring43JSONDocScanner();
        final Set<Method> methods = scanner.jsondocMethods(Controller.class);
        assertTrue(methods.contains(Controller.class.getMethod("requestMapping")));
        assertTrue(methods.contains(Controller.class.getMethod("get")));
        assertTrue(methods.contains(Controller.class.getMethod("post")));
        assertTrue(methods.contains(Controller.class.getMethod("path")));
        assertTrue(methods.contains(Controller.class.getMethod("put")));
        assertTrue(methods.contains(Controller.class.getMethod("delete")));
    }

}