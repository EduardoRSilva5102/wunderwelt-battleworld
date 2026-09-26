package com.hexinteractive.wunderwelt.ui.battle;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.hexinteractive.wunderwelt.R;
import com.hexinteractive.wunderwelt.databinding.FragmentBattleBinding;

public class BattleFragment extends Fragment {
    private FragmentBattleBinding binding;
    private BattleViewModel viewModel;

    public BattleFragment() {
        super(R.layout.fragment_battle);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentBattleBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(BattleViewModel.class);
        binding.attackButton.setOnClickListener(ignored -> viewModel.attack());
        binding.abilityButton.setOnClickListener(ignored -> viewModel.useAbility());
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
        binding.enemyHp.setMax(state.enemy.getMaxHp());
        binding.enemyHp.setProgress(state.enemyHp);
        binding.enemyHpText.setText(state.enemyHp + " / " + state.enemy.getMaxHp() + " HP");
        binding.playerHp.setMax(state.player.getMaxHp());
        binding.playerHp.setProgress(state.playerHp);
        binding.playerHpText.setText(state.player.getName() + " · " + state.playerHp + " / " + state.player.getMaxHp() + " HP");
        binding.battleLog.setText(state.message);
        binding.actionsContainer.setVisibility(state.finished ? View.GONE : View.VISIBLE);
        binding.abilityButton.setEnabled(state.abilityAvailable);
        binding.abilityButton.setText(state.abilityAvailable ? "Habilidade" : "Habilidade utilizada");
        binding.resultButton.setVisibility(state.finished ? View.VISIBLE : View.GONE);
        if (state.finished && state.won) {
            binding.resultButton.setText("Continuar a história");
            binding.resultButton.setOnClickListener(ignored -> NavHostFragment.findNavController(this).popBackStack());
        } else if (state.finished) {
            binding.resultButton.setText("Tentar novamente");
            binding.resultButton.setOnClickListener(ignored -> viewModel.retry());
        }
    }

    @Override public void onDestroyView() { binding = null; super.onDestroyView(); }
}
