/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Yoann CAPLAIN
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.blackdread.cameraframework.api.command;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.command.builder.ShootOption;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

import java.io.File;
import java.util.List;

/**
 * <p>Created on 2018/10/02.<p>
 *
 * @author Yoann CAPLAIN
 * @see org.blackdread.cameraframework.api.helper.logic.ShootLogic#shoot(EdsCameraRef)
 * @see org.blackdread.cameraframework.api.helper.logic.ShootLogic#shoot(EdsCameraRef, ShootOption)
 * @since 1.0.0
 */
public class ShootCommand extends AbstractCanonCommand<List<File>> {

    private final ShootOption shootOption;

    public ShootCommand() {
        shootOption = null;
    }

    public ShootCommand(final ShootOption shootOption) {
        this.shootOption = shootOption;
    }

    public ShootCommand(final ShootCommand toCopy) {
        super(toCopy);
        this.shootOption = toCopy.shootOption;
    }

    @Override
    protected List<File> runInternal() throws InterruptedException {
        final EdsCameraRef ref = (EdsCameraRef) getTargetRefInternal();
        if (shootOption == null) {
            return CanonFactory.shootLogic().shoot(ref);
        } else {
            return CanonFactory.shootLogic().shoot(ref, shootOption);
        }
    }
}
