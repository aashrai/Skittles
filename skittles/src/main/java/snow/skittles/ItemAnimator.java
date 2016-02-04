package snow.skittles;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by aashrai on 3/2/16.
 */
public class ItemAnimator extends DefaultItemAnimator {

  private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(2);
  private static final DecelerateInterpolator DECELERATE_INTERPOLATOR =
      new DecelerateInterpolator();

  @Override public boolean animateAdd(RecyclerView.ViewHolder holder) {
    if (!(holder instanceof SkittleAdapter.MainSkittleViewHolder)) {
      runEnterAnimation(holder);
      return false;
    }
    dispatchAddFinished(holder);
    return false;
  }

  @Override public boolean animateRemove(RecyclerView.ViewHolder holder) {
    if (!(holder instanceof SkittleAdapter.MainSkittleViewHolder)) {
      runExitAnimation(holder);
      return false;
    }
    dispatchRemoveFinished(holder);
    return false;
  }

  private void runEnterAnimation(final RecyclerView.ViewHolder holder) {
    holder.itemView.setTranslationY(
        holder.itemView.getContext().getResources().getDimensionPixelOffset(R.dimen.skittle_size));
    holder.itemView.setAlpha(0);
    holder.itemView.animate()
        .translationY(0)
        .alpha(1)
        .setInterpolator(OVERSHOOT_INTERPOLATOR)
        .setListener(new Animator.AnimatorListener() {
          @Override public void onAnimationStart(Animator animation) {
            dispatchAddStarting(holder);
          }

          @Override public void onAnimationEnd(Animator animation) {
            dispatchAddFinished(holder);
          }

          @Override public void onAnimationCancel(Animator animation) {

          }

          @Override public void onAnimationRepeat(Animator animation) {

          }
        })
        .start();
  }

  private void runExitAnimation(final RecyclerView.ViewHolder holder) {
    holder.itemView.setTranslationY(0);
    holder.itemView.setAlpha(1);
    holder.itemView.animate()
        .alpha(0)
        .translationYBy(getExitTranslation(holder))
        .setInterpolator(DECELERATE_INTERPOLATOR)
        .setListener(new Animator.AnimatorListener() {
          @Override public void onAnimationStart(Animator animation) {
            dispatchRemoveStarting(holder);
          }

          @Override public void onAnimationEnd(Animator animation) {
            dispatchRemoveFinished(holder);
          }

          @Override public void onAnimationCancel(Animator animation) {

          }

          @Override public void onAnimationRepeat(Animator animation) {

          }
        })
        .start();
  }

  private int getExitTranslation(RecyclerView.ViewHolder holder) {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    ((WindowManager) holder.itemView.getContext()
        .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
    return displayMetrics.heightPixels - holder.itemView.getTop() - 2 * holder.itemView.getHeight();
  }
}