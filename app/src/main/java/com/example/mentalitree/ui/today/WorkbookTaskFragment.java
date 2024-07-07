package com.example.mentalitree.ui.today;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mentalitree.DataHandler;
import com.example.mentalitree.R;
import com.example.mentalitree.databinding.FragmentProfileSettingsBinding;
import com.example.mentalitree.databinding.FragmentWorkbookTaskBinding;
import com.example.mentalitree.databinding.WorkbookTaskBinding;

import java.util.ArrayList;

public class WorkbookTaskFragment extends DialogFragment implements View.OnClickListener {


    TextView doneBtnTv, taskDescTv;
    DataHandler dataHandler = DataHandler.getInstance();
    private OnDialogConfirmedListener confirmedListener;




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    public void setConfirmedListener(OnDialogConfirmedListener confirmedListener) {
        this.confirmedListener = confirmedListener;
    }

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

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        return inflater.inflate(R.layout.fragment_workbook_task, container, false);


    }



    @Override
    public void onClick(View view) {
        if(view == doneBtnTv){

            ArrayList<TaskModel> masterList = dataHandler.getTodaysTasks();

            for (TaskModel task : masterList){
                if(task.getTaskId().equals(taskObject.getTaskId())){
                    task.setCompleted(true);
                    break;
                }
            }

            dataHandler.addCompletedTaskToLog(taskObject);
            dataHandler.setTodaysTasks(masterList);
            //ArrayList<TaskModel> newUpdatedList = oldListBeforeUpdate.get()
            if(confirmedListener != null){
                confirmedListener.onDialogCompleted();
            }
            dismiss();
        }else if(view == closeBtnIv){
            dismiss();
        }
    }

    public interface OnDialogConfirmedListener{

        void onDialogCompleted();
    }
}

