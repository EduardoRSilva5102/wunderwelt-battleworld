package com.hexinteractive.wunderwelt.ui.marvel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hexinteractive.wunderwelt.R;
import com.hexinteractive.wunderwelt.data.model.Character;
import com.hexinteractive.wunderwelt.data.model.Issue;
import com.hexinteractive.wunderwelt.data.model.Power;
import com.hexinteractive.wunderwelt.databinding.FragmentCharacterDetailBinding;

public class CharacterDetailFragment extends Fragment {
    private FragmentCharacterDetailBinding binding;

    public CharacterDetailFragment() {
        super(R.layout.fragment_character_detail);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentCharacterDetailBinding.bind(view);
        long characterId = requireArguments().getLong("characterId");
        CharacterDetailViewModel viewModel = new ViewModelProvider(this).get(CharacterDetailViewModel.class);
        viewModel.getState().observe(getViewLifecycleOwner(), state -> {
            if (state == null || binding == null) return;
            if (state.loading) {
                binding.characterName.setText("Carregando...");
                binding.characterDescription.setText("Consultando a Comic Vine.");
            } else if (state.error != null) {
                binding.characterName.setText("Arquivo indisponível");
                binding.characterDescription.setText(state.error);
            } else {
                renderCharacter(state.character);
            }
        });
        viewModel.load(characterId);
    }

    private void renderCharacter(Character character) {
        binding.characterName.setText(character.getName());
        binding.characterDeck.setText(character.getDeck());
        StringBuilder metadata = new StringBuilder();
        Issue firstIssue = character.getFirstAppearedInIssue();
        if (firstIssue != null) {
            metadata.append("Primeira aparição: ").append(firstIssue.getName());
            if (!firstIssue.getIssueNumber().isEmpty()) metadata.append(" #").append(firstIssue.getIssueNumber());
        }
        if (!character.getPowers().isEmpty()) {
            if (metadata.length() > 0) metadata.append("\n");
            metadata.append("Poderes: ");
            int count = Math.min(6, character.getPowers().size());
            for (int i = 0; i < count; i++) {
                Power power = character.getPowers().get(i);
                if (i > 0) metadata.append(", ");
                metadata.append(power.getName());
            }
        }
        binding.characterMetadata.setText(metadata.length() == 0 ? "Metadados não informados." : metadata.toString());
        String html = character.getDescription().isEmpty() ? "Descrição não informada pela Comic Vine." : character.getDescription();
        binding.characterDescription.setText(HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT));
        boolean hasSource = character.getSiteDetailUrl().startsWith("https://");
        binding.sourceButton.setVisibility(hasSource ? View.VISIBLE : View.GONE);
        if (hasSource) {
            binding.sourceButton.setOnClickListener(ignored ->
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(character.getSiteDetailUrl()))));
        }
    }

    @Override public void onDestroyView() { binding = null; super.onDestroyView(); }
}
