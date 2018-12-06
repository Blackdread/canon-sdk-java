package org.blackdread.cameraframework.api.command.builder;

import org.blackdread.cameraframework.api.constant.EdsSaveTo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * <p>Created on 2018/12/06.</p>
 *
 * @author Yoann CAPLAIN
 */
class ShootOptionBuilderTest {

    private ShootOptionBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new ShootOptionBuilder();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void defaultBuilderIsValid() {
        final ShootOption shootOption = builder.build();

        Assertions.assertNotNull(shootOption);
    }

    @Test
    void defaultValuesAreUnChanged() {
        final ShootOption shootOption = builder.build();

        Assertions.assertEquals(5, shootOption.getShootAttemptCount());
        Assertions.assertEquals(200, shootOption.getBusyWaitMillis());
        Assertions.assertFalse(shootOption.isCheckLiveViewState());
        Assertions.assertFalse(shootOption.isFetchEvents());
        Assertions.assertTrue(shootOption.isShootWithAF());
        Assertions.assertTrue(shootOption.isShootWithNoAF());
        Assertions.assertTrue(shootOption.isShootWithV0());
        Assertions.assertTrue(shootOption.isWaitForItemDownloadEvent());

        Assertions.assertFalse(shootOption.getFilename().isPresent());
        Assertions.assertFalse(shootOption.getFolderDestination().isPresent());

        Assertions.assertTrue(shootOption.getSaveTo().isPresent());
        Assertions.assertEquals(EdsSaveTo.kEdsSaveTo_Both, shootOption.getSaveTo().get());
    }

    @Test
    void builderIsReusable() {
        final ShootOption shootOption = builder.build();
        final ShootOption shootOption2 = builder.build();

        Assertions.assertNotNull(shootOption);
        Assertions.assertNotNull(shootOption2);
        Assertions.assertNotSame(shootOption, shootOption2);
        Assertions.assertEquals(shootOption, shootOption2);
        Assertions.assertEquals(shootOption.hashCode(), shootOption2.hashCode());
    }

    @Disabled("Get PreconditionViolationException, why? You must configure at least one set of arguments for this @ParameterizedTest")
    @ParameterizedTest
    @MethodSource("differentBuilder")
    void shootOptionDefineProperEqualsHashCode(ShootOptionBuilder optionBuilder) {
        final ShootOption shootOption = optionBuilder.build();
        final ShootOption shootOption2 = optionBuilder.build();

        Assertions.assertNotNull(shootOption);
        Assertions.assertNotSame(shootOption, shootOption2);
        Assertions.assertEquals(shootOption, shootOption2);
        Assertions.assertEquals(shootOption.hashCode(), shootOption2.hashCode());
    }

    @Test
    void throwsWhenShootAttemptCountIsLessThan1() {
        builder.setShootAttemptCount(0);

        Assertions.assertThrows(IllegalArgumentException.class, () -> builder.build());
    }

    @Test
    void throwsWhenNoShootOptionIsSelected() {
        builder
            .setShootWithV0(false)
            .setShootWithAF(false)
            .setShootWithNoAF(false);

        Assertions.assertThrows(IllegalStateException.class, () -> builder.build());
    }

    @Test
    void throwsWhenBusyWaitMillisIsLessThan1() {
        builder.setBusyWaitMillis(0);
        Assertions.assertThrows(IllegalArgumentException.class, () -> builder.build());

        builder.setBusyWaitMillis(-1);
        Assertions.assertThrows(IllegalArgumentException.class, () -> builder.build());
    }

    @Test
    void fetchEventToTrueAlsoChangeOther() {
        builder.setWaitForItemDownloadEvent(false);
        builder.setFetchEvents(true);
        final ShootOption shootOption = builder.build();

        Assertions.assertTrue(shootOption.isWaitForItemDownloadEvent());
    }

    @Test
    void fetchEventToTrueButNotWaitThrows() {
        builder.setFetchEvents(true);
        builder.setWaitForItemDownloadEvent(false);

        Assertions.assertThrows(IllegalStateException.class, () -> builder.build());
    }

    static Stream<Arguments> differentBuilder() {
        return Stream.of(
            arguments(new ShootOptionBuilder()),
            arguments(new ShootOptionBuilder()
                .setBusyWaitMillis(956)),
            arguments(new ShootOptionBuilder()
                .setCheckLiveViewState(true)),
            arguments(new ShootOptionBuilder()
                .setFolderDestination(new File("folder/subFolder"))),
            arguments(new ShootOptionBuilder()
                .setFilename("myFileName")),
            arguments(new ShootOptionBuilder()
                .setSaveTo(EdsSaveTo.kEdsSaveTo_Camera)),
            arguments(new ShootOptionBuilder()
                .setShootWithAF(true)),
            arguments(new ShootOptionBuilder()
                .setShootWithNoAF(true)),
            arguments(new ShootOptionBuilder()
                .setWaitForItemDownloadEvent(true)),
            arguments(new ShootOptionBuilder()
                .setBusyWaitMillis(fail())
                .setWaitForItemDownloadEvent(false))
        );
    }
}
