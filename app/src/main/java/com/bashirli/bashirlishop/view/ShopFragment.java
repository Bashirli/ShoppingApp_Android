package com.bashirli.bashirlishop.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bashirli.bashirlishop.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.UUID;


public class ShopFragment extends Fragment {
    EditText editText,editText2,multiText;
    ImageView imageView;
    Button button,button2;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    FirebaseStorage storage;
    StorageReference storageReference;
    Uri uriImage;
    Bitmap bitmapImage;
    ActivityResultLauncher<Intent>  activityResultLauncher;
    ActivityResultLauncher<String> permissionLauncher;
    String info;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    auth=FirebaseAuth.getInstance();
    firestore=FirebaseFirestore.getInstance();
    storage = FirebaseStorage.getInstance();
    storageReference=storage.getReference();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText=view.findViewById(R.id.editTextTextPersonName2);
        editText2=view.findViewById(R.id.editTextTextPersonName4);
        multiText=view.findViewById(R.id.editTextTextMultiLine);
        button=view.findViewById(R.id.button3);
        button2=view.findViewById(R.id.button5);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                geridon(view);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paylas(view);
            }
        });
        imageView=view.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_image(view);
            }
        });
    activity_launcher();
    info=ShopFragmentArgs.fromBundle(getArguments()).getInfo();


    if(info.equals("new")){
editText2.setInputType(InputType.TYPE_CLASS_NUMBER);
editText.setInputType(InputType.TYPE_CLASS_TEXT);
imageView.setImageResource(R.drawable.ic_launcher_background);
editText.setText("");
editText2.setText("");
multiText.setText("");

        button.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_image(view);
            }
        });
}else if(info.equals("old")){
        String name=ShopFragmentArgs.fromBundle(getArguments()).getName();
    String about=ShopFragmentArgs.fromBundle(getArguments()).getAbout();
    String price=ShopFragmentArgs.fromBundle(getArguments()).getPrice();
    String image=ShopFragmentArgs.fromBundle(getArguments()).getImage();
    editText2.setInputType(InputType.TYPE_NULL);
    editText.setInputType(InputType.TYPE_NULL);
    imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        }
    });
    Picasso.get().load(image).into(imageView);
    editText2.setText("Qiymət : "+ Double.parseDouble(price)+" AZN");
    multiText.setText(about);
    editText.setText(name);
    button.setVisibility(View.GONE);
}



    }

    public int problemTapma(){
        if(editText.getText().toString().equals("")||
                multiText.getText().toString().equals("")||
        editText2.getText().toString().equals("")){
            Toast.makeText(requireContext(), "Boş xanalar var! ", Toast.LENGTH_SHORT).show();
        return 0;
        }
        if(bitmapImage==null){
            Toast.makeText(requireContext(),"Şəkilsiz paylaşmaq olmaz!",Toast.LENGTH_LONG).show();
            return 0;
        }
        return 1;

    }

    public void paylas(View view){
    if(problemTapma()==0){
        return;
    }
    Snackbar.make(view,"Mal paylaşılır...",Snackbar.LENGTH_SHORT).show();
    String price,name,about;
    price=editText2.getText().toString();
    name=editText.getText().toString();
    about=multiText.getText().toString();

        UUID uuid=UUID.randomUUID();
        String imageData="image/"+uuid+".jpg";
    storageReference.child(imageData).putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
    @Override
    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        StorageReference reference=storage.getReference(imageData);
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
            String uriLink=uri.toString();

                HashMap<String,Object> data=new HashMap<>();

            data.put("price",price);
            data.put("name",name);
            data.put("about",about);
            data.put("image",uriLink);
            data.put("date", FieldValue.serverTimestamp());

            firestore.collection("Object").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
Toast.makeText(getContext(),"Mal paylaşıldı!",Toast.LENGTH_LONG).show();
                    NavDirections navDirections=ShopFragmentDirections.actionShopFragmentToFeedFragment();
                    Navigation.findNavController(requireActivity(),R.id.fragmentContainerView).navigate(navDirections);
                }
            });

            }
        });
    }
});



    }

    public void geridon(View view){
        NavDirections navDirections=ShopFragmentDirections.actionShopFragmentToFeedFragment();
        Navigation.findNavController(view).navigate(navDirections);
    }


    public void select_image(View view){
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)){
                Snackbar.make(view,"İcazə yoxdur!",Snackbar.LENGTH_INDEFINITE).setAction("İcazə ver", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //permission
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                }).show();
            }else {
                //permission

                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }else{
            //activity
            Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intent);
        }

    }

    public void activity_launcher(){
    activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()== Activity.RESULT_OK){
                Intent intent=result.getData();
                if(intent!=null){
                    uriImage=intent.getData();
                    try {
                        if(Build.VERSION.SDK_INT>=28){
                            ImageDecoder.Source source= ImageDecoder.createSource(getContext().getContentResolver(),uriImage);
                            bitmapImage=ImageDecoder.decodeBitmap(source);
                            imageView.setImageBitmap(bitmapImage);
                        }else{
                        bitmapImage= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),uriImage);
                        imageView.setImageBitmap(bitmapImage);
                        }
                    }catch (Exception e){
                        e.getLocalizedMessage();
                    }
                }
            }
        }
    });

        permissionLauncher=registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            if(result){
                //activity
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
            }else {
                Toast.makeText(requireContext(), "Icazə verilmədi", Toast.LENGTH_SHORT).show();
            }
        }
    });

    }



}