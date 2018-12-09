package org.blackdread.cameraframework.api.command;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.constant.EdsCameraCommand;
import org.blackdread.cameraframework.api.constant.NativeEnum;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

/**
 * <p>Created on 2018/10/02.<p>
 *
 * @author Yoann CAPLAIN
 */
public class CameraCommand extends AbstractCanonCommand<Void> {

    private final EdsCameraCommand cameraCommand;

    private final long inParam;

    public CameraCommand(final EdsCameraCommand cameraCommand) {
        this.cameraCommand = cameraCommand;
        this.inParam = 0;
    }

    public CameraCommand(final EdsCameraCommand cameraCommand, final long inParam) {
        this.cameraCommand = cameraCommand;
        this.inParam = inParam;
    }

    public CameraCommand(final EdsCameraCommand cameraCommand, final NativeEnum<? extends Number> param) {
        this.cameraCommand = cameraCommand;
        this.inParam = param.value().longValue();
    }

    public CameraCommand(final CameraCommand toCopy) {
        super(toCopy);
        this.cameraCommand = toCopy.cameraCommand;
        this.inParam = toCopy.inParam;
    }

    @Override
    protected Void runInternal() {
        CanonFactory.cameraLogic().sendCommand((EdsCameraRef) getTargetRefInternal(), cameraCommand, inParam);
        return null;
    }
}
