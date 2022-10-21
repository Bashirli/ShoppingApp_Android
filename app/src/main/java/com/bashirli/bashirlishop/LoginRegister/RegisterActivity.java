package com.bashirli.bashirlishop.LoginRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bashirli.bashirlishop.R;
import com.bashirli.bashirlishop.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        auth=FirebaseAuth.getInstance();

    }

    public void login(View view){

        AlertDialog.Builder alert=new AlertDialog.Builder(RegisterActivity.this);
        alert.setTitle("DİQQƏT!").setMessage("Login səhifəsinə dönmək istəyirsiniz?")
                .setPositiveButton("Bəli", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent =new Intent(getApplicationContext(),LogRegActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("Xeyr", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                }).create().show();

    }

    public int problem_tapma(){
        if(binding.editTextTextPassword.getText().toString().equals("")||
                binding.editTextTextPassword2.getText().toString().equals("")||
                binding.editTextTextPersonName3.getText().toString().equals("")
        ){
            Toast.makeText(this, "Xana boş buraxılıb!", Toast.LENGTH_LONG).show();
            return 0;
        }

        if(!binding.editTextTextPassword.getText().toString().equals(binding.editTextTextPassword2.getText().toString())){
            Toast.makeText(this, "Şifrələr uyuşmur!", Toast.LENGTH_LONG).show();
            return 0;
        }
        if(binding.editTextTextPassword.getText().toString().length()<6){
            Toast.makeText(this, "Şifrə çox qısadır(Min 6 simvol)!", Toast.LENGTH_LONG).show();
            return 0;
        }
        return 1;
    }


    public void register(View view){
if(problem_tapma()==0){
    return;
}
String email=binding.editTextTextPersonName3.getText().toString();
String password=binding.editTextTextPassword2.getText().toString();


auth.createUserWithEmailAndPassword(email,password).addOnFailureListener(new OnFailureListener() {
    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(RegisterActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }
}).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
    @Override
    public void onSuccess(AuthResult authResult) {
        Toast.makeText(RegisterActivity.this, "Hesab uğurla yaradıldı!", Toast.LENGTH_SHORT).show();

        Intent intent =new Intent(getApplicationContext(),LogRegActivity.class);
        startActivity(intent);
        finish();
    }
});

    }

}