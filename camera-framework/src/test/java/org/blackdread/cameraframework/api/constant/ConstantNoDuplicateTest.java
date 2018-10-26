package org.blackdread.cameraframework.api.constant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class ConstantNoDuplicateTest {

    private static final Logger log = LoggerFactory.getLogger(ConstantNoDuplicateTest.class);

    @Test
    void noDuplicateEnumName() {
        final Map<String, List<NativeEnum>> groupByNativeEnumName = ConstantUtil.getNativeEnums().stream()
            .collect(Collectors.groupingBy(NativeEnum::name));
        final Map<String, List<NativeEnum>> duplicateNames = groupByNativeEnumName.values().stream()
            .filter(list -> list.size() > 1)
            .flatMap(Collection::stream)
            .collect(Collectors.groupingBy(NativeEnum::name));
        if (!duplicateNames.isEmpty()) {
            log.error("Duplicate enum name found: {}", duplicateNames);
            Assertions.fail("Duplicate enum name found: " + String.join(",", duplicateNames.keySet()));
        }
    }

}
