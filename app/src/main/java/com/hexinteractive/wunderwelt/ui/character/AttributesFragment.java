package com.hexinteractive.wunderwelt.ui.character;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;
import com.hexinteractive.wunderwelt.R;
import com.hexinteractive.wunderwelt.databinding.FragmentAttributesBinding;
import com.hexinteractive.wunderwelt.model.game.AttributeType;
import com.hexinteractive.wunderwelt.model.game.Attributes;

public class AttributesFragment extends Fragment {
    private FragmentAttributesBinding binding;
    private CharacterViewModel viewModel;

    public AttributesFragment() {
        super(R.layout.fragment_attributes);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentAttributesBinding.bind(view);
        viewModel = new ViewModelProvider(requireActivity()).get(CharacterViewModel.class);
        bindControls();
        render();
        binding.nextButton.setOnClickListener(ignored -> {
            if (!viewModel.getAttributes().isComplete()) {
                Snackbar.make(binding.getRoot(), "Distribua todos os 10 pontos.", Snackbar.LENGTH_SHORT).show();
                return;
            }
            NavHostFragment.findNavController(this).navigate(R.id.action_attributes_to_summary);
        });
    }

    private void bindControls() {
        binding.strengthPlus.setOnClickListener(v -> change(AttributeType.STRENGTH, true));
        binding.strengthMinus.setOnClickListener(v -> change(AttributeType.STRENGTH, false));
        binding.vitalityPlus.setOnClickListener(v -> change(AttributeType.VITALITY, true));
        binding.vitalityMinus.setOnClickListener(v -> change(AttributeType.VITALITY, false));
        binding.agilityPlus.setOnClickListener(v -> change(AttributeType.AGILITY, true));
        binding.agilityMinus.setOnClickListener(v -> change(AttributeType.AGILITY, false));
        binding.intelligencePlus.setOnClickListener(v -> change(AttributeType.INTELLIGENCE, true));
        binding.intelligenceMinus.setOnClickListener(v -> change(AttributeType.INTELLIGENCE, false));
        binding.energyPlus.setOnClickListener(v -> change(AttributeType.ENERGY, true));
        binding.energyMinus.setOnClickListener(v -> change(AttributeType.ENERGY, false));
    }

    private void change(AttributeType type, boolean increase) {
        if (increase) viewModel.increaseAttribute(type); else viewModel.decreaseAttribute(type);
        render();
    }

    private void render() {
        Attributes attributes = viewModel.getAttributes();
        binding.remainingPoints.setText("Pontos restantes: " + attributes.getRemainingPoints());
        binding.strengthValue.setText(String.valueOf(attributes.get(AttributeType.STRENGTH)));
        binding.vitalityValue.setText(String.valueOf(attributes.get(AttributeType.VITALITY)));
        binding.agilityValue.setText(String.valueOf(attributes.get(AttributeType.AGILITY)));
        binding.intelligenceValue.setText(String.valueOf(attributes.get(AttributeType.INTELLIGENCE)));
        binding.energyValue.setText(String.valueOf(attributes.get(AttributeType.ENERGY)));
        binding.nextButton.setEnabled(attributes.isComplete());
    }

    @Override public void onDestroyView() { binding = null; super.onDestroyView(); }
}
