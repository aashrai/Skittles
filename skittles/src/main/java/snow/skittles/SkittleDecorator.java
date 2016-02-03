package snow.skittles;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by aashrai on 3/2/16.
 */
public class SkittleDecorator extends RecyclerView.ItemDecoration {

  @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {
    int margin;
    SkittleAdapter adapter = (SkittleAdapter) parent.getAdapter();
    switch (adapter.getItemViewType(parent.getChildAdapterPosition(view))) {
      case Constants.MAIN_SKITTLE:
        margin =
            parent.getContext().getResources().getDimensionPixelOffset(R.dimen.fab_margin_bottom);
        outRect.left = getSkittleMarginLeft(parent.getContext(), margin);
        outRect.bottom = margin;
        break;
      case Constants.SKITTLE:
        margin =
            parent.getContext().getResources().getDimensionPixelOffset(R.dimen.mini_fab_margin);
        outRect.left = getMiniSkittleMarginLeft(parent.getContext(), margin);
        outRect.bottom = margin;
        break;
    }
  }

  private int getRecyclerViewWidth(Context context) {
    return context.getResources().getDimensionPixelOffset(R.dimen.recycler_view_width);
  }

  private int getMiniSkittleMarginLeft(Context context, int margin) {
    return getRecyclerViewWidth(context) - context.getResources()
        .getDimensionPixelOffset(R.dimen.skittle_size) - margin;
  }

  private int getSkittleMarginLeft(Context context, int margin) {
    return getRecyclerViewWidth(context) - context.getResources()
        .getDimensionPixelOffset(R.dimen.skittle_size) - margin;
  }
}
