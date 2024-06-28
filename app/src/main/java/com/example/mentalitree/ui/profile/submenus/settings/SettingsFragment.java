package com.example.mentalitree.ui.profile.submenus.settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mentalitree.R;
import com.example.mentalitree.databinding.FragmentProfileSettingsBinding;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    Button resetPreferencesBtn, deleteDataBtn;
    private FragmentProfileSettingsBinding binding;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileSettingsBinding.inflate(inflater, container, false);

        //View root = binding.getRoot();

        resetPreferencesBtn = binding.resetPreferencesBtn;
        resetPreferencesBtn.setOnClickListener(this);

        deleteDataBtn = binding.deleteDataBtn;
        deleteDataBtn.setOnClickListener(this);

        return inflater.inflate(R.layout.fragment_profile_settings, container, false);

    }

    @Override
    public void onClick(View view) {

        if(view == deleteDataBtn){
            //TODO: delete data
        }else if(view == resetPreferencesBtn){
            //TODO: reset preferences
        }
    }
}