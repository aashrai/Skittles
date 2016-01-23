package snow.skittles.underlay.blur;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * I am a util class which provides helper methods to operate on bitmaps and files
 */
public final class ImageFileUtils {

    private static final String TAG = "ImageFileUtils";

    private static final int PNG_QUALITY_VALUE = 1;

    /**
     * This methods save bitmap to provided file.
     * @param image which we want to store in a file
     * @param file to which we save provided bitmap
     */
    public static void storeBitmapToFile(Bitmap image, File file) {
        if(file == null) {
            return;
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, PNG_QUALITY_VALUE, fileOutputStream);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "The file to save a bitmap file couldn't be find! ", e);
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, "There was a problem when closing stream to save a blur image!", e);
                }
            }
        }
    }
}