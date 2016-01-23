package snow.skittles.underlay.blur;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;

import snow.skittles.underlay.utils.SafeAsyncTask;

/**
 * I represent background task which is responsible for creating blur bitmap for provided image and save it in local storage.
 */
public class BlurAsyncTask extends SafeAsyncTask<Bitmap> {

    /**
     * An interface which is a callback for blur processing
     */
    public interface BlurAsyncCallback {
        void onBlurSuccess(Bitmap blur);

        void onBlurError(Throwable throwable);
    }

    private Context context;
    private Bitmap bitmapToBlur;
    private BlurAsyncCallback blurAsyncCallback;

    public BlurAsyncTask(Context context, Bitmap bitmapToBlur, BlurAsyncCallback blurAsyncCallback) {
        this.context = context;
        this.bitmapToBlur = bitmapToBlur;
        this.blurAsyncCallback = blurAsyncCallback;
    }

    @Override
    public Bitmap call() throws Exception {
        File file = new File(context.getFilesDir(), "skittle-blur.png");

        Bitmap blurredBitmap = BlurUtils.fastBlur(context, bitmapToBlur, 8f);
        ImageFileUtils.storeBitmapToFile(blurredBitmap, file);

        return blurredBitmap;
    }

    @Override
    protected void onSuccess(Bitmap bitmap) throws Exception {
        super.onSuccess(bitmap);
        blurAsyncCallback.onBlurSuccess(bitmap);
    }

    @Override
    protected void onFinally() throws RuntimeException {
        super.onFinally();
        bitmapToBlur.recycle();
    }

    @Override
    protected void onThrowable(Throwable t) throws RuntimeException {
        super.onThrowable(t);
        blurAsyncCallback.onBlurError(t);
    }
}
