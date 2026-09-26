package com.hexinteractive.wunderwelt.ui.intro;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.hexinteractive.wunderwelt.R;
import com.hexinteractive.wunderwelt.databinding.FragmentOpeningBinding;

/** The system splash precedes this screen; the old IntroFragment is now the menu. */
public class OpeningFragment extends Fragment {
    private FragmentOpeningBinding binding;
    private ValueAnimator animator;
    private float fraction;
    private boolean leaving;
    public OpeningFragment() { super(R.layout.fragment_opening); }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = FragmentOpeningBinding.bind(view);
        fraction = savedInstanceState == null ? 0f : savedInstanceState.getFloat("crawlProgress", 0f);
        leaving = false;
        binding.skipButton.setOnClickListener(v -> finish());
        binding.crawlText.setAlpha(0f);
    }

    @Override public void onResume() {
        super.onResume();
        binding.crawlViewport.post(() -> {
            if (binding == null || !isResumed() || leaving) return;
            if (animator != null) { animator.resume(); return; }
            // FrameLayout constrains wrap_content to its viewport. Measure the entire
            // text explicitly so the last paragraphs can scroll into view on small screens.
            binding.crawlText.measure(View.MeasureSpec.makeMeasureSpec(binding.crawlViewport.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            android.view.ViewGroup.LayoutParams params = binding.crawlText.getLayoutParams();
            params.height = binding.crawlText.getMeasuredHeight();
            binding.crawlText.setLayoutParams(params);
            animator = ValueAnimator.ofFloat(fraction, 1f);
            animator.setDuration((long) (42000 * (1f - fraction)));
            animator.setInterpolator(new LinearInterpolator());
            animator.addUpdateListener(value -> {
                if (binding == null) return;
                fraction = (float) value.getAnimatedValue();
                float height = binding.crawlViewport.getHeight();
                binding.crawlText.setTranslationY(height - fraction * (height + binding.crawlText.getHeight()));
                binding.crawlText.setAlpha(1f);
            });
            animator.addListener(new AnimatorListenerAdapter() {
                private boolean cancelled;
                @Override public void onAnimationCancel(Animator animation) { cancelled = true; }
                @Override public void onAnimationEnd(Animator animation) { if (!cancelled) finish(); }
            });
            animator.start();
        });
    }

    private void finish() {
        if (binding == null || leaving || !isResumed()) return;
        leaving = true;
        if (animator != null) animator.cancel();
        NavHostFragment.findNavController(this).navigate(R.id.action_opening_to_menu);
    }
    @Override public void onPause() {
        if (animator != null) animator.pause();
        super.onPause();
    }
    @Override public void onSaveInstanceState(@NonNull Bundle state) {
        state.putFloat("crawlProgress", fraction);
        super.onSaveInstanceState(state);
    }
    @Override public void onDestroyView() {
        if (animator != null) animator.cancel();
        animator = null;
        binding = null;
        super.onDestroyView();
    }
}
