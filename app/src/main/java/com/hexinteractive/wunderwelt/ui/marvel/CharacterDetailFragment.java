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
import com.hexinteractive.wunderwelt.data.model.CharacterPair;
import com.hexinteractive.wunderwelt.data.repository.CharacterCatalog;
import com.hexinteractive.wunderwelt.databinding.FragmentCharacterDetailBinding;

public class CharacterDetailFragment extends Fragment {
    private FragmentCharacterDetailBinding binding;
    private Character current;
    private boolean battleworld;
    private final RemotePortraitLoader portraitLoader = new RemotePortraitLoader();

    public CharacterDetailFragment() {
        super(R.layout.fragment_character_detail);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentCharacterDetailBinding.bind(view);
        battleworld = savedInstanceState != null && savedInstanceState.getBoolean("battleworld");
        binding.versionToggle.setOnClickListener(v -> {
            battleworld = !battleworld;
            if (current != null) renderCharacter(current);
        });
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
        if (character == null) return;
        current = character;
        CharacterPair pair = CharacterCatalog.find(character.getId());
        portraitLoader.cancel();
        binding.portrait.setImageResource(battleworld ? R.drawable.enemy_guard_default : R.drawable.character_traveler_portrait);
        binding.versionToggle.setText(battleworld ? "Ver versão normal" : "Ver versão Battleworld");
        binding.versionLabel.setText(battleworld ? "BATTLEWORLD · SECRET WARS (2015)" : "NORMAL · REFERÊNCIA TERRA-616");
        if (battleworld && pair != null) {
            binding.characterName.setText(pair.battleworldName);
            binding.characterDeck.setText(pair.region);
            binding.characterMetadata.setText("Versão curada para o arquivo do viajante");
            binding.characterDescription.setText(pair.lore);
            binding.sourceLabel.setText("Lore original local · retrato XML provisório");
            binding.portrait.setContentDescription("Retrato provisório de " + pair.battleworldName);
            binding.sourceButton.setVisibility(View.GONE);
            return;
        }
        binding.sourceLabel.setText(character.isRemote() ? "Dados: Comic Vine · imagem remota quando disponível"
                : "Fallback local · dados online opcionais, sujeitos a chave, conexão e disponibilidade");
        binding.portrait.setContentDescription("Retrato de " + character.getName());
        if (character.isRemote()) portraitLoader.load(character.getImageUrl(), binding.portrait);
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
        String html = character.isRemote() ? "Registro principal da Comic Vine. Os dados podem incluir referências a outras versões; a lore de Battleworld fica na aba local."
                : character.getDescription();
        binding.characterDescription.setText(HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT));
        Uri source = Uri.parse(character.getSiteDetailUrl());
        boolean hasSource = "https".equals(source.getScheme()) && "comicvine.gamespot.com".equals(source.getHost());
        binding.sourceButton.setVisibility(hasSource ? View.VISIBLE : View.GONE);
        if (hasSource) {
            binding.sourceButton.setOnClickListener(ignored ->
                    {
                        try { startActivity(new Intent(Intent.ACTION_VIEW, source)); }
                        catch (android.content.ActivityNotFoundException unavailable) {
                            binding.sourceLabel.setText("Nenhum navegador disponível neste dispositivo.");
                        }
                    });
        }
    }

    @Override public void onSaveInstanceState(@NonNull Bundle state) {
        state.putBoolean("battleworld", battleworld);
        super.onSaveInstanceState(state);
    }
    @Override public void onDestroyView() { portraitLoader.cancel(); binding = null; super.onDestroyView(); }
}
