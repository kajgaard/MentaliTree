package com.example.mentalitree.ui.today;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mentalitree.DataHandler;
import com.example.mentalitree.PrivateDataHandler;
import com.example.mentalitree.R;
import com.example.mentalitree.databinding.FragmentReviewBinding;
import com.google.android.material.button.MaterialButton;

public class ReviewFragment extends DialogFragment implements View.OnClickListener {

    private static final String TAG = "MMREVIEWFRAG";
    EditText userNoteInput;
    MaterialButton rating1Btn, rating2Btn, rating3Btn, rating4Btn, rating5Btn;
    FragmentReviewBinding binding;
    OnReviewCompletedListener listener;
    ImageView closeBtnIv;
    TextView doneBtnTv;
    String dynamicRating;
    String dynamicRatingDescriptiveString ="";
    PrivateDataHandler privateDataHandler = PrivateDataHandler.getInstance();
    DataHandler datahandler = DataHandler.getInstance();
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

        builder.setView(view);
        return builder.create();
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
            String input = userNoteInput.getText().toString();
            String encryptedNote = privateDataHandler.encryptString(input);
            String encryptedRating = privateDataHandler.encryptString(dynamicRating);
            datahandler.saveEncryptedNoteToDatabase(encryptedNote, encryptedRating);
            datahandler.setTodaysUserRating(dynamicRatingDescriptiveString);
            datahandler.updateCategoryProbabilitiesInDatabaseAfterReview();
            if(listener != null){
                datahandler.setHasReviewedToday(true);
                listener.onReviewCompleted();

            }
            //save notes and review
            dismiss();
        }else if(view == rating1Btn){
            resetAllButtons();
            setButtonChosen(rating1Btn);
            dynamicRating = "1";
            dynamicRatingDescriptiveString = "Good";
        } else if (view == rating2Btn) {
            resetAllButtons();
            setButtonChosen(rating2Btn);
            dynamicRating = "2";
            dynamicRatingDescriptiveString = "Okay";
        } else if (view == rating3Btn) {
            resetAllButtons();
            setButtonChosen(rating3Btn);
            dynamicRating = "3";
            dynamicRatingDescriptiveString = "Not sure";
        } else if (view == rating4Btn) {
            resetAllButtons();
            setButtonChosen(rating4Btn);
            dynamicRating = "4";
            dynamicRatingDescriptiveString = "Poorly";
        } else if (view == rating5Btn) {
            resetAllButtons();
            setButtonChosen(rating5Btn);
            dynamicRating = "5";
            dynamicRatingDescriptiveString = "Very bad";
        }

    }

    @SuppressLint("ResourceAsColor")
    public void setButtonChosen(MaterialButton button){
        button.setBackgroundTintList(getResources().getColorStateList(R.color.checked_button));
        button.setStrokeColor(getResources().getColorStateList(R.color.checked_button_stroke));
    }

    @SuppressLint("ResourceAsColor")
    public void resetAllButtons(){

        rating1Btn.setStrokeColor(getResources().getColorStateList(R.color.unchecked_button_stroke));
        rating1Btn.setBackgroundTintList(getResources().getColorStateList(R.color.unchecked_button));

        rating2Btn.setStrokeColor(getResources().getColorStateList(R.color.unchecked_button_stroke));
        rating2Btn.setBackgroundTintList(getResources().getColorStateList(R.color.unchecked_button));

        rating3Btn.setStrokeColor(getResources().getColorStateList(R.color.unchecked_button_stroke));
        rating3Btn.setBackgroundTintList(getResources().getColorStateList(R.color.unchecked_button));

        rating4Btn.setStrokeColor(getResources().getColorStateList(R.color.unchecked_button_stroke));
        rating4Btn.setBackgroundTintList(getResources().getColorStateList(R.color.unchecked_button));

        rating5Btn.setStrokeColor(getResources().getColorStateList(R.color.unchecked_button_stroke));
        rating5Btn.setBackgroundTintList(getResources().getColorStateList(R.color.unchecked_button));
    }

    public interface OnReviewCompletedListener{

        void onReviewCompleted();
    }

}

