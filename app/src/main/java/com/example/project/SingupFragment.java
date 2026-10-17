package com.example.project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingupFragment extends Fragment {

    private EditText etUsername, etPassword;
    private FirebaseServices fbs;
    private Button btnSignup;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SingupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SingupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SingupFragment newInstance(String param1, String param2) {
        SingupFragment fragment = new SingupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_singup, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        fbs = FirebaseServices.getInstance();

        if (getView() != null) {
            etPassword = getView().findViewById(R.id.etPasswordSignup);
            etUsername = getView().findViewById(R.id.etUsernameSignup);
            btnSignup = getView().findViewById(R.id.btnSignupSignup);

            btnSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String username = etUsername.getText().toString();
                    String password = etPassword.getText().toString();

                    if (username.trim().isEmpty() || password.trim().isEmpty()) {
                        Toast.makeText(getActivity(), "Some fields are empty!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    fbs.getAuth().createUserWithEmailAndPassword(username, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "Signup successful!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Signup failed: " + (task.getException() != null ? task.getException().getMessage() : ""), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });
        }
    }
}
