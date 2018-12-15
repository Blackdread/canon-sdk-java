package org.blackdread.cameraframework.api.command.builder;

/**
 * <p>Created on 2018/12/14.</p>
 *
 * @author Yoann CAPLAIN
 * @see OpenSessionOption OpenSessionOption for documentation on options
 * @since 1.0.0
 */
public class OpenSessionOptionBuilder {

    private Integer cameraByIndex = 0;
    private String cameraBySerialNumber;

    public OpenSessionOptionBuilder setCameraByIndex(final Integer cameraByIndex) {
        this.cameraByIndex = cameraByIndex;
        this.cameraBySerialNumber = null;
        return this;
    }

    public OpenSessionOptionBuilder setCameraBySerialNumber(final String cameraBySerialNumber) {
        this.cameraBySerialNumber = cameraBySerialNumber;
        this.cameraByIndex = null;
        return this;
    }

    public OpenSessionOption build() {
        validate();
        return new OpenSessionOption(cameraByIndex, cameraBySerialNumber);
    }

    private void validate() {
        if (cameraByIndex == null && cameraBySerialNumber == null) {
            throw new IllegalStateException("At least one option must be chosen");
        }
        if (cameraByIndex != null && cameraBySerialNumber != null) {
            throw new IllegalStateException("cameraByIndex and cameraBySerialNumber cannot be both non-null");
        }
    }
}
