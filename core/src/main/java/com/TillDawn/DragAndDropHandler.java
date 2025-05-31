package com.TillDawn;

public interface DragAndDropHandler {
    void registerFileDropListener(FileDropCallback callback);

    interface FileDropCallback {
        void onFileDropped(String path);
    }
}
