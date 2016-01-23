package snow.skittles.underlay;

import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import snow.skittles.R;
import snow.skittles.underlay.processor.BlurUnderlayViewProcessor;
import snow.skittles.underlay.processor.DefaultUnderlayViewProcessor;
import snow.skittles.underlay.processor.StandardUnderlayViewProcessor;
import snow.skittles.underlay.processor.UnderlayViewProcessor;

import static snow.skittles.SkittleLayout.SKITTLE_BLUR_UNDERLAY_MODE;
import static snow.skittles.SkittleLayout.SKITTLE_NONE_UNDERLAY_MODE;
import static snow.skittles.SkittleLayout.SKITTLE_STANDARD_UNDERLAY_MODE;
import static snow.skittles.SkittleLayout.SkittleBackgroundMode;

/**
 * I am a factory used to build proper Skittle underlay processor which is used when Skittle is expanded.
 * Mode param can be one of the value from {@link SkittleBackgroundMode}
 */
public final class SkittleUnderlayProcessorFactory {

    /**
     * Metohd used to build proper Skittle underline processor based on given arguments.
     * @param mode defines which type of underlay processor {@link UnderlayViewProcessor} we want to build
     * @param parentContainer is a skittle container {@link snow.skittles.SkittleLayout}
     * @param standardBgColor describes the alpha channel for standard {@link snow.skittles.SkittleLayout#SKITTLE_STANDARD_UNDERLAY_MODE} mode
     * @param standardBgAlpha describes the alpha channel for standard {@link snow.skittles.SkittleLayout#SKITTLE_STANDARD_UNDERLAY_MODE} mode
     * @return proper underlay processor used by skittle container {@link snow.skittles.SkittleLayout} to show underlay for menu skittles
     */
    public static UnderlayViewProcessor build(@SkittleBackgroundMode int mode,
                                            ViewGroup parentContainer,
                                            @ColorInt int standardBgColor,
                                            @FloatRange(from = 0, to = 1) float standardBgAlpha) {

        View backgroundViewContainer;

        switch (mode) {
            case SKITTLE_STANDARD_UNDERLAY_MODE:
                backgroundViewContainer =  LayoutInflater.from(parentContainer.getContext())
                        .inflate(R.layout.view_menu_window_background, parentContainer, false);

                backgroundViewContainer.setBackgroundColor(standardBgColor);
                backgroundViewContainer.setAlpha(standardBgAlpha);

                return new StandardUnderlayViewProcessor(backgroundViewContainer);

            case SKITTLE_BLUR_UNDERLAY_MODE:
                backgroundViewContainer = LayoutInflater.from(parentContainer.getContext())
                        .inflate(R.layout.view_menu_window_blur_background, parentContainer, false);

                return new BlurUnderlayViewProcessor(parentContainer, backgroundViewContainer);

            case SKITTLE_NONE_UNDERLAY_MODE:
            default:
                return new DefaultUnderlayViewProcessor();
        }
    }

}
