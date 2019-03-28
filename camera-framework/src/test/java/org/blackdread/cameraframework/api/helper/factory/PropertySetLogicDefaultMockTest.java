package org.blackdread.cameraframework.api.helper.factory;

import com.google.common.collect.Lists;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.AbstractMockTest;
import org.blackdread.cameraframework.MockFactory;
import org.blackdread.cameraframework.api.constant.EdsDataType;
import org.blackdread.cameraframework.api.constant.EdsISOSpeed;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.constant.NativeEnum;
import org.blackdread.cameraframework.api.helper.logic.PropertyInfo;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * <p>Created on 2019/03/27.</p>
 *
 * @author Yoann CAPLAIN
 */
class PropertySetLogicDefaultMockTest extends AbstractMockTest {

    private EdsCameraRef cameraRef;

    private EdsPropertyID propertyID;

    private NativeEnum<Integer> nativeEnum;

    private long inParam;

    @BeforeEach
    void setUp() {
        cameraRef = new EdsCameraRef();
        propertyID = EdsPropertyID.kEdsPropID_Unknown;
        nativeEnum = EdsISOSpeed.kEdsISOSpeed_100;
        inParam = 0L;

        when(CanonFactory.getCanonFactory().getPropertySetLogic()).thenReturn(MockFactory.initialCanonFactory.getPropertySetLogic());
        propertySetLogic = MockFactory.initialCanonFactory.getPropertySetLogic();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setPropertyDataErrorAreReThrown() {
        final PropertyInfo propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_Int32, 4);

        when(propertyLogic.getPropertyTypeAndSize(cameraRef, propertyID)).thenReturn(propertyInfo);

        final EdsdkError error = EdsdkError.EDS_ERR_DEVICE_INVALID;
        final Class<? extends EdsdkErrorException> exceptionClass = error.getException().getClass();

        mockEdsdkLibrary();

        when(canonLibrary.edsdkLibrary().EdsSetPropertyData(eq(cameraRef), any(NativeLong.class), any(NativeLong.class), any(NativeLong.class), any(Pointer.class))).thenReturn(new NativeLong(error.value()));

        Assertions.assertThrows(exceptionClass, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum));
        Assertions.assertThrows(exceptionClass, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum.value().longValue()));
        Assertions.assertThrows(exceptionClass, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum));
        Assertions.assertThrows(exceptionClass, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum.value().longValue()));
        Assertions.assertThrows(exceptionClass, () -> propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, 5L));
        Assertions.assertThrows(exceptionClass, () -> propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, inParam, 6L));
    }

    @Test
    void setPropertyDataThrowsForUnknown() {
        final PropertyInfo propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_Unknown, 4);

        when(propertyLogic.getPropertyTypeAndSize(cameraRef, propertyID)).thenReturn(propertyInfo);

        Assertions.assertThrows(IllegalStateException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum));
        Assertions.assertThrows(IllegalStateException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum.value().longValue()));
        Assertions.assertThrows(IllegalStateException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum));
        Assertions.assertThrows(IllegalStateException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum.value().longValue()));
        Assertions.assertThrows(IllegalStateException.class, () -> propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, "any"));
        Assertions.assertThrows(IllegalStateException.class, () -> propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, inParam, "any"));
    }

    @Test
    void setPropertyDataThrowsForBool() {
        final PropertyInfo propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_Bool, 1);

        when(propertyLogic.getPropertyTypeAndSize(cameraRef, propertyID)).thenReturn(propertyInfo);

        Assertions.assertThrows(IllegalStateException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum));
        Assertions.assertThrows(IllegalStateException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum.value().longValue()));
        Assertions.assertThrows(IllegalStateException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum));
        Assertions.assertThrows(IllegalStateException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum.value().longValue()));
        Assertions.assertThrows(IllegalStateException.class, () -> propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, "any"));
        Assertions.assertThrows(IllegalStateException.class, () -> propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, inParam, "any"));
    }

    @Test
    void setPropertyDataThrowsForString() {
        final PropertyInfo propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_String, 4);

        when(propertyLogic.getPropertyTypeAndSize(cameraRef, propertyID)).thenReturn(propertyInfo);

        mockEdsdkLibrary();

        when(canonLibrary.edsdkLibrary().EdsSetPropertyData(eq(cameraRef), any(NativeLong.class), any(NativeLong.class), any(NativeLong.class), any(Pointer.class))).thenReturn(new NativeLong(0));

        Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum));
        Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum.value().longValue()));
        Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum));
        Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum.value().longValue()));
        propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, "any");
        propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, inParam, "any");
    }

    @Test
    void setPropertyDataThrowsForInt8() {
        final ArrayList<PropertyInfo> infos = Lists.newArrayList(new PropertyInfo(EdsDataType.kEdsDataType_Int8, 1), new PropertyInfo(EdsDataType.kEdsDataType_UInt8, 1));
        for (PropertyInfo propertyInfo : infos) {
            when(propertyLogic.getPropertyTypeAndSize(cameraRef, propertyID)).thenReturn(propertyInfo);

            mockEdsdkLibrary();

            when(canonLibrary.edsdkLibrary().EdsSetPropertyData(eq(cameraRef), any(NativeLong.class), any(NativeLong.class), any(NativeLong.class), any(Pointer.class))).thenReturn(new NativeLong(0));

            Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum));
            Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum.value().longValue()));
            Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum));
            Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum.value().longValue()));
            propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, (byte) 0x5);
            propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, inParam, (byte) 0x6);
        }
    }

    @Test
    void setPropertyDataThrowsForInt16() {
        final ArrayList<PropertyInfo> infos = Lists.newArrayList(new PropertyInfo(EdsDataType.kEdsDataType_Int16, 2), new PropertyInfo(EdsDataType.kEdsDataType_UInt16, 2));
        for (PropertyInfo propertyInfo : infos) {
            when(propertyLogic.getPropertyTypeAndSize(cameraRef, propertyID)).thenReturn(propertyInfo);

            mockEdsdkLibrary();

            Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum));
            Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum.value().longValue()));
            Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum));
            Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum.value().longValue()));

            when(canonLibrary.edsdkLibrary().EdsSetPropertyData(eq(cameraRef), any(NativeLong.class), any(NativeLong.class), any(NativeLong.class), any(Pointer.class))).thenReturn(new NativeLong(0));

            propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, (short) 0x5);
            propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, inParam, (short) 0x6);
        }
    }

    @Test
    void setPropertyDataThrowsForInt32() {
        final ArrayList<PropertyInfo> infos = Lists.newArrayList(new PropertyInfo(EdsDataType.kEdsDataType_Int32, 4), new PropertyInfo(EdsDataType.kEdsDataType_UInt32, 4));
        for (PropertyInfo propertyInfo : infos) {
            when(propertyLogic.getPropertyTypeAndSize(cameraRef, propertyID)).thenReturn(propertyInfo);

            mockEdsdkLibrary();

            when(canonLibrary.edsdkLibrary().EdsSetPropertyData(eq(cameraRef), any(NativeLong.class), any(NativeLong.class), any(NativeLong.class), any(Pointer.class))).thenReturn(new NativeLong(0));

            propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum);
            propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum.value().longValue());
            propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum);
            propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum.value().longValue());
            propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, 5L);
            propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, inParam, 6L);
        }
    }

    @Test
    void setPropertyDataThrowsForInt64() {
        final ArrayList<PropertyInfo> infos = Lists.newArrayList(new PropertyInfo(EdsDataType.kEdsDataType_Int64, 8), new PropertyInfo(EdsDataType.kEdsDataType_UInt64, 8));
        for (PropertyInfo propertyInfo : infos) {
            when(propertyLogic.getPropertyTypeAndSize(cameraRef, propertyID)).thenReturn(propertyInfo);

            mockEdsdkLibrary();

            when(canonLibrary.edsdkLibrary().EdsSetPropertyData(eq(cameraRef), any(NativeLong.class), any(NativeLong.class), any(NativeLong.class), any(Pointer.class))).thenReturn(new NativeLong(0));

            propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum);
            propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum.value().longValue());
            propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum);
            propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum.value().longValue());
            propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, 5L);
            propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, inParam, 6L);
        }
    }

    @Test
    void setPropertyDataThrowsForFloat() {
        final PropertyInfo propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_Float, 8);

        when(propertyLogic.getPropertyTypeAndSize(cameraRef, propertyID)).thenReturn(propertyInfo);

        mockEdsdkLibrary();

        when(canonLibrary.edsdkLibrary().EdsSetPropertyData(eq(cameraRef), any(NativeLong.class), any(NativeLong.class), any(NativeLong.class), any(Pointer.class))).thenReturn(new NativeLong(0));

        Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum));
        Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum.value().longValue()));
        Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum));
        Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum.value().longValue()));
        propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, 5.5f);
        propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, inParam, 6.5f);
    }

    @Test
    void setPropertyDataThrowsForDouble() {
        final PropertyInfo propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_Double, 8);

        when(propertyLogic.getPropertyTypeAndSize(cameraRef, propertyID)).thenReturn(propertyInfo);

        mockEdsdkLibrary();

        when(canonLibrary.edsdkLibrary().EdsSetPropertyData(eq(cameraRef), any(NativeLong.class), any(NativeLong.class), any(NativeLong.class), any(Pointer.class))).thenReturn(new NativeLong(0));

        Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum));
        Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum.value().longValue()));
        Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum));
        Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum.value().longValue()));
        propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, 5.5);
        propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, inParam, 6.5);
    }

    @Test
    void setPropertyDataThrowsForByteBlock() {
        final PropertyInfo propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_ByteBlock, 4);

        when(propertyLogic.getPropertyTypeAndSize(cameraRef, propertyID)).thenReturn(propertyInfo);

        mockEdsdkLibrary();

        when(canonLibrary.edsdkLibrary().EdsSetPropertyData(eq(cameraRef), any(NativeLong.class), any(NativeLong.class), any(NativeLong.class), any(Pointer.class))).thenReturn(new NativeLong(0));

        Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum));
        Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum.value().longValue()));
        Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum));
        Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum.value().longValue()));
        propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, new int[]{5, 6, 7, 8});
        propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, inParam, new int[]{6, 7, 8, 9});
    }

    @Test
    void setPropertyDataThrowsForInt8Array() {
        final ArrayList<PropertyInfo> infos = Lists.newArrayList(new PropertyInfo(EdsDataType.kEdsDataType_Int8_Array, 1), new PropertyInfo(EdsDataType.kEdsDataType_UInt8_Array, 1));
        for (PropertyInfo propertyInfo : infos) {
            when(propertyLogic.getPropertyTypeAndSize(cameraRef, propertyID)).thenReturn(propertyInfo);

            mockEdsdkLibrary();

            when(canonLibrary.edsdkLibrary().EdsSetPropertyData(eq(cameraRef), any(NativeLong.class), any(NativeLong.class), any(NativeLong.class), any(Pointer.class))).thenReturn(new NativeLong(0));

            Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum));
            Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum.value().longValue()));
            Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum));
            Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum.value().longValue()));
            propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, new byte[]{5, 6, 7, 8});
            propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, inParam, new byte[]{6, 7, 8, 9});
        }
    }

    @Test
    void setPropertyDataThrowsForInt16Array() {
        final ArrayList<PropertyInfo> infos = Lists.newArrayList(new PropertyInfo(EdsDataType.kEdsDataType_Int16_Array, 2), new PropertyInfo(EdsDataType.kEdsDataType_UInt16_Array, 2));
        for (PropertyInfo propertyInfo : infos) {
            when(propertyLogic.getPropertyTypeAndSize(cameraRef, propertyID)).thenReturn(propertyInfo);

            mockEdsdkLibrary();

            when(canonLibrary.edsdkLibrary().EdsSetPropertyData(eq(cameraRef), any(NativeLong.class), any(NativeLong.class), any(NativeLong.class), any(Pointer.class))).thenReturn(new NativeLong(0));

            Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum));
            Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum.value().longValue()));
            Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum));
            Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum.value().longValue()));
            propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, new short[]{5, 6, 7, 8});
            propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, inParam, new short[]{6, 7, 8, 9});
        }
    }

    @Test
    void setPropertyDataThrowsForInt32Array() {
        final ArrayList<PropertyInfo> infos = Lists.newArrayList(new PropertyInfo(EdsDataType.kEdsDataType_Int32_Array, 4), new PropertyInfo(EdsDataType.kEdsDataType_UInt32_Array, 4));
        for (PropertyInfo propertyInfo : infos) {
            when(propertyLogic.getPropertyTypeAndSize(cameraRef, propertyID)).thenReturn(propertyInfo);

            mockEdsdkLibrary();

            when(canonLibrary.edsdkLibrary().EdsSetPropertyData(eq(cameraRef), any(NativeLong.class), any(NativeLong.class), any(NativeLong.class), any(Pointer.class))).thenReturn(new NativeLong(0));

            Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum));
            Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum.value().longValue()));
            Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum));
            Assertions.assertThrows(ClassCastException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum.value().longValue()));
            propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, new int[]{5, 6, 7, 8});
            propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, inParam, new int[]{6, 7, 8, 9});
        }
    }

    @Test
    void setPropertyDataThrowsForRationalArray() {
        final PropertyInfo propertyInfo = new PropertyInfo(EdsDataType.kEdsDataType_Rational_Array, 4);

        when(propertyLogic.getPropertyTypeAndSize(cameraRef, propertyID)).thenReturn(propertyInfo);

        mockEdsdkLibrary();

        when(canonLibrary.edsdkLibrary().EdsSetPropertyData(eq(cameraRef), any(NativeLong.class), any(NativeLong.class), any(NativeLong.class), any(Pointer.class))).thenReturn(new NativeLong(0));

        Assertions.assertThrows(IllegalStateException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum));
        Assertions.assertThrows(IllegalStateException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, nativeEnum.value().longValue()));
        Assertions.assertThrows(IllegalStateException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum));
        Assertions.assertThrows(IllegalStateException.class, () -> propertySetLogic.setPropertyData(cameraRef, propertyID, inParam, nativeEnum.value().longValue()));
        Assertions.assertThrows(IllegalStateException.class, () -> propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, "any"));
        Assertions.assertThrows(IllegalStateException.class, () -> propertySetLogic.setPropertyDataAdvanced(cameraRef, propertyID, inParam, "any"));
    }

}
