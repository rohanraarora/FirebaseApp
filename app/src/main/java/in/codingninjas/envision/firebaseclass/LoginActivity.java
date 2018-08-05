package in.codingninjas.envision.firebaseclass;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();
        if( user != null){
            afterSignin();
        }
    }

    public void signin(View view){
        String emailString = email.getEditableText().toString();
        String pass = password.getEditableText().toString();

        auth.signInWithEmailAndPassword(emailString,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
                    Toast.makeText(LoginActivity.this,user.getEmail(),Toast.LENGTH_LONG).show();
                    afterSignin();
                }else {
                    Log.e("LoginActivity",task.getException().getMessage());
                    Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void signup(View view){
        String emailString = email.getEditableText().toString();
        String pass = password.getEditableText().toString();

        auth.createUserWithEmailAndPassword(emailString,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
                    Toast.makeText(LoginActivity.this,user.getEmail(),Toast.LENGTH_LONG).show();
                    afterSignin();
                }else {
                    Log.e("LoginActivity",task.getException().getMessage());
                    Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void afterSignin(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
