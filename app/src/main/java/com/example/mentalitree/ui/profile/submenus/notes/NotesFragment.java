package com.example.mentalitree.ui.profile.submenus.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mentalitree.R;
import com.example.mentalitree.databinding.FragmentProfileNotesBinding;

import java.util.ArrayList;

public class NotesFragment extends Fragment {

    private FragmentProfileNotesBinding binding;
RecyclerView notesRecyclerView;

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileNotesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        notesRecyclerView = binding.notesRecyclerView;
        NoteAdapter adapter = new NoteAdapter(getStoredNotes());
        notesRecyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        notesRecyclerView.setLayoutManager(linearLayoutManager);

        //return inflater.inflate(R.layout.fragment_profile_notes, container, false);
        return root;
    }

    public ArrayList<NoteModel> getStoredNotes(){
        ArrayList<NoteModel> mNotes = new ArrayList<>();
        mNotes.add(new NoteModel("June 12th 2024", "Finished a challenging puzzle today. The sense of accomplishment feels really good."));
        mNotes.add(new NoteModel("June 11th 2024", "Had a heartwarming a with an old friend. It brought back many happy memories."));
        mNotes.add(new NoteModel("June 10th 2024", "Took a walk outside, felt peaceful."));
        mNotes.add(new NoteModel("June 9th 2024", "Organized my desk this morning. The clean space is refreshing and helps me think clearly."));
        mNotes.add(new NoteModel("June 8th 2024", "Enjoyed a quiet evening reading a captivating book; it was very relaxing."));
        mNotes.add(new NoteModel("June 7th 2024", "Today i had a great day at work where I got to present that solution i have been working on."));
        mNotes.add(new NoteModel("June 6th 2024", "Played a board game with friends and won! The sense of victory was small but sweet, bringing unexpected joy to my day. It's moments like these that make life enjoyable."));
        mNotes.add(new NoteModel("June 5th 2024", "Cleaned the entire kitchen today. It's sparkling now, and I feel very accomplished."));

        return mNotes;
    }
}