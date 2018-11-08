package org.blackdread.cameraframework;

import java.lang.ref.WeakReference;
import java.util.function.Consumer;

public class CameraFramework {


    public static void main(String... args) throws InterruptedException {
        final CameraFramework cameraFramework = new CameraFramework();

        Object s1 = new Object();
        Object s2 = new Object();
        Object s3 = new Object();

        final Consumer<Object> consumer1 = cameraFramework.buildHandler1(s1);
        final Consumer<Object> consumer2 = cameraFramework.buildHandler2(s2);
        final Consumer<Object> consumer3 = cameraFramework.buildHandler3(s3);

        s1 = null;
        s2 = null;

        for (int i = 0; i < 999999; i++) {
            consumer1.accept("aby1");
            consumer2.accept("aby2");
            consumer3.accept("aby3");
            System.gc();
            Thread.sleep(100);
            System.gc();
        }

    }


    private Consumer<Object> buildHandler1(final Object myS) {
        final WeakReference<Object> weak = new WeakReference<>(myS);
        return s -> {
            final Object s1 = weak.get();
            System.out.println("Weak 1=" + s1);
        };
    }


    private Consumer<Object> buildHandler2(final Object myS) {
        return s -> System.out.println("Weak 2=" + myS);
    }


    private Consumer<Object> buildHandler3(final Object myS) {
        final WeakReference<Object> weak = new WeakReference<>(myS);
        return s -> {
            final Object s1 = weak.get();
            System.out.println("Weak 3=" + s1);
        };
    }

}
