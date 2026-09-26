package com.hexinteractive.wunderwelt.ui.intro;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.hexinteractive.wunderwelt.R;
import com.hexinteractive.wunderwelt.databinding.FragmentIntroBinding;
import com.hexinteractive.wunderwelt.ui.character.CharacterViewModel;
import com.hexinteractive.wunderwelt.utils.GameManager;

public class IntroFragment extends Fragment {
    private FragmentIntroBinding binding;

    public IntroFragment() {
        super(R.layout.fragment_intro);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentIntroBinding.bind(view);
        binding.startButton.setOnClickListener(ignored -> {
            new ViewModelProvider(requireActivity()).get(CharacterViewModel.class).reset();
            GameManager.getInstance().clearGame();
            NavHostFragment.findNavController(this).navigate(R.id.action_intro_to_race);
        });
        binding.archiveButton.setOnClickListener(ignored ->
                NavHostFragment.findNavController(this).navigate(R.id.action_intro_to_marvel));
        binding.creditsButton.setOnClickListener(ignored ->
                NavHostFragment.findNavController(this).navigate(R.id.action_intro_to_credits));
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
