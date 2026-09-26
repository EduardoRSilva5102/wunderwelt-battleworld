package com.hexinteractive.wunderwelt.ui.character;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.hexinteractive.wunderwelt.R;
import com.hexinteractive.wunderwelt.databinding.FragmentCharacterSummaryBinding;
import com.hexinteractive.wunderwelt.model.game.AttributeType;
import com.hexinteractive.wunderwelt.model.game.Attributes;
import com.hexinteractive.wunderwelt.model.game.Player;
import com.hexinteractive.wunderwelt.utils.GameManager;

public class CharacterSummaryFragment extends Fragment {
    private FragmentCharacterSummaryBinding binding;

    public CharacterSummaryFragment() {
        super(R.layout.fragment_character_summary);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentCharacterSummaryBinding.bind(view);
        CharacterViewModel viewModel = new ViewModelProvider(requireActivity()).get(CharacterViewModel.class);
        binding.playerName.setText(viewModel.getName());
        String variant = viewModel.getRaceVariant() == null ? "" : " · " + viewModel.getRaceVariant();
        binding.identitySummary.setText(
                "Raça: " + viewModel.getRace() + variant + "\n"
                        + "Classe: " + viewModel.getClassType() + "\n"
                        + "Arma: " + viewModel.getWeapon() + " (" + viewModel.getWeapon().getBattleType() + ")\n"
                        + "Origem: " + viewModel.getRegion()
        );
        Attributes a = viewModel.getAttributes();
        binding.attributesSummary.setText(
                "Força " + a.get(AttributeType.STRENGTH) + "   ·   Vitalidade " + a.get(AttributeType.VITALITY) + "\n"
                        + "Agilidade " + a.get(AttributeType.AGILITY) + "   ·   Inteligência " + a.get(AttributeType.INTELLIGENCE) + "\n"
                        + "Energia " + a.get(AttributeType.ENERGY) + "   ·   HP " + viewModel.buildPlayer().getMaxHp()
        );
        binding.editButton.setOnClickListener(ignored ->
                NavHostFragment.findNavController(this).popBackStack());
        binding.startStoryButton.setOnClickListener(ignored -> {
            Player player = viewModel.buildPlayer();
            GameManager.getInstance().startNewGame(player);
            NavHostFragment.findNavController(this).navigate(R.id.action_summary_to_story);
        });
    }

    @Override public void onDestroyView() { binding = null; super.onDestroyView(); }
}
