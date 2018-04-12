package com.codefrogapp.dioritbajrami.einkaufsliste.administrator_activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.codefrogapp.dioritbajrami.einkaufsliste.MainActivity;
import com.codefrogapp.dioritbajrami.einkaufsliste.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import static com.codefrogapp.dioritbajrami.einkaufsliste.administrator_activity.Administrator_activity.firebaseAuth;

/**
 * Created by dioritbajrami on 05.04.18.
 */

public class administrator_login_fragment extends Fragment  {

    EditText usernameEditText, passwordEditText;
    Button loginButton;
    TextInputLayout textInputLayout;
    ProgressDialog progressDialog;
    LinearLayout linearLayout;
    Administrator_activity ad = new Administrator_activity();
    View view;
    Fragment fragment1;
    FragmentTransaction fragmentTransaction;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.administrator_fragment_login, container, false);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragment1 = new administrator_list_fragment();


        usernameEditText = (EditText) view.findViewById(R.id.usernameID);
        passwordEditText = (EditText) view.findViewById(R.id.passwordID);
        loginButton = (Button) view.findViewById(R.id.loginbtnID);
        textInputLayout = (TextInputLayout) view.findViewById(R.id.textLayoutID);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearlayoutID);
        progressDialog = new ProgressDialog(getActivity());

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        return view;
    }


    public void login() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getActivity(), "Bitte email eingeben!", Toast.LENGTH_SHORT).show();
            //Return stops the function execution further
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "Bitte Password eingeben!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Wird Einloggt...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    fragmentTransaction.replace(R.id.content_frame, fragment1).commit();
                    Toast.makeText(getActivity(), "Du bist jetzt eingeloggt.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(),"Passwort oder Email waren falsch.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
