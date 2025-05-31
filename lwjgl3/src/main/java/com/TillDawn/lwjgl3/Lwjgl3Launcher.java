package com.TillDawn.lwjgl3;

import com.TillDawn.DragAndDropHandler;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.TillDawn.TillDawn;

import java.io.File;
import java.util.List;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        createApplication();
    }

//    private static DragAndDropHandler.FileDropCallback fileDropCallback;
//
//    public static java.awt.Frame getWindow() {
//        for (java.awt.Window window : java.awt.Window.getWindows()) {
//            if (window instanceof java.awt.Frame) return (java.awt.Frame) window;
//        }
//        return null;
//    }

    private static Lwjgl3Application createApplication() {
        TillDawn app = new TillDawn();
//        app.setDragAndDropHandler(callback -> {
//            fileDropCallback = callback;
//
//            java.awt.EventQueue.invokeLater(() -> {
//                java.awt.Frame frame = getWindow();
//                if (frame != null) {
//                    System.out.println("hey");
//                    new java.awt.dnd.DropTarget(frame, new java.awt.dnd.DropTargetAdapter() {
//                        @Override
//                        public void drop(java.awt.dnd.DropTargetDropEvent event) {
//                            try {
//                                System.out.println("droopped");
//                                event.acceptDrop(java.awt.dnd.DnDConstants.ACTION_COPY);
//                                @SuppressWarnings("unchecked")
//                                List<File> droppedFiles = (List<File>) event.getTransferable()
//                                    .getTransferData(java.awt.datatransfer.DataFlavor.javaFileListFlavor);
//                                for (File file : droppedFiles) {
//                                    if (file.isFile() && fileDropCallback != null) {
//                                        fileDropCallback.onFileDropped(file.getAbsolutePath());
//                                    }
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                }
//            });
//        });

        return new Lwjgl3Application(app, getDefaultConfiguration());
    }


    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("TillDawn");
        //// Vsync limits the frames per second to what your hardware can display, and helps eliminate
        //// screen tearing. This setting doesn't always work on Linux, so the line after is a safeguard.
        configuration.useVsync(true);
        //// Limits FPS to the refresh rate of the currently active monitor, plus 1 to try to match fractional
        //// refresh rates. The Vsync setting above should limit the actual FPS to match the monitor.
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        //// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
        //// useful for testing performance, but can also be very stressful to some hardware.
        //// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.

        configuration.setWindowedMode(1728, 972);
        //// You can change these files; they are in lwjgl3/src/main/resources/ .
        //// They can also be loaded from the root of assets/ .
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        return configuration;
    }
}
