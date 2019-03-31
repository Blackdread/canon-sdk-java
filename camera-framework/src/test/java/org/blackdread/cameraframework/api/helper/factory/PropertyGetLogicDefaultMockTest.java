package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.Memory;
import com.sun.jna.NativeLong;
import org.apache.commons.lang3.NotImplementedException;
import org.blackdread.camerabinding.jna.EdsRational;
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
import org.junit.jupiter.api.Disabled;
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
    void getPropertyDataForUnknown() {
        final Long expectedResult = 5L;

        final EdsPropertyID propertyID = EdsPropertyID.kEdsPropID_ISOSpeed;
        final long inParam = 0L;
        final int inPropertySize = 4;

        propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_Unknown, inPropertySize);

        // mocks

        when(CanonFactory.propertyLogic().getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam)).thenReturn(propertyInfo);

        returnNoErrorForEdsGetPropertyData(propertyID, inParam, inPropertySize);

        // mock actual result
        when(mockMemory.getNativeLong(0)).thenReturn(new NativeLong(expectedResult));

        Assertions.assertThrows(IllegalStateException.class, () -> propertyGetLogicDefaultExtended.getPropertyData(fakeBaseRef, propertyID));

        verify(CanonFactory.propertyLogic()).getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam);

        verify(CanonFactory.edsdkLibrary()).EdsGetPropertyData(eq(fakeBaseRef), eq(new NativeLong(propertyID.value())), eq(new NativeLong(inParam)), eq(new NativeLong(inPropertySize)), eq(mockMemory));
    }

    @Test
    void getPropertyDataForBool() {
        final Boolean expectedResult = true;

        final EdsPropertyID propertyID = EdsPropertyID.kEdsPropID_ISOSpeed;
        final long inParam = 0L;
        final int inPropertySize = 4;

        propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_Bool, inPropertySize);

        // mocks

        when(CanonFactory.propertyLogic().getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam)).thenReturn(propertyInfo);

        returnNoErrorForEdsGetPropertyData(propertyID, inParam, inPropertySize);

        // mock actual result
        when(mockMemory.getByte(0)).thenReturn((byte) 1);

        final Boolean result = propertyGetLogicDefaultExtended.getPropertyData(fakeBaseRef, propertyID);

        Assertions.assertEquals(expectedResult, result);

        verify(CanonFactory.propertyLogic()).getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam);

        verify(CanonFactory.edsdkLibrary()).EdsGetPropertyData(eq(fakeBaseRef), eq(new NativeLong(propertyID.value())), eq(new NativeLong(inParam)), eq(new NativeLong(inPropertySize)), eq(mockMemory));
    }

    @Test
    void getPropertyDataForString() {
        final String expectedResult = "result";

        final EdsPropertyID propertyID = EdsPropertyID.kEdsPropID_ISOSpeed;
        final long inParam = 0L;
        final int inPropertySize = 4;

        propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_String, inPropertySize);

        // mocks

        when(CanonFactory.propertyLogic().getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam)).thenReturn(propertyInfo);

        returnNoErrorForEdsGetPropertyData(propertyID, inParam, inPropertySize);

        // mock actual result
        when(mockMemory.getString(0)).thenReturn(expectedResult);

        final String result = propertyGetLogicDefaultExtended.getPropertyData(fakeBaseRef, propertyID);

        Assertions.assertEquals(expectedResult, result);

        verify(CanonFactory.propertyLogic()).getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam);

        verify(CanonFactory.edsdkLibrary()).EdsGetPropertyData(eq(fakeBaseRef), eq(new NativeLong(propertyID.value())), eq(new NativeLong(inParam)), eq(new NativeLong(inPropertySize)), eq(mockMemory));
    }

    @Test
    void getPropertyDataForInt8() {
        final Byte expectedResult = 5;

        final EdsPropertyID propertyID = EdsPropertyID.kEdsPropID_ISOSpeed;
        final long inParam = 0L;
        final int inPropertySize = 4;

        propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_Int8, inPropertySize);

        // mocks

        when(CanonFactory.propertyLogic().getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam)).thenReturn(propertyInfo);

        returnNoErrorForEdsGetPropertyData(propertyID, inParam, inPropertySize);

        // mock actual result
        when(mockMemory.getByte(0)).thenReturn(expectedResult);

        final Byte result = propertyGetLogicDefaultExtended.getPropertyData(fakeBaseRef, propertyID);

        Assertions.assertEquals(expectedResult, result);

        verify(CanonFactory.propertyLogic()).getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam);

        verify(CanonFactory.edsdkLibrary()).EdsGetPropertyData(eq(fakeBaseRef), eq(new NativeLong(propertyID.value())), eq(new NativeLong(inParam)), eq(new NativeLong(inPropertySize)), eq(mockMemory));
    }

    @Test
    void getPropertyDataForInt16() {
        final Short expectedResult = 5;

        final EdsPropertyID propertyID = EdsPropertyID.kEdsPropID_ISOSpeed;
        final long inParam = 0L;
        final int inPropertySize = 4;

        propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_Int16, inPropertySize);

        // mocks

        when(CanonFactory.propertyLogic().getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam)).thenReturn(propertyInfo);

        returnNoErrorForEdsGetPropertyData(propertyID, inParam, inPropertySize);

        // mock actual result
        when(mockMemory.getShort(0)).thenReturn(expectedResult);

        final Short result = propertyGetLogicDefaultExtended.getPropertyData(fakeBaseRef, propertyID);

        Assertions.assertEquals(expectedResult, result);

        verify(CanonFactory.propertyLogic()).getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam);

        verify(CanonFactory.edsdkLibrary()).EdsGetPropertyData(eq(fakeBaseRef), eq(new NativeLong(propertyID.value())), eq(new NativeLong(inParam)), eq(new NativeLong(inPropertySize)), eq(mockMemory));
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

        returnNoErrorForEdsGetPropertyData(propertyID, inParam, inPropertySize);

        // mock actual result
        when(mockMemory.getNativeLong(0)).thenReturn(new NativeLong(expectedResult));

        final Long result = propertyGetLogicDefaultExtended.getPropertyData(fakeBaseRef, propertyID);

        Assertions.assertEquals(expectedResult, result);

        verify(CanonFactory.propertyLogic()).getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam);

        verify(CanonFactory.edsdkLibrary()).EdsGetPropertyData(eq(fakeBaseRef), eq(new NativeLong(propertyID.value())), eq(new NativeLong(inParam)), eq(new NativeLong(inPropertySize)), eq(mockMemory));
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

        returnNoErrorForEdsGetPropertyData(propertyID, inParam, inPropertySize);

        // mock actual result
        when(mockMemory.getNativeLong(0)).thenReturn(new NativeLong(expectedResult));

        final Long result = propertyGetLogicDefaultExtended.getPropertyData(fakeBaseRef, propertyID);

        Assertions.assertEquals(expectedResult, result);

        verify(CanonFactory.propertyLogic()).getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam);

        verify(CanonFactory.edsdkLibrary()).EdsGetPropertyData(eq(fakeBaseRef), eq(new NativeLong(propertyID.value())), eq(new NativeLong(inParam)), eq(new NativeLong(inPropertySize)), eq(mockMemory));
    }

    @Test
    void getPropertyDataForInt64() {
        final Long expectedResult = 5L;

        final EdsPropertyID propertyID = EdsPropertyID.kEdsPropID_ISOSpeed;
        final long inParam = 0L;
        final int inPropertySize = 4;

        propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_Int64, inPropertySize);

        // mocks

        when(CanonFactory.propertyLogic().getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam)).thenReturn(propertyInfo);

        returnNoErrorForEdsGetPropertyData(propertyID, inParam, inPropertySize);

        // mock actual result
        when(mockMemory.getLong(0)).thenReturn(expectedResult);

        final Long result = propertyGetLogicDefaultExtended.getPropertyData(fakeBaseRef, propertyID);

        Assertions.assertEquals(expectedResult, result);

        verify(CanonFactory.propertyLogic()).getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam);

        verify(CanonFactory.edsdkLibrary()).EdsGetPropertyData(eq(fakeBaseRef), eq(new NativeLong(propertyID.value())), eq(new NativeLong(inParam)), eq(new NativeLong(inPropertySize)), eq(mockMemory));
    }

    @Test
    void getPropertyDataForFloat() {
        final Float expectedResult = 5.06f;

        final EdsPropertyID propertyID = EdsPropertyID.kEdsPropID_ISOSpeed;
        final long inParam = 0L;
        final int inPropertySize = 4;

        propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_Float, inPropertySize);

        // mocks

        when(CanonFactory.propertyLogic().getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam)).thenReturn(propertyInfo);

        returnNoErrorForEdsGetPropertyData(propertyID, inParam, inPropertySize);

        // mock actual result
        when(mockMemory.getFloat(0)).thenReturn(expectedResult);

        final Float result = propertyGetLogicDefaultExtended.getPropertyData(fakeBaseRef, propertyID);

        Assertions.assertEquals(expectedResult, result);

        verify(CanonFactory.propertyLogic()).getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam);

        verify(CanonFactory.edsdkLibrary()).EdsGetPropertyData(eq(fakeBaseRef), eq(new NativeLong(propertyID.value())), eq(new NativeLong(inParam)), eq(new NativeLong(inPropertySize)), eq(mockMemory));
    }

    @Test
    void getPropertyDataForDouble() {
        final Double expectedResult = 5.0936;

        final EdsPropertyID propertyID = EdsPropertyID.kEdsPropID_ISOSpeed;
        final long inParam = 0L;
        final int inPropertySize = 4;

        propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_Double, inPropertySize);

        // mocks

        when(CanonFactory.propertyLogic().getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam)).thenReturn(propertyInfo);

        returnNoErrorForEdsGetPropertyData(propertyID, inParam, inPropertySize);

        // mock actual result
        when(mockMemory.getDouble(0)).thenReturn(expectedResult);

        final Double result = propertyGetLogicDefaultExtended.getPropertyData(fakeBaseRef, propertyID);

        Assertions.assertEquals(expectedResult, result);

        verify(CanonFactory.propertyLogic()).getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam);

        verify(CanonFactory.edsdkLibrary()).EdsGetPropertyData(eq(fakeBaseRef), eq(new NativeLong(propertyID.value())), eq(new NativeLong(inParam)), eq(new NativeLong(inPropertySize)), eq(mockMemory));
    }

    @Test
    void getPropertyDataForByteBlock() {
        final int[] expectedResult = new int[]{1, 5, 6, 99};

        final EdsPropertyID propertyID = EdsPropertyID.kEdsPropID_ISOSpeed;
        final long inParam = 0L;
        final int inPropertySize = 4;

        propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_ByteBlock, inPropertySize);

        // mocks

        when(CanonFactory.propertyLogic().getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam)).thenReturn(propertyInfo);

        returnNoErrorForEdsGetPropertyData(propertyID, inParam, inPropertySize);

        // mock actual result
        when(mockMemory.getIntArray(0, (int) (inPropertySize / 4))).thenReturn(expectedResult);

        final int[] result = propertyGetLogicDefaultExtended.getPropertyData(fakeBaseRef, propertyID);

        Assertions.assertEquals(expectedResult, result);

        verify(CanonFactory.propertyLogic()).getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam);

        verify(CanonFactory.edsdkLibrary()).EdsGetPropertyData(eq(fakeBaseRef), eq(new NativeLong(propertyID.value())), eq(new NativeLong(inParam)), eq(new NativeLong(inPropertySize)), eq(mockMemory));
    }

    @Disabled("Need fix")
    @Test
    void getPropertyDataForRational() {
        final EdsRational expectedResult = new EdsRational(mockMemory);

        final EdsPropertyID propertyID = EdsPropertyID.kEdsPropID_ISOSpeed;
        final long inParam = 0L;
        final int inPropertySize = 4;

        propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_Rational, inPropertySize);

        // mocks

        when(CanonFactory.propertyLogic().getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam)).thenReturn(propertyInfo);

        returnNoErrorForEdsGetPropertyData(propertyID, inParam, inPropertySize);

        // mock actual result
//        when(mockMemory.getDouble(0)).thenReturn(expectedResult);

        final EdsRational result = propertyGetLogicDefaultExtended.getPropertyData(fakeBaseRef, propertyID);

        Assertions.assertEquals(expectedResult, result);

        verify(CanonFactory.propertyLogic()).getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam);

        verify(CanonFactory.edsdkLibrary()).EdsGetPropertyData(eq(fakeBaseRef), eq(new NativeLong(propertyID.value())), eq(new NativeLong(inParam)), eq(new NativeLong(inPropertySize)), eq(mockMemory));
    }

    @Test
    void getPropertyDataForBoolArray() {
//        final EdsRational expectedResult = new EdsRational(mockMemory);

        final EdsPropertyID propertyID = EdsPropertyID.kEdsPropID_ISOSpeed;
        final long inParam = 0L;
        final int inPropertySize = 4;

        propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_Bool_Array, inPropertySize);

        // mocks

        when(CanonFactory.propertyLogic().getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam)).thenReturn(propertyInfo);

        returnNoErrorForEdsGetPropertyData(propertyID, inParam, inPropertySize);

        // mock actual result
//        when(mockMemory.getDouble(0)).thenReturn(expectedResult);

//        final EdsRational result = propertyGetLogicDefaultExtended.getPropertyData(fakeBaseRef, propertyID);
        Assertions.assertThrows(NotImplementedException.class, () -> propertyGetLogicDefaultExtended.getPropertyData(fakeBaseRef, propertyID));

//        Assertions.assertEquals(expectedResult, result);

        verify(CanonFactory.propertyLogic()).getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam);

        verify(CanonFactory.edsdkLibrary()).EdsGetPropertyData(eq(fakeBaseRef), eq(new NativeLong(propertyID.value())), eq(new NativeLong(inParam)), eq(new NativeLong(inPropertySize)), eq(mockMemory));
    }

    @Test
    void getPropertyDataForInt8Array() {
        final byte[] expectedResult = new byte[]{1, 2, 5, 9};

        final EdsPropertyID propertyID = EdsPropertyID.kEdsPropID_ISOSpeed;
        final long inParam = 0L;
        final int inPropertySize = 4;

        propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_Int8_Array, inPropertySize);

        // mocks

        when(CanonFactory.propertyLogic().getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam)).thenReturn(propertyInfo);

        returnNoErrorForEdsGetPropertyData(propertyID, inParam, inPropertySize);

        // mock actual result
        when(mockMemory.getByteArray(0, (int) inPropertySize)).thenReturn(expectedResult);

        final byte[] result = propertyGetLogicDefaultExtended.getPropertyData(fakeBaseRef, propertyID);

        Assertions.assertEquals(expectedResult, result);

        verify(CanonFactory.propertyLogic()).getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam);

        verify(CanonFactory.edsdkLibrary()).EdsGetPropertyData(eq(fakeBaseRef), eq(new NativeLong(propertyID.value())), eq(new NativeLong(inParam)), eq(new NativeLong(inPropertySize)), eq(mockMemory));
    }

    @Test
    void getPropertyDataForInt16Array() {
        final short[] expectedResult = new short[]{1, 2, 5, 9};

        final EdsPropertyID propertyID = EdsPropertyID.kEdsPropID_ISOSpeed;
        final long inParam = 0L;
        final int inPropertySize = 4;

        propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_Int16_Array, inPropertySize);

        // mocks

        when(CanonFactory.propertyLogic().getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam)).thenReturn(propertyInfo);

        returnNoErrorForEdsGetPropertyData(propertyID, inParam, inPropertySize);

        // mock actual result
        when(mockMemory.getShortArray(0, (int) (inPropertySize / 2))).thenReturn(expectedResult);

        final short[] result = propertyGetLogicDefaultExtended.getPropertyData(fakeBaseRef, propertyID);

        Assertions.assertEquals(expectedResult, result);

        verify(CanonFactory.propertyLogic()).getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam);

        verify(CanonFactory.edsdkLibrary()).EdsGetPropertyData(eq(fakeBaseRef), eq(new NativeLong(propertyID.value())), eq(new NativeLong(inParam)), eq(new NativeLong(inPropertySize)), eq(mockMemory));
    }

    @Test
    void getPropertyDataForInt32Array() {
        final int[] expectedResult = new int[]{1, 2, 5, 9};

        final EdsPropertyID propertyID = EdsPropertyID.kEdsPropID_ISOSpeed;
        final long inParam = 0L;
        final int inPropertySize = 4;

        propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_Int32_Array, inPropertySize);

        // mocks

        when(CanonFactory.propertyLogic().getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam)).thenReturn(propertyInfo);

        returnNoErrorForEdsGetPropertyData(propertyID, inParam, inPropertySize);

        // mock actual result
        when(mockMemory.getIntArray(0, (int) (inPropertySize / 4))).thenReturn(expectedResult);

        final int[] result = propertyGetLogicDefaultExtended.getPropertyData(fakeBaseRef, propertyID);

        Assertions.assertEquals(expectedResult, result);

        verify(CanonFactory.propertyLogic()).getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam);

        verify(CanonFactory.edsdkLibrary()).EdsGetPropertyData(eq(fakeBaseRef), eq(new NativeLong(propertyID.value())), eq(new NativeLong(inParam)), eq(new NativeLong(inPropertySize)), eq(mockMemory));
    }

    @Test
    void getPropertyDataForRationalArray() {
        final EdsRational[] expectedResult = new EdsRational[0];

        final EdsPropertyID propertyID = EdsPropertyID.kEdsPropID_ISOSpeed;
        final long inParam = 0L;
        final int inPropertySize = 4;

        propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_Rational_Array, inPropertySize);

        // mocks

        when(CanonFactory.propertyLogic().getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam)).thenReturn(propertyInfo);

        returnNoErrorForEdsGetPropertyData(propertyID, inParam, inPropertySize);

        // mock actual result
//        when(mockMemory...).thenReturn(expectedResult);

//        final EdsRational[] result = propertyGetLogicDefaultExtended.getPropertyData(fakeBaseRef, propertyID);
        Assertions.assertThrows(NotImplementedException.class, () -> propertyGetLogicDefaultExtended.getPropertyData(fakeBaseRef, propertyID));

//        Assertions.assertEquals(expectedResult, result);

        verify(CanonFactory.propertyLogic()).getPropertyTypeAndSize(fakeBaseRef, propertyID, inParam);

        verify(CanonFactory.edsdkLibrary()).EdsGetPropertyData(eq(fakeBaseRef), eq(new NativeLong(propertyID.value())), eq(new NativeLong(inParam)), eq(new NativeLong(inPropertySize)), eq(mockMemory));
    }

    private void returnNoErrorForEdsGetPropertyData(final EdsPropertyID propertyID, final long inParam, final int inPropertySize) {
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
