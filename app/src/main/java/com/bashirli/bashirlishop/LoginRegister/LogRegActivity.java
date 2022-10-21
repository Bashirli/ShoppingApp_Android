package com.bashirli.bashirlishop.LoginRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bashirli.bashirlishop.databinding.ActivityLogRegBinding;
import com.bashirli.bashirlishop.view.ScreenActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogRegActivity extends AppCompatActivity {
    private ActivityLogRegBinding binding;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLogRegBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        if(user!=null){
            Intent intent=new Intent(this, ScreenActivity.class);
            startActivity(intent);
            finish();
        }

    }



    public int problem_tapma(){
        if(binding.editTextTextPassword3.getText().toString().equals("")||

                binding.editTextTextPersonName.getText().toString().equals("")
        ){
            Toast.makeText(this, "Xana boş buraxılıb!", Toast.LENGTH_LONG).show();
            return 0;
        }


        if(binding.editTextTextPassword3.getText().toString().length()<6){
            Toast.makeText(this, "Şifrə çox qısadır(Min 6 simvol)!", Toast.LENGTH_LONG).show();
            return 0;
        }
        return 1;
    }

    public void login(View view){
if(problem_tapma()==0){
    return ;
}

String email=binding.editTextTextPersonName.getText().toString();
String password=binding.editTextTextPassword3.getText().toString();

auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
    @Override
    public void onSuccess(AuthResult authResult) {
        Toast.makeText(LogRegActivity.this, "Hesaba giriş edildi!", Toast.LENGTH_LONG).show();
        Intent intent=new Intent(LogRegActivity.this, ScreenActivity.class);
        startActivity(intent);
        finish();

    }
}).addOnFailureListener(new OnFailureListener() {
    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(LogRegActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();

    }
});


    }


    public void register(View view){
        Intent intent =new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }


}