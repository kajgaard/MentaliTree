package com.example.mentalitree.ui.profile.submenus.workbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mentalitree.PrivateDataHandler;
import com.example.mentalitree.R;
import com.example.mentalitree.databinding.FragmentWorkbookShowNoteBinding;
import com.example.mentalitree.ui.today.TaskModel;


public class WorkbookShowNoteFragment extends DialogFragment {


    ImageView categoryLogoIv;
    TextView workbookTaskTitleTv, workbookNoteTv;
    ImageView closeDialogBtnIv;
    FragmentWorkbookShowNoteBinding binding;
    TaskModel clickedTask;
    PrivateDataHandler privateDataHandler = PrivateDataHandler.getInstance();

    public WorkbookShowNoteFragment() {
        // Required empty public constructor
    }

    public WorkbookShowNoteFragment(TaskModel workbookTask) {
        this.clickedTask = workbookTask;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Inflate and set the layout for the dialog
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_workbook_show_note, null);

        binding = FragmentWorkbookShowNoteBinding.bind(view);

        categoryLogoIv = binding.categoryIconIv;
        workbookTaskTitleTv = binding.shortDescriptionForTaskTv;
        workbookNoteTv = binding.userInputWorkbookTaskTv;
        closeDialogBtnIv = binding.closeDialogIv;
        closeDialogBtnIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        categoryLogoIv.setImageResource(clickedTask.getMatchingCategoryIcon(clickedTask.getCategory()));
        workbookTaskTitleTv.setText(clickedTask.getTaskShortDescription());
        workbookNoteTv.setText(privateDataHandler.decryptString(clickedTask.getUserInputNote()));

        builder.setView(view);
        return builder.create();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Inflate the layout for this fragment
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return inflater.inflate(R.layout.fragment_review, container, false);
    }


}