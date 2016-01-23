package snow.skittles.underlay.blur;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;

/**
 * I am a util class which provides a blur method
 */
public final class BlurUtils {

    /**
     * To reduce size of the blur bitmap
     */
    private static final float BITMAP_SCALE = 0.4f;

    static {
        try {
            System.loadLibrary("RSSupport");
            System.loadLibrary("rsjni");
        } catch (UnsatisfiedLinkError e) {
            if ("Dalvik".equals(System.getProperty("java.vm.name"))
                    || "Art".equals(System.getProperty("java.vm.name"))) {
                throw e;
            }
        }
    }

    /**
     * Fast blur using render script implementation @see <a href="http://developer.android.com/intl/ja/guide/topics/renderscript/compute.html">RenderScript</a>
     * @param context used to init RenderScript {@link RenderScript}
     * @param bitmapToBlur is an image which want to blur
     * @param radius used in blur algorithm
     * @return a blurred image
     */
    public static Bitmap fastBlur(Context context, Bitmap bitmapToBlur, float radius) {

        int width = Math.round(bitmapToBlur.getWidth() * BITMAP_SCALE);
        int height = Math.round(bitmapToBlur.getHeight() * BITMAP_SCALE);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(bitmapToBlur, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(radius);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;

    }
}