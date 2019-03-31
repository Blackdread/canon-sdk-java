package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.NativeLong;
import org.blackdread.camerabinding.jna.EdsPropertyDesc;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.AbstractMockTest;
import org.blackdread.cameraframework.MockFactory;
import org.blackdread.cameraframework.api.constant.EdsISOSpeed;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.NativeEnum;
import org.blackdread.cameraframework.api.helper.logic.PropertyDescLogic;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

/**
 * <p>Created on 2019/03/31.</p>
 *
 * @author Yoann CAPLAIN
 */
class PropertyDescLogicDefaultMockTest extends AbstractMockTest {

    private EdsCameraRef fakeCamera;

    private PropertyDescLogic spyPropertyDescLogic;

    private PropertyDescLogicDefaultExtended propertyDescLogicDefaultExtended;

    @BeforeEach
    void setUp() {
        mockEdsdkLibrary();

        fakeCamera = new EdsCameraRef();

        propertyDescLogicDefaultExtended = new PropertyDescLogicDefaultExtended();

        spyPropertyDescLogic = Mockito.spy(MockFactory.initialCanonFactory.getPropertyDescLogic());

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getPropertyDesc() {
        final NativeLong[] desc = new NativeLong[128];
        desc[0] = new NativeLong(EdsISOSpeed.kEdsISOSpeed_50.value());
        desc[1] = new NativeLong(EdsISOSpeed.kEdsISOSpeed_100.value());
        desc[2] = new NativeLong(EdsISOSpeed.kEdsISOSpeed_160.value());
        desc[3] = new NativeLong(EdsISOSpeed.kEdsISOSpeed_200.value());
        final EdsPropertyDesc propertyDesc = new EdsPropertyDesc(
            new NativeLong(0),
            new NativeLong(0),
            new NativeLong(4),
            desc
        );

        propertyDescLogicDefaultExtended.setPropertyDescStructure(propertyDesc);

        final List<NativeEnum<Integer>> nativeEnums = propertyDescLogicDefaultExtended.getPropertyDesc(fakeCamera, EdsPropertyID.kEdsPropID_ISOSpeed);

        Assertions.assertEquals(4, nativeEnums.size());
        Assertions.assertEquals(EdsISOSpeed.kEdsISOSpeed_50, nativeEnums.get(0));
        Assertions.assertEquals(EdsISOSpeed.kEdsISOSpeed_200, nativeEnums.get(3));
    }

    @Test
    void getPropertyDescColorTemperature() {
        final NativeLong[] desc = new NativeLong[128];
        desc[0] = new NativeLong(100);
        desc[1] = new NativeLong(1000);
        desc[2] = new NativeLong(2000);
        desc[3] = new NativeLong(3000);
        desc[4] = new NativeLong(6500);
        final EdsPropertyDesc propertyDesc = new EdsPropertyDesc(
            new NativeLong(0),
            new NativeLong(0),
            new NativeLong(5),
            desc
        );

        propertyDescLogicDefaultExtended.setPropertyDescStructure(propertyDesc);

        final List<Integer> propertyDescColorTemperature = propertyDescLogicDefaultExtended.getPropertyDescColorTemperature(fakeCamera);

        Assertions.assertEquals(5, propertyDescColorTemperature.size());
        Assertions.assertEquals(100, propertyDescColorTemperature.get(0));
        Assertions.assertEquals(6500, propertyDescColorTemperature.get(4));
    }

    @Test
    void getPropertyDescEvfColorTemperature() {
        final NativeLong[] desc = new NativeLong[128];
        desc[0] = new NativeLong(100);
        desc[1] = new NativeLong(1000);
        desc[2] = new NativeLong(2000);
        desc[3] = new NativeLong(3000);
        final EdsPropertyDesc propertyDesc = new EdsPropertyDesc(
            new NativeLong(0),
            new NativeLong(0),
            new NativeLong(4),
            desc
        );

        propertyDescLogicDefaultExtended.setPropertyDescStructure(propertyDesc);

        final List<Integer> propertyDescColorTemperature = propertyDescLogicDefaultExtended.getPropertyDescEvfColorTemperature(fakeCamera);

        Assertions.assertEquals(4, propertyDescColorTemperature.size());
        Assertions.assertEquals(100, propertyDescColorTemperature.get(0));
        Assertions.assertEquals(3000, propertyDescColorTemperature.get(3));
    }

    @Test
    void getPropertyDescValues() {
        final NativeLong[] desc = new NativeLong[128];
        final EdsPropertyDesc propertyDesc = new EdsPropertyDesc(
            new NativeLong(0),
            new NativeLong(0),
            new NativeLong(0),
            desc
        );

        propertyDescLogicDefaultExtended.setPropertyDescStructure(propertyDesc);

        final List<Integer> propertyDescValues = propertyDescLogicDefaultExtended.getPropertyDescValues(fakeCamera, EdsPropertyID.kEdsPropID_ISOSpeed);

        Assertions.assertEquals(0, propertyDescValues.size());
    }

    @Test
    void getPropertyDescStructure() {
    }


//        when(CanonFactory.getCanonFactory().getPropertyDescLogic()).thenReturn(propertyDescLogicDefaultExtended);

//        when(propertyDescLogic.getPropertyDescStructure(any(), any())).thenReturn(propertyDesc);

//        final List<Integer> propertyDescColorTemperature = spyPropertyDescLogic.getPropertyDescColorTemperature(fakeCamera);
//        final List<Integer> propertyDescColorTemperature = CanonFactory.getCanonFactory().getPropertyDescLogic().getPropertyDescColorTemperature(fakeCamera);

}
