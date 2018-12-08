package org.blackdread.cameraframework.api.command;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * <p>Created on 2018/12/08.</p>
 *
 * @author Yoann CAPLAIN
 */
public enum TargetRefType {
    CAMERA,
    IMAGE,
    EVF_IMAGE,
    VOLUME,
    DIRECTORY_ITEM;

    public static final Set<TargetRefType> CAMERA_ONLY = ImmutableSet.of(TargetRefType.CAMERA);

    public static final Set<TargetRefType> IMAGE_ONLY = ImmutableSet.of(TargetRefType.IMAGE);

    public static final Set<TargetRefType> CAMERA_IMAGE_ONLY = ImmutableSet.of(TargetRefType.CAMERA, TargetRefType.IMAGE);

    public static final Set<TargetRefType> EVF_IMAGE_ONLY = ImmutableSet.of(TargetRefType.EVF_IMAGE);

    public static final Set<TargetRefType> CAMERA_EVF_IMAGE_ONLY = ImmutableSet.of(TargetRefType.CAMERA, TargetRefType.EVF_IMAGE);

}
