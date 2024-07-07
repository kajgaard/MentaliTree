package com.example.mentalitree.ui.today;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
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

import com.example.mentalitree.R;
import com.example.mentalitree.databinding.FragmentReviewBinding;
import com.example.mentalitree.databinding.FragmentWorkbookTaskBinding;
import com.google.android.material.button.MaterialButton;

public class ReviewFragment extends DialogFragment implements View.OnClickListener {

    EditText userNoteInput;
    MaterialButton rating1Btn, rating2Btn, rating3Btn, rating4Btn, rating5Btn;
    FragmentReviewBinding binding;
    OnReviewCompletedListener listener;
    ImageView closeBtnIv;
    TextView doneBtnTv;
    int dynamicRating;
    public ReviewFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Inflate and set the layout for the dialog
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_review, null);

        binding = FragmentReviewBinding.bind(view);
        userNoteInput = binding.userInputReviewNotesEt;
        rating1Btn = binding.review1Btn;
        rating1Btn.setOnClickListener(this);
        rating2Btn = binding.review2Btn;
        rating2Btn.setOnClickListener(this);
        rating3Btn = binding.review3Btn;
        rating3Btn.setOnClickListener(this);
        rating4Btn = binding.review4Btn;
        rating4Btn.setOnClickListener(this);
        rating5Btn = binding.review5Btn;
        rating5Btn.setOnClickListener(this);
        closeBtnIv = binding.closeReviewDialogIv;
        closeBtnIv.setOnClickListener(this);
        doneBtnTv = binding.doneWithReviewTv;
        doneBtnTv.setOnClickListener(this);

        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return inflater.inflate(R.layout.fragment_review, container, false);

    }

    public void setListener(OnReviewCompletedListener listener) {
        this.listener = listener;
    }


    @Override
    public void onClick(View view) {
        if(view == closeBtnIv){
            dismiss();
        }else if(view == doneBtnTv){

            //save notes and review
            dismiss();
        }else if(view == rating1Btn){
            resetAllButtons();
            setButtonChosen(rating1Btn);
            dynamicRating = 1;
        } else if (view == rating2Btn) {
            resetAllButtons();
            setButtonChosen(rating2Btn);
            dynamicRating = 2;
        } else if (view == rating3Btn) {
            resetAllButtons();
            setButtonChosen(rating3Btn);
            dynamicRating = 3;
        } else if (view == rating4Btn) {
            resetAllButtons();
            setButtonChosen(rating4Btn);
            dynamicRating = 4;
        } else if (view == rating5Btn) {
            resetAllButtons();
            setButtonChosen(rating5Btn);
            dynamicRating = 5;
        }
    }

    @SuppressLint("ResourceAsColor")
    public void setButtonChosen(MaterialButton button){
        button.setStrokeColor(ColorStateList.valueOf(R.color.blue));
        button.setBackgroundTintList(ColorStateList.valueOf(R.color.lightest_blue));
    }

    @SuppressLint("ResourceAsColor")
    public void resetAllButtons(){
        rating1Btn.setStrokeColor(ColorStateList.valueOf(R.color.light_stroke_grey));
        rating1Btn.setBackgroundTintList(ColorStateList.valueOf(R.color.white));

        rating2Btn.setStrokeColor(ColorStateList.valueOf(R.color.light_stroke_grey));
        rating2Btn.setBackgroundTintList(ColorStateList.valueOf(R.color.white));

        rating3Btn.setStrokeColor(ColorStateList.valueOf(R.color.light_stroke_grey));
        rating3Btn.setBackgroundTintList(ColorStateList.valueOf(R.color.white));

        rating4Btn.setStrokeColor(ColorStateList.valueOf(R.color.light_stroke_grey));
        rating4Btn.setBackgroundTintList(ColorStateList.valueOf(R.color.white));

        rating5Btn.setStrokeColor(ColorStateList.valueOf(R.color.light_stroke_grey));
        rating5Btn.setBackgroundTintList(ColorStateList.valueOf(R.color.white));
    }

    public interface OnReviewCompletedListener{

        void onReviewCompleted();
    }

}

