package snow.skittles.underlay.processor;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.widget.ImageView;

import snow.skittles.R;
import snow.skittles.SkittleLayout;
import snow.skittles.underlay.blur.BlurAsyncTask;

/**
 * I am a class which defines a blur underlay layer for skittle expanded container {@link SkittleLayout.SkittleBackgroundMode}.
 * This processor has two main responsibilities:
 *  - create a snapshot of current parent view
 *  - blur created in previous step bitmap of the view
 */
public class BlurUnderlayViewProcessor extends UnderlayViewProcessor {

    /**
     * It's a view container for {@link SkittleLayout}
     */
    private View viewParentContainer;

    /**
     * It's responsible for displaying blurred image
     */
    private ImageView imageViewBlur;

    public BlurUnderlayViewProcessor(View viewParentContainer, View backgroundContainer) {
        super(backgroundContainer);

        this.viewParentContainer = viewParentContainer;
        this.imageViewBlur = (ImageView) backgroundContainer.findViewById(R.id.iv_skittle_blur);
    }

    @Override
    public void process(final ViewProcessorCallback callback) {
        Bitmap viewSnapshot = createSnapshot(viewParentContainer);
        Bitmap finalSnapshot = cutStatusBar(viewSnapshot.getWidth(), viewSnapshot.getHeight(), viewSnapshot, viewParentContainer.getResources());
        viewSnapshot.recycle();

        new BlurAsyncTask(viewParentContainer.getContext(), finalSnapshot, new BlurAsyncTask.BlurAsyncCallback() {
            @Override
            public void onBlurSuccess(Bitmap blur) {
                imageViewBlur.setImageBitmap(blur);
                callback.onViewSuccess(viewBackgroundContainer);
            }

            @Override
            public void onBlurError(Throwable throwable) {
                callback.onViewError(throwable);
            }
        }).execute();
    }

    @Override
    public void release() {
        imageViewBlur.setImageBitmap(null);
    }

    private Bitmap createSnapshot(View view) {
        Bitmap localBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        view.draw(localCanvas);
        return localBitmap;
    }

    private Bitmap cutStatusBar(int width, int height, Bitmap localBitmap, Resources resources) {
        int yOffset = getDeviceStatusBarHeight(resources);

        if(hasTranscluentStatusBar(resources)) {
            return Bitmap.createBitmap(localBitmap, 0, yOffset, width, height - yOffset, null, true);
        } else {
            return Bitmap.createBitmap(localBitmap, 0, 0, width, height, null, true);
        }
    }

    private boolean hasTranscluentStatusBar(Resources resources) {
        int resourceId = resources.getIdentifier("config_enableTranslucentDecor", "bool", "android");
        return resourceId > 0;
    }

    private int getDeviceStatusBarHeight(Resources resources) {
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resourceId > 0 ? resources.getDimensionPixelSize(resourceId) : 0;
    }
}
