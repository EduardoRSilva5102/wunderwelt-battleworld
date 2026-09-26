package com.hexinteractive.wunderwelt.ui.battle;

import android.os.Bundle;
import android.view.View;
import android.animation.ObjectAnimator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.hexinteractive.wunderwelt.R;
import com.hexinteractive.wunderwelt.databinding.FragmentBattleBinding;
import com.hexinteractive.wunderwelt.utils.DamageCalculator;

public class BattleFragment extends Fragment {
    private FragmentBattleBinding binding;
    private BattleViewModel viewModel;
    private BattleViewModel.BattleUiState previous;

    public BattleFragment() {
        super(R.layout.fragment_battle);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentBattleBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(BattleViewModel.class);
        binding.attackButton.setOnClickListener(ignored -> viewModel.attack());
        binding.abilityButton.setOnClickListener(ignored -> {
            binding.enemyPortrait.animate().scaleX(1.08f).scaleY(1.08f).setDuration(120)
                    .withEndAction(() -> {
                        if (binding != null) binding.enemyPortrait.animate().scaleX(1f).scaleY(1f).setDuration(140);
                    }).start();
            viewModel.useAbility();
        });
        binding.defendButton.setOnClickListener(ignored -> viewModel.defend());
        viewModel.getState().observe(getViewLifecycleOwner(), this::render);
    }

    private void render(BattleViewModel.BattleUiState state) {
        if (state == null || binding == null) return;
        if (!state.valid) {
            NavHostFragment.findNavController(this).popBackStack();
            return;
        }
        binding.enemyName.setText(state.enemy.getName());
        binding.enemyType.setText("Tipo: " + state.enemy.getBattleType());
        binding.enemyPortrait.setContentDescription("Retrato provisório de " + state.enemy.getName());
        binding.typePreview.setText("Ataque: " + advantage(state, false)
                + " · Habilidade: " + advantage(state, true));
        if (previous != null && previous.valid) {
            if (state.enemyHp < previous.enemyHp) shake(binding.enemyPortrait);
            if (state.playerHp < previous.playerHp) shake(binding.playerHpText);
            if (!state.message.equals(previous.message)) {
                binding.battleLog.setAlpha(0.25f);
                binding.battleLog.animate().alpha(1f).setDuration(250).start();
            }
        }
        previous = state;
        binding.enemyHp.setMax(state.enemy.getMaxHp());
        binding.enemyHp.setProgress(state.enemyHp);
        binding.enemyHpText.setText(state.enemyHp + " / " + state.enemy.getMaxHp() + " HP");
        binding.playerHp.setMax(state.player.getMaxHp());
        binding.playerHp.setProgress(state.playerHp);
        binding.playerHpText.setText(state.player.getName() + " · " + state.playerHp + " / " + state.player.getMaxHp() + " HP");
        binding.battleLog.setText(state.message);
        binding.actionsContainer.setVisibility(state.finished ? View.GONE : View.VISIBLE);
        binding.abilityButton.setEnabled(state.abilityAvailable);
        binding.abilityButton.setText(state.abilityAvailable ? state.player.getClassType().getAbilityName() : "Habilidade utilizada");
        binding.resultButton.setVisibility(state.finished ? View.VISIBLE : View.GONE);
        if (state.finished && state.won) {
            binding.resultButton.setText("Continuar a história");
            binding.resultButton.setOnClickListener(ignored -> NavHostFragment.findNavController(this).popBackStack());
        } else if (state.finished) {
            binding.resultButton.setText("Tentar novamente");
            binding.resultButton.setOnClickListener(ignored -> viewModel.retry());
        }
    }

    private String advantage(BattleViewModel.BattleUiState state, boolean ability) {
        float multiplier = DamageCalculator.getTypeMultiplier(DamageCalculator.getAttackType(state.player, ability),
                state.enemy.getBattleType());
        return multiplier > 1f ? "↑ vantagem" : multiplier < 1f ? "↓ resistência" : "— neutro";
    }

    private void shake(View target) {
        float offset = 7f * getResources().getDisplayMetrics().density;
        ObjectAnimator.ofFloat(target, "translationX", 0f, -offset, offset, -offset / 2, 0f)
                .setDuration(220).start();
    }

    @Override public void onDestroyView() {
        binding.enemyPortrait.animate().cancel();
        binding.battleLog.animate().cancel();
        previous = null;
        binding = null;
        super.onDestroyView();
    }
}
