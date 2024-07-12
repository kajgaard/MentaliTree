package com.example.mentalitree.ui.profile.submenus.settings;

import static androidx.core.app.ActivityCompat.finishAffinity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mentalitree.DataHandler;
import com.example.mentalitree.LogInActivity;
import com.example.mentalitree.R;
import com.example.mentalitree.databinding.FragmentDeleteDataBinding;


public class DeleteDataFragment extends DialogFragment {

    FragmentDeleteDataBinding binding;

    RadioGroup radioGroup;
    boolean acceptFlag = false;
    Button confirmBtn;
    DataHandler dataHandler = DataHandler.getInstance();

    public DeleteDataFragment() {
        // Required empty public constructor
    }

    @SuppressLint("NonConstantResourceId")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Inflate and set the layout for the dialog
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_delete_data, null);
        binding = FragmentDeleteDataBinding.bind(view);

        radioGroup = binding.deleteRadioGroup;
        confirmBtn = binding.confirmBtn;
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(acceptFlag){
                    Toast.makeText(getContext(), "will delete", Toast.LENGTH_SHORT).show();
                    dataHandler.deleteDataFromDatabase(flag -> {
                        dismiss();
                        Intent intent = new Intent(getContext(), LogInActivity.class);
                        startActivity(intent);
                        getActivity().finish();

                    });

                }else{
                    Toast.makeText(getContext(), "wont delete", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int idRadioButtonChosen = radioGroup.getCheckedRadioButtonId();

            if(idRadioButtonChosen == R.id.yesDeleteRadio){
                acceptFlag = true;
                Drawable drawable = getResources().getDrawable(R.drawable.red_btn_bg);
                confirmBtn.setBackground(drawable);
                confirmBtn.setText("Delete my data & log out");
            } else if (idRadioButtonChosen == R.id.dontDeleteRadio) {

                acceptFlag = false;
                Drawable drawable = getResources().getDrawable(R.drawable.green_btn_bg);
                confirmBtn.setBackground(drawable);
                confirmBtn.setText("Keep my data");
            }

        });


        builder.setView(view);
        return builder.create();
    }

    public static DeleteDataFragment newInstance(String param1, String param2) {
        DeleteDataFragment fragment = new DeleteDataFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delete_data, container, false);
    }
}