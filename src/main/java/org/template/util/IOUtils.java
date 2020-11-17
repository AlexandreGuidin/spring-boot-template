package org.template.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.stream.Collectors;

public class IOUtils {

    private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);

    private IOUtils() {
    }

    public static Optional<String> requestBodyString(HttpServletRequest req) {
        return Optional.ofNullable(req)
                .map(IOUtils::inputStreamFromRequest)
                .map(IOUtils::inputStreamToString);
    }

    public static ServletInputStream inputStreamFromRequest(HttpServletRequest req) {
        try {
            return req.getInputStream();
        } catch (Exception e) {
            logger.error("IOUtils.getFromReq error", e);
            return null;
        }
    }

    public static String inputStreamToString(InputStream inputStream) {
        try {
            return new BufferedReader(new InputStreamReader(inputStream))
                    .lines()
                    .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            logger.error("IOUtils.inputStreamToString error", e);
            return null;
        }
    }
}
