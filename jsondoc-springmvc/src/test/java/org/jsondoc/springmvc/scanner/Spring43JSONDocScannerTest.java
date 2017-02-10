package org.jsondoc.springmvc.scanner;

import org.junit.Test;
import org.springframework.web.bind.annotation.*;

import static java.util.Arrays.asList;

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
        scanner.jsondocMethods(Controller.class).containsAll(asList(
                RequestMapping.class,
                GetMapping.class,
                PostMapping.class,
                PutMapping.class,
                PatchMapping.class,
                DeleteMapping.class));

    }

}