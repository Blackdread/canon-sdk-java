package org.blackdread.cameraframework.api.helper.property;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.constant.EdsDataType;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;

/**
 * <p>Created on 2018/10/09.<p>
 *
 * @author Yoann CAPLAIN
 */
public class PropertyLogicDefault implements PropertyLogic {

    public static final PropertyLogic DEFAULT = new PropertyLogicDefault();

    protected PropertyLogicDefault() {
    }

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
}
