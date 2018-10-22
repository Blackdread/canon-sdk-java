package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.Pointer;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.constant.EdsDataType;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkErrors;
import org.blackdread.cameraframework.api.constant.NativeEnum;
import org.blackdread.cameraframework.api.helper.logic.PropertyLogic;

/**
 * <p>Created on 2018/10/09.<p>
 *
 * @author Yoann CAPLAIN
 */
public class PropertyLogicDefault implements PropertyLogic {

    protected PropertyLogicDefault() {
    }

    /*
     * @return new instance at each call
     */
//    static PropertyLogic getDefault() {
//        return new PropertyLogicDefault();
//    }

    @Override
    public EdsDataType getPropertyType(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long param) {
//        final int bufferSize = 1;
//        final IntBuffer type = IntBuffer.allocate(bufferSize);
//        final NativeLongByReference number = new NativeLongByReference(new NativeLong(bufferSize));
//        final EdsError err = CanonUtils.toEdsError(CanonCamera.EDSDK.EdsGetPropertySize(ref, new NativeLong(property.value()), new NativeLong(param), type, number));
//        if (err == EdsError.EDS_ERR_OK) {
//            final EdsDataType edsDataType = EdsDataType.enumOfValue(type.get(0));
//            if (edsDataType != null) {
//                //System.out.println( " > property type = " + edsDataType.value() + " : " + edsDataType.name() + " : " + edsDataType.description() );
//                return edsDataType;
//            }
//        } else if (err == EdsError.EDS_ERR_NOT_SUPPORTED) {
//            return null;
//        }
//
//        return EdsDataType.kEdsDataType_Unknown;
        return null;
    }

    @Override
    public long getPropertySize(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long param) {
        return 0;
    }

    @Override
    public Long getPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property) {
        return null;
    }

    @Override
    public Long getPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long param) {
        return null;
    }

    @Override
    public <T> T getPropertyDataAdvanced(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property) {
        return null;
    }

    @Override
    public <T> T getPropertyDataAdvanced(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long param) {
        return null;
    }

    @Override
    public EdsdkErrors getPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long param, final long size, final Pointer data) {
        return null;
    }

    @Override
    public EdsdkErrors setPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final NativeEnum<? extends Number> value) {
        return null;
    }

    @Override
    public EdsdkErrors setPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long param, final NativeEnum<? extends Number> value) {
        return null;
    }

    @Override
    public EdsdkErrors setPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long value) {
        return null;
    }

    @Override
    public EdsdkErrors setPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long param, final long value) {
        return null;
    }

    @Override
    public EdsdkErrors setPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long param, final int size, final Pointer data) {
        return null;
    }

    @Override
    public EdsdkErrors setPropertyDataAdvanced(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final Object value) {
        return null;
    }

    @Override
    public EdsdkErrors setPropertyDataAdvanced(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long param, final Object value) {
        return null;
    }
}
