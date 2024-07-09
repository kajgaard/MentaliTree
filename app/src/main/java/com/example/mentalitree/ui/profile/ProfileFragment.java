package com.example.mentalitree.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.mentalitree.DataHandler;
import com.example.mentalitree.R;
import com.example.mentalitree.databinding.FragmentProfileBinding;
import com.example.mentalitree.ui.profile.submenus.notes.NotesFragment;
import com.example.mentalitree.ui.profile.submenus.settings.SettingsFragment;
import com.example.mentalitree.ui.profile.submenus.statistics.StatisticsFragment;
import com.example.mentalitree.ui.profile.submenus.workbook.WorkbookFragment;

import java.util.ArrayList;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    private FragmentProfileBinding binding;
    private Button settingsBtn, notesBtn, workbookBtn, statisticsBtn;
    ImageView avatarIv;
    DataHandler dataHandler = DataHandler.getInstance();


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
        avatarIv = binding.profileAvatarIv;

        switch (dataHandler.getAvatarPref()){
            case 1:
                avatarIv.setImageResource(R.drawable.bear_profile_avatar);
                break;

            case 2:
                avatarIv.setImageResource(R.drawable.panda_profile_avatar);
                break;

            case 3:
                avatarIv.setImageResource(R.drawable.bunny_profile_avatar);
                break;

            default:
                avatarIv.setImageResource(R.drawable.bear_profile_avatar);
        }




        // on below line creating a child fragment
        Fragment childFragment = new WorkbookFragment();

        // on below line creating a fragment transaction and initializing it.
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // on below line replacing the fragment in child container with child fragment.
        transaction.replace(R.id.childFragmentContainer, childFragment).commit();

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
            // on below line creating a child fragment
            Fragment childFragment = new SettingsFragment();

            // on below line creating a fragment transaction and initializing it.
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

            // on below line replacing the fragment in child container with child fragment.
            transaction.replace(R.id.childFragmentContainer, childFragment).commit();

            updateUiForSubMenuShift(settingsBtn);

        }else if (view == notesBtn){
            // on below line creating a child fragment
            Fragment childFragment = new NotesFragment();

            // on below line creating a fragment transaction and initializing it.
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

            // on below line replacing the fragment in child container with child fragment.
            transaction.replace(R.id.childFragmentContainer, childFragment).commit();

            updateUiForSubMenuShift(notesBtn);
        }else if (view == workbookBtn){
            // on below line creating a child fragment
            Fragment childFragment = new WorkbookFragment();

            // on below line creating a fragment transaction and initializing it.
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

            // on below line replacing the fragment in child container with child fragment.
            transaction.replace(R.id.childFragmentContainer, childFragment).commit();

            updateUiForSubMenuShift(workbookBtn);
        }else if (view == statisticsBtn){
            // on below line creating a child fragment
            Fragment childFragment = new StatisticsFragment();

            // on below line creating a fragment transaction and initializing it.
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

            // on below line replacing the fragment in child container with child fragment.
            transaction.replace(R.id.childFragmentContainer, childFragment).commit();

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