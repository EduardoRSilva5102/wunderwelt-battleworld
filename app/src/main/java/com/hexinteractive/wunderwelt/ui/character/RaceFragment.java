package com.hexinteractive.wunderwelt.ui.character;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.hexinteractive.wunderwelt.R;
import com.hexinteractive.wunderwelt.databinding.FragmentRaceBinding;
import com.hexinteractive.wunderwelt.model.game.Race;
import com.hexinteractive.wunderwelt.model.game.RaceVariant;

import java.util.Arrays;
import java.util.List;

public class RaceFragment extends Fragment {
    private FragmentRaceBinding binding;
    private CharacterViewModel viewModel;

    public RaceFragment() {
        super(R.layout.fragment_race);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentRaceBinding.bind(view);
        viewModel = new ViewModelProvider(requireActivity()).get(CharacterViewModel.class);
        binding.nameInput.setText(viewModel.getName());

        List<Race> races = Arrays.asList(Race.values());
        ArrayAdapter<Race> adapter = spinnerAdapter(races);
        binding.raceSpinner.setAdapter(adapter);
        binding.raceSpinner.setSelection(races.indexOf(viewModel.getRace()));
        binding.raceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onNothingSelected(AdapterView<?> parent) { }
            @Override public void onItemSelected(AdapterView<?> parent, View selected, int position, long id) {
                Race race = races.get(position);
                viewModel.setRace(race);
                binding.raceDescription.setText(race.getDescription());
                showVariants(race);
            }
        });

        binding.nextButton.setOnClickListener(ignored -> {
            String name = binding.nameInput.getText() == null ? "" : binding.nameInput.getText().toString().trim();
            if (name.isEmpty()) {
                binding.nameInputLayout.setError("Informe o nome do protagonista.");
                return;
            }
            binding.nameInputLayout.setError(null);
            viewModel.setName(name);
            NavHostFragment.findNavController(this).navigate(R.id.action_race_to_class);
        });
    }

    private void showVariants(Race race) {
        List<RaceVariant> variants = RaceVariant.forRace(race);
        boolean hasVariants = !variants.isEmpty();
        binding.variantLabel.setVisibility(hasVariants ? View.VISIBLE : View.GONE);
        binding.variantSpinner.setVisibility(hasVariants ? View.VISIBLE : View.GONE);
        if (!hasVariants) {
            viewModel.setRaceVariant(null);
            return;
        }
        ArrayAdapter<RaceVariant> adapter = spinnerAdapter(variants);
        binding.variantSpinner.setAdapter(adapter);
        int selected = variants.indexOf(viewModel.getRaceVariant());
        binding.variantSpinner.setSelection(Math.max(0, selected));
        binding.variantSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onNothingSelected(AdapterView<?> parent) { }
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setRaceVariant(variants.get(position));
            }
        });
    }

    private <T> ArrayAdapter<T> spinnerAdapter(List<T> values) {
        ArrayAdapter<T> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
