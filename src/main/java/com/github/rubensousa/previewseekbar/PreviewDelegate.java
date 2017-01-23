package com.github.rubensousa.previewseekbar;


import android.os.Build;
import android.view.View;
import android.widget.SeekBar;

class PreviewDelegate implements SeekBar.OnSeekBarChangeListener {

    private PreviewSeekBarLayout previewSeekBarLayout;
    private PreviewAnimator animator;
    private boolean showing;

    public PreviewDelegate(PreviewSeekBarLayout previewSeekBarLayout) {
        this.previewSeekBarLayout = previewSeekBarLayout;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.animator = new PreviewAnimatorLollipopImpl(previewSeekBarLayout);
        } else {
            this.animator = new PreviewAnimatorImpl(previewSeekBarLayout);
        }

        previewSeekBarLayout.getPreviewFrameLayout().setVisibility(View.INVISIBLE);
        previewSeekBarLayout.getMorphView().setVisibility(View.INVISIBLE);
        previewSeekBarLayout.getFrameView().setVisibility(View.INVISIBLE);
        previewSeekBarLayout.getSeekBar().addOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        animator.move();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        animator.cancel();

        if (!showing) {
            animator.show();
        }
        showing = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        animator.cancel();

        if (showing) {
            animator.hide();
        }
        showing = false;
    }

}
