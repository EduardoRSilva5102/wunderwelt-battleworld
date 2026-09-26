package com.hexinteractive.wunderwelt.ui.credits;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.hexinteractive.wunderwelt.R;
import com.hexinteractive.wunderwelt.databinding.FragmentCreditsBinding;

public class CreditsFragment extends Fragment {
    private FragmentCreditsBinding binding;

    public CreditsFragment() {
        super(R.layout.fragment_credits);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentCreditsBinding.bind(view);
        binding.backButton.setOnClickListener(ignored -> NavHostFragment.findNavController(this).popBackStack());
    }

    @Override public void onDestroyView() { binding = null; super.onDestroyView(); }
}
