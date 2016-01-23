package snow.skittles.underlay.processor;

import android.view.View;

/**
 * I am the abstract base class for all underlay processors which are used by skittle container {@link snow.skittles.SkittleLayout} when skittle is expanded.
 */
public abstract class UnderlayViewProcessor {

    /**
     * I am the callback interface to notify when underlay processor finished his work
     */
    public interface ViewProcessorCallback {
        void onViewSuccess(View backgroundView);

        void onViewError(Throwable throwable);
    }

    /**
     * Variable which holds underlay layer which skittle container {@link snow.skittles.SkittleLayout}
     */
    protected View viewBackgroundContainer;

    public UnderlayViewProcessor(View viewBackgroundContainer) {
        this.viewBackgroundContainer = viewBackgroundContainer;
    }

    /**
     * Method responsible for processing underlay for expanded {@link snow.skittles.SkittleLayout}
     * @param callback {@link ViewProcessorCallback}
     */
    public abstract void process(ViewProcessorCallback callback);

    /**
     * Getter method to get a view which is our background underlay
     * @return
     */
    public View getViewBackgorundContainer() {
        return viewBackgroundContainer;
    }

    public void release() {}

}
