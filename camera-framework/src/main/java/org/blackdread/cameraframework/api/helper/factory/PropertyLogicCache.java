package org.blackdread.cameraframework.api.helper.factory;

import org.blackdread.cameraframework.api.helper.logic.PropertyLogic;

import java.util.Objects;

/**
 * <p>Created on 2018/10/18.<p>
 *
 * @author Yoann CAPLAIN
 */
public class PropertyLogicCache /*implements PropertyLogic*/ {

    private final PropertyLogic delegate;

    public PropertyLogicCache(final PropertyLogic delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }
/*
    @Override
    public EdsDataType getPropertyType(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long param) {
        return null;
    }

    @Override
    public long getPropertySize(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long param) {
        return 0;
    }*/
}
