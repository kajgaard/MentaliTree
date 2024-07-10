package com.example.mentalitree.ui.profile.submenus.settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mentalitree.R;
import com.example.mentalitree.databinding.FragmentProfileSettingsBinding;
import com.example.mentalitree.ui.today.ReviewFragment;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "MMSETTINGS";
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

        View root = binding.getRoot();

        resetPreferencesBtn = binding.resetPreferencesBtn;
        resetPreferencesBtn.setOnClickListener(this);

        deleteDataBtn = binding.deleteDataBtn;
        deleteDataBtn.setOnClickListener(this);

        return root;

    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "you clicked : "+ view.toString());
        if(view == deleteDataBtn){
            DeleteDataFragment deleteDataFragment = new DeleteDataFragment();
            deleteDataFragment.show(getParentFragmentManager(), "DeleteFragment");
        }
        if(view == resetPreferencesBtn){
            Toast.makeText(getContext(), "We haven't implemented any preferences to reset yet, sorry!", Toast.LENGTH_LONG).show();
        }
    }
}