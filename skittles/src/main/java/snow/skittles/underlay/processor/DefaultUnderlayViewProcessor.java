package snow.skittles.underlay.processor;

import snow.skittles.SkittleLayout;

/**
 * I am a class which defines a default/none underlay layer for skittle expanded container {@link SkittleLayout.SkittleBackgroundMode}.
 * This processor provides empty underlay.
 */
public class DefaultUnderlayViewProcessor extends UnderlayViewProcessor {

    public DefaultUnderlayViewProcessor() {
        super(null);
    }

    @Override
    public void process(ViewProcessorCallback callback) {
        callback.onViewSuccess(viewBackgroundContainer);
    }
}
