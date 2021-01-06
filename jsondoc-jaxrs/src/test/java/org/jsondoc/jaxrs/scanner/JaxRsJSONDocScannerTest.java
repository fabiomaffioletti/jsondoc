package org.jsondoc.jaxrs.scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsondoc.core.pojo.JSONDoc;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Arne Bosien
 */
public class JaxRsJSONDocScannerTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(JaxRsJSONDocScannerTest.class);

    String version = "1.0";
    String basePath = "http://localhost/rest";
    List<String> packages = Arrays.asList("org.jsondoc.jaxrs.test");
    boolean playgroundEnabled = true;
    JSONDoc.MethodDisplay displayMethodAs = JSONDoc.MethodDisplay.URI;

    @Test
    public void t() throws IOException {
        JaxRsJSONDocScanner sut = new JaxRsJSONDocScanner();
        JSONDoc jsonDoc = sut.getJSONDoc(version, basePath, packages, playgroundEnabled, displayMethodAs);
        String s = MAPPER.writeValueAsString(jsonDoc);
        LOGGER.info(s);
    }
}