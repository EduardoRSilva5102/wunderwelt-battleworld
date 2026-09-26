package com.hexinteractive.wunderwelt.ui.marvel;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;
import com.hexinteractive.wunderwelt.R;
import com.hexinteractive.wunderwelt.data.model.Character;
import com.hexinteractive.wunderwelt.databinding.FragmentMarvelBinding;

public class MarvelFragment extends Fragment {
    private FragmentMarvelBinding binding;
    private MarvelViewModel viewModel;

    public MarvelFragment() {
        super(R.layout.fragment_marvel);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentMarvelBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(MarvelViewModel.class);
        binding.searchButton.setOnClickListener(ignored -> search());
        binding.searchInput.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search();
                return true;
            }
            return false;
        });
        viewModel.getState().observe(getViewLifecycleOwner(), this::render);
    }

    private void search() {
        String query = binding.searchInput.getText() == null ? "" : binding.searchInput.getText().toString();
        viewModel.search(query);
    }

    private void render(MarvelViewModel.SearchState state) {
        if (binding == null || state == null) return;
        binding.loading.setVisibility(state.loading ? View.VISIBLE : View.GONE);
        binding.searchButton.setEnabled(!state.loading);
        binding.statusText.setText(state.message);
        binding.resultsContainer.removeAllViews();
        for (Character character : state.results) {
            MaterialButton button = new MaterialButton(requireContext(), null,
                    com.google.android.material.R.attr.materialButtonOutlinedStyle);
            String subtitle = character.getDeck().isEmpty() ? "" : "\n" + character.getDeck();
            button.setText(character.getName() + subtitle);
            button.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.bottomMargin = getResources().getDimensionPixelSize(R.dimen.small_spacing);
            button.setLayoutParams(params);
            button.setOnClickListener(ignored -> {
                Bundle args = new Bundle();
                args.putLong("characterId", character.getId());
                NavHostFragment.findNavController(this).navigate(R.id.action_marvel_to_detail, args);
            });
            binding.resultsContainer.addView(button);
        }
    }

    @Override public void onDestroyView() { binding = null; super.onDestroyView(); }
}
