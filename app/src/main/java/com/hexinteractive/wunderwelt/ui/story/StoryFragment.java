package com.hexinteractive.wunderwelt.ui.story;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.hexinteractive.wunderwelt.R;
import com.hexinteractive.wunderwelt.databinding.FragmentStoryBinding;
import com.hexinteractive.wunderwelt.model.story.StoryScene;

public class StoryFragment extends Fragment {
    private FragmentStoryBinding binding;
    private StoryViewModel viewModel;

    public StoryFragment() {
        super(R.layout.fragment_story);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentStoryBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(StoryViewModel.class);
        if (!viewModel.hasGame()) {
            restart();
            return;
        }
        binding.choiceOneButton.setOnClickListener(ignored -> handleChoice(0));
        binding.statusButton.setOnClickListener(ignored -> {
            Bundle args = new Bundle();
            args.putBoolean("readOnly", true);
            NavHostFragment.findNavController(this).navigate(R.id.characterSummaryFragment, args);
        });
        binding.choiceTwoButton.setOnClickListener(ignored -> handleChoice(1));
        binding.archiveButton.setOnClickListener(ignored ->
                NavHostFragment.findNavController(this).navigate(R.id.action_story_to_marvel));
        render();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (viewModel != null && viewModel.hasGame()) {
            viewModel.applyBattleResult();
            if (viewModel.hasPendingBattle()) {
                NavHostFragment.findNavController(this).navigate(R.id.action_story_to_battle);
                return;
            }
            render();
        }
    }

    private void handleChoice(int index) {
        StoryViewModel.StoryAction action = viewModel.choose(index);
        if (action == StoryViewModel.StoryAction.BATTLE) {
            NavHostFragment.findNavController(this).navigate(R.id.action_story_to_battle);
        } else if (action == StoryViewModel.StoryAction.RESTART) {
            restart();
        } else {
            render();
        }
    }

    private void render() {
        if (binding == null || !viewModel.hasGame()) return;
        StoryScene scene = viewModel.getCurrentScene();
        binding.chapterLabel.setText(viewModel.getChapterLabel());
        binding.sceneTitle.setText(scene.getTitle());
        binding.narrator.setText(scene.getNarrator());
        binding.storyText.setText(scene.getText());
        binding.choiceOneButton.setVisibility(scene.getChoices().isEmpty() ? View.GONE : View.VISIBLE);
        if (!scene.getChoices().isEmpty()) binding.choiceOneButton.setText(scene.getChoices().get(0).getLabel());
        boolean secondChoice = scene.getChoices().size() > 1;
        binding.choiceTwoButton.setVisibility(secondChoice ? View.VISIBLE : View.GONE);
        if (secondChoice) binding.choiceTwoButton.setText(scene.getChoices().get(1).getLabel());
    }

    private void restart() {
        NavOptions options = new NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build();
        NavHostFragment.findNavController(this).navigate(R.id.introFragment, null, options);
    }

    @Override public void onDestroyView() { binding = null; super.onDestroyView(); }
}
