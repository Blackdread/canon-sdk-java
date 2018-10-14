package org.blackdread.cameraframework.api.helper.factory;

import org.blackdread.cameraframework.api.helper.property.PropertyLogic;
import org.blackdread.cameraframework.api.helper.property.PropertyLogicDefault;

/**
 * <p>Created on 2018/10/09.<p>
 *
 * @author Yoann CAPLAIN
 */
public class HelperFactory {

    PropertyLogic getPropertyLogic() {
        return PropertyLogicDefault.DEFAULT;
    }
}
