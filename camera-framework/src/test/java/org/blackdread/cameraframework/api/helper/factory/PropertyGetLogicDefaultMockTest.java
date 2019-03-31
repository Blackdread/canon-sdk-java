package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.Memory;
import com.sun.jna.NativeLong;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import org.blackdread.cameraframework.AbstractMockTest;
import org.blackdread.cameraframework.api.constant.EdsDataType;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.logic.PropertyInfo;
import org.blackdread.cameraframework.exception.error.device.EdsdkDeviceInvalidErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * <p>Created on 2019/03/31.</p>
 *
 * @author Yoann CAPLAIN
 */
class PropertyGetLogicDefaultMockTest extends AbstractMockTest {

    private EdsBaseRef fakeBaseRef;

    private Memory mockMemory;

    private PropertyGetLogicDefaultExtended propertyGetLogicDefaultExtended;

    private PropertyInfo propertyInfo;

    @BeforeEach
    void setUp() {
        mockEdsdkLibrary();

        fakeBaseRef = new EdsBaseRef();

        mockMemory = Mockito.mock(Memory.class);

        propertyGetLogicDefaultExtended = new PropertyGetLogicDefaultExtended();

        propertyGetLogicDefaultExtended.setMemory(mockMemory);

        propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_Int32, 4);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getPropertyDataLong() {
        // we throw quickly so real full test is done in getPropertyData() methods of test
        when(CanonFactory.propertyLogic().getPropertyTypeAndSize(fakeBaseRef, EdsPropertyID.kEdsPropID_ISOSpeed, 0L)).thenReturn(propertyInfo);

        when(CanonFactory.edsdkLibrary().EdsGetPropertyData(eq(fakeBaseRef), eq(new NativeLong(EdsPropertyID.kEdsPropID_ISOSpeed.value())), eq(new NativeLong(0)), eq(new NativeLong(4)), eq(mockMemory))).thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()));

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> propertyGetLogicDefaultExtended.getPropertyDataLong(fakeBaseRef, EdsPropertyID.kEdsPropID_ISOSpeed));
    }

    @Test
    void getPropertyDataLong1() {
        // we throw quickly so real full test is done in getPropertyData() methods of test
        when(CanonFactory.propertyLogic().getPropertyTypeAndSize(fakeBaseRef, EdsPropertyID.kEdsPropID_ISOSpeed, 1L)).thenReturn(propertyInfo);

        when(CanonFactory.edsdkLibrary().EdsGetPropertyData(eq(fakeBaseRef), eq(new NativeLong(EdsPropertyID.kEdsPropID_ISOSpeed.value())), eq(new NativeLong(1L)), eq(new NativeLong(4)), eq(mockMemory))).thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()));

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> propertyGetLogicDefaultExtended.getPropertyDataLong(fakeBaseRef, EdsPropertyID.kEdsPropID_ISOSpeed, 1L));
    }

    @Test
    void getPropertyDataForInt32() {
        final Long expectedResult = 5L;

        final EdsPropertyID propertyID = EdsPropertyID.kEdsPropID_ISOSpeed;
        final long inParam = 0L;
        final int inPropertySize = 4;

        propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_Int32, inPropertySize);

        // mocks

        when(CanonFactory.propertyLogic().getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam)).thenReturn(propertyInfo);

        retunNoErrorForEdsGetPropertyData(EdsPropertyID.kEdsPropID_ISOSpeed, inParam, inPropertySize);

        // mock actual result
        when(mockMemory.getNativeLong(0)).thenReturn(new NativeLong(expectedResult));

        final Long result = propertyGetLogicDefaultExtended.getPropertyData(fakeBaseRef, propertyID);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getPropertyDataForUInt32() {
        final Long expectedResult = 5L;

        final EdsPropertyID propertyID = EdsPropertyID.kEdsPropID_ISOSpeed;
        final long inParam = 0L;
        final int inPropertySize = 4;

        propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_UInt32, inPropertySize);

        // mocks

        when(CanonFactory.propertyLogic().getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam)).thenReturn(propertyInfo);

        retunNoErrorForEdsGetPropertyData(EdsPropertyID.kEdsPropID_ISOSpeed, inParam, inPropertySize);

        // mock actual result
        when(mockMemory.getNativeLong(0)).thenReturn(new NativeLong(expectedResult));

        final Long result = propertyGetLogicDefaultExtended.getPropertyData(fakeBaseRef, propertyID);

        Assertions.assertEquals(expectedResult, result);
    }

    private void retunNoErrorForEdsGetPropertyData(final EdsPropertyID propertyID, final long inParam, final int inPropertySize) {
        when(CanonFactory.edsdkLibrary().EdsGetPropertyData(eq(fakeBaseRef), eq(new NativeLong(propertyID.value())), eq(new NativeLong(inParam)), eq(new NativeLong(inPropertySize)), eq(mockMemory))).thenReturn(new NativeLong(0));
    }

    @Test
    void getPropertyDataAllData() {
        when(CanonFactory.edsdkLibrary().EdsGetPropertyData(eq(fakeBaseRef), eq(new NativeLong(EdsPropertyID.kEdsPropID_ISOSpeed.value())), eq(new NativeLong(0)), eq(new NativeLong(4)), eq(mockMemory))).thenReturn(new NativeLong(0));

        final EdsdkError edsdkError = propertyGetLogicDefaultExtended.getPropertyData(fakeBaseRef, EdsPropertyID.kEdsPropID_ISOSpeed, 0L, 4L, mockMemory);

        Assertions.assertEquals(EdsdkError.EDS_ERR_OK, edsdkError);

        verify(CanonFactory.edsdkLibrary()).EdsGetPropertyData(eq(fakeBaseRef), eq(new NativeLong(EdsPropertyID.kEdsPropID_ISOSpeed.value())), eq(new NativeLong(0)), eq(new NativeLong(4)), eq(mockMemory));
    }
}
