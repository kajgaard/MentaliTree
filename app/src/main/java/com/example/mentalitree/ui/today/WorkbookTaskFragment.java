package com.example.mentalitree.ui.today;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mentalitree.R;
import com.example.mentalitree.databinding.FragmentProfileSettingsBinding;
import com.example.mentalitree.databinding.FragmentWorkbookTaskBinding;
import com.example.mentalitree.databinding.WorkbookTaskBinding;

public class WorkbookTaskFragment extends DialogFragment implements View.OnClickListener {


    TextView doneBtnTv, taskDescTv;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Inflate and set the layout for the dialog
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_workbook_task, null);

        binding = FragmentWorkbookTaskBinding.bind(view);

        doneBtnTv = binding.doneWithTaskTv;
        hintText = taskObject.getHelpText();
        closeBtnIv = binding.closeDialogIv;
        taskDescTv = binding.shortDescriptionForTaskTv;
        taskLogoIv = binding.categoryIconIv;
        inputFieldEt = binding.userInputWorkbookTaskEt;

        doneBtnTv.setOnClickListener(this);
        inputFieldEt.setHint(hintText);
        closeBtnIv.setOnClickListener(this);
        taskDescTv.setText(taskObject.getTaskShortDescription());
        taskLogoIv.setImageResource(taskObject.getImage());

        builder.setView(view);
        return builder.create();
        //return super.onCreateDialog(savedInstanceState);
    }

    String hintText;
    ImageView closeBtnIv, taskLogoIv;
    EditText inputFieldEt;
    FragmentWorkbookTaskBinding binding;
    TaskModel taskObject;


    public WorkbookTaskFragment() {
        // Required empty public constructor
    }
    public WorkbookTaskFragment(TaskModel taskModel) {
        this.taskObject = taskModel;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //binding = FragmentWorkbookTaskBinding.inflate(inflater, container, false);
//
        //doneBtnTv = binding.doneWithTaskTv;
        //hintText = taskObject.getHelpText();
        //closeBtnIv = binding.closeDialogIv;
        //taskDescTv = binding.shortDescriptionForTaskTv;
        //taskLogoIv = binding.categoryIconIv;
        //inputFieldEt = binding.userInputWorkbookTaskEt;
//
        //doneBtnTv.setOnClickListener(this);
        //inputFieldEt.setHint(hintText);
        //closeBtnIv.setOnClickListener(this);
        //taskDescTv.setText(taskObject.getTaskShortDescription());
        //taskLogoIv.setImageResource(taskObject.getImage());
//
        return inflater.inflate(R.layout.fragment_workbook_task, container, false);


    }

    @Override
    public void onClick(View view) {
        if(view == doneBtnTv){
            //close frag and save
        }else if(view == closeBtnIv){
            //close frag and cancel
        }
    }
}