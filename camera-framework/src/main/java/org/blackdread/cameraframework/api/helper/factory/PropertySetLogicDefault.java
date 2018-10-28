package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.Pointer;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.constant.NativeEnum;
import org.blackdread.cameraframework.api.helper.logic.PropertySetLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Created on 2018/10/28.</p>
 *
 * @author Yoann CAPLAIN
 */
public class PropertySetLogicDefault implements PropertySetLogic {

    private static final Logger log = LoggerFactory.getLogger(PropertySetLogicDefault.class);

    @Override
    public EdsdkError setPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final NativeEnum<? extends Number> value) {
        return null;
    }

    @Override
    public EdsdkError setPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long inParam, final NativeEnum<? extends Number> value) {
        return null;
    }

    @Override
    public EdsdkError setPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long value) {
        return null;
    }

    @Override
    public EdsdkError setPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long inParam, final long value) {
        return null;
    }

    @Override
    public EdsdkError setPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long inParam, final int size, final Pointer data) {
        return null;
    }

    @Override
    public EdsdkError setPropertyDataAdvanced(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final Object value) {
        return null;
    }

    @Override
    public EdsdkError setPropertyDataAdvanced(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long inParam, final Object value) {
        return null;
    }
}
