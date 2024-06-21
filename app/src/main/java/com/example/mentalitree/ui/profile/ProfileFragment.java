package com.example.mentalitree.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mentalitree.R;
import com.example.mentalitree.databinding.FragmentProfileBinding;

import java.util.ArrayList;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private FragmentProfileBinding binding;
    private Button settingsBtn, notesBtn, workbookBtn, statisticsBtn;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        settingsBtn = binding.settingsBtn;
        settingsBtn.setOnClickListener(this);
        notesBtn = binding.notesBtn;
        notesBtn.setOnClickListener(this);
        workbookBtn = binding.workbookBtn;
        workbookBtn.setOnClickListener(this);
        statisticsBtn = binding.statisticsBtn;
        statisticsBtn.setOnClickListener(this);

        final TextView textView = binding.textNotifications;
        profileViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        if (view == settingsBtn){
            updateUiForSubMenuShift(settingsBtn);
        }else if (view == notesBtn){
            updateUiForSubMenuShift(notesBtn);
        }else if (view == workbookBtn){
            updateUiForSubMenuShift(workbookBtn);
        }else if (view == statisticsBtn){
            updateUiForSubMenuShift(statisticsBtn);
        }
    }

    void updateUiForSubMenuShift(Button chosen){
        ArrayList<Button> subMenus = new ArrayList<Button>();
        subMenus.add(settingsBtn);
        subMenus.add(notesBtn);
        subMenus.add(workbookBtn);
        subMenus.add(statisticsBtn);

        subMenus.remove(chosen);

        chosen.setBackgroundResource(R.drawable.active_submenu_bg);
        chosen.setTextColor(getResources().getColor(R.color.white));

        for (Button submenuBtn : subMenus){
            submenuBtn.setBackgroundResource(R.drawable.inactive_submenu_bg);
            submenuBtn.setTextColor(getResources().getColor(R.color.light_grey));
        }

    }


}