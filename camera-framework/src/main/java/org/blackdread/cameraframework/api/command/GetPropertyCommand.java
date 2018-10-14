package org.blackdread.cameraframework.api.command;

import org.blackdread.cameraframework.api.constant.EdsPropertyID;

/**
 * Gets a property from the camera.
 * <br>
 * The function EdsSetPropertyData is used to set properties.
 * <br>
 * This API takes objects of undefined type as arguments, the properties that can be retrieved or set
 * differ depending on the given object. In addition, some properties have a list of currently settable values.
 * EdsGetPropertyDesc is used to get this list of settable values.
 * <p>Created on 2018/10/02.<p>
 *
 * @author Yoann CAPLAIN
 */
public abstract class GetPropertyCommand<R> extends AbstractCanonCommand<R> {

    @Override
    protected void runInternal() {
        System.out.println("impl 1");
    }

    public static class ProductName extends GetPropertyCommand<String> {

        public ProductName() {
            final EdsPropertyID kEdsPropID_productName = EdsPropertyID.kEdsPropID_ProductName;
        }
    }

}
