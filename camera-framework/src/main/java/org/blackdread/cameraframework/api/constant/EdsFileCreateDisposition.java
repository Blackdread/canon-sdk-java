/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Yoann CAPLAIN
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
package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * File Create Disposition
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsFileCreateDisposition implements NativeEnum<Integer> {
    kEdsFileCreateDisposition_CreateNew("Creates a new file. An error occurs if the designated file already exists"),
    kEdsFileCreateDisposition_CreateAlways("Creates a new file. If the designated file already exists, that file is overwritten and existing attributes is erased"),
    kEdsFileCreateDisposition_OpenExisting("Opens a file. An error occurs if the designated file does not exist"),
    kEdsFileCreateDisposition_OpenAlways("If the file exists, it is opened. If the designated file does not exist, a new file is created"),
    kEdsFileCreateDisposition_TruncateExsisting("Opens a file and sets the file size to 0 bytes");

    private final int value;
    private final String description;

    EdsFileCreateDisposition(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsFileCreateDisposition.class, name());
        this.description = description;
    }

    @Override
    public final Integer value() {
        return value;
    }

    @Override
    public final String description() {
        return description;
    }

    /**
     * @param value value to search
     * @return enum having same value as passed
     * @throws IllegalArgumentException if value was not found
     */
    public static EdsFileCreateDisposition ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsFileCreateDisposition.class, value);
    }

}
