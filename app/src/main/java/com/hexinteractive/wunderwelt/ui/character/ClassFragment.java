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
import com.hexinteractive.wunderwelt.databinding.FragmentClassBinding;
import com.hexinteractive.wunderwelt.model.game.ClassType;
import com.hexinteractive.wunderwelt.model.game.Weapon;

import java.util.Arrays;
import java.util.List;

public class ClassFragment extends Fragment {
    private FragmentClassBinding binding;
    private CharacterViewModel viewModel;

    public ClassFragment() {
        super(R.layout.fragment_class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentClassBinding.bind(view);
        viewModel = new ViewModelProvider(requireActivity()).get(CharacterViewModel.class);
        List<ClassType> classes = Arrays.asList(ClassType.values());
        binding.classSpinner.setAdapter(spinnerAdapter(classes));
        binding.classSpinner.setSelection(classes.indexOf(viewModel.getClassType()));
        binding.classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onNothingSelected(AdapterView<?> parent) { }
            @Override public void onItemSelected(AdapterView<?> parent, View selected, int position, long id) {
                ClassType classType = classes.get(position);
                viewModel.setClassType(classType);
                binding.classDescription.setText(classType.getDescription());
                showWeapons(classType);
            }
        });
        binding.nextButton.setOnClickListener(ignored ->
                NavHostFragment.findNavController(this).navigate(R.id.action_class_to_region));
    }

    private void showWeapons(ClassType classType) {
        List<Weapon> weapons = Weapon.forClass(classType);
        binding.weaponSpinner.setAdapter(spinnerAdapter(weapons));
        int selected = weapons.indexOf(viewModel.getWeapon());
        binding.weaponSpinner.setSelection(Math.max(0, selected));
        binding.weaponSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onNothingSelected(AdapterView<?> parent) { }
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Weapon weapon = weapons.get(position);
                viewModel.setWeapon(weapon);
                binding.weaponDescription.setText(weapon.getBattleType() + " · poder " + weapon.getPower());
            }
        });
    }

    private <T> ArrayAdapter<T> spinnerAdapter(List<T> values) {
        ArrayAdapter<T> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    @Override public void onDestroyView() { binding = null; super.onDestroyView(); }
}
