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
import com.hexinteractive.wunderwelt.databinding.FragmentRegionBinding;
import com.hexinteractive.wunderwelt.model.game.Region;

import java.util.ArrayList;
import java.util.List;

public class RegionFragment extends Fragment {
    private FragmentRegionBinding binding;
    private CharacterViewModel viewModel;

    public RegionFragment() {
        super(R.layout.fragment_region);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentRegionBinding.bind(view);
        viewModel = new ViewModelProvider(requireActivity()).get(CharacterViewModel.class);
        List<Region> regions = new ArrayList<>();
        for (Region region : Region.values()) if (region.isStartingRegion()) regions.add(region);
        ArrayAdapter<Region> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, regions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.regionSpinner.setAdapter(adapter);
        binding.regionSpinner.setSelection(regions.indexOf(viewModel.getRegion()));
        binding.regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onNothingSelected(AdapterView<?> parent) { }
            @Override public void onItemSelected(AdapterView<?> parent, View selected, int position, long id) {
                Region region = regions.get(position);
                viewModel.setRegion(region);
                binding.regionDescription.setText(region.getDescription());
            }
        });
        binding.nextButton.setOnClickListener(ignored ->
                NavHostFragment.findNavController(this).navigate(R.id.action_region_to_attributes));
    }

    @Override public void onDestroyView() { binding = null; super.onDestroyView(); }
}
