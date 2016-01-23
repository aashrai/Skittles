package snow.skittles.underlay.processor;

import android.view.View;

import snow.skittles.SkittleLayout;

/**
 * I am a class which defines standard underlay layer for skittle expanded container {@link SkittleLayout.SkittleBackgroundMode}.
 * This provides simple layer which is only a frame layout which is expanded with skittle is expanded. User can set an alpha and background color for it.
 */
public class StandardUnderlayViewProcessor extends UnderlayViewProcessor {

    public StandardUnderlayViewProcessor(View backgroundContainer) {
        super(backgroundContainer);
    }

    @Override
    public void process(ViewProcessorCallback callback) {
        callback.onViewSuccess(viewBackgroundContainer);
    }

}
