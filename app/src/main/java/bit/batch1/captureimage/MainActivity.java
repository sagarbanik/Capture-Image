package bit.batch1.captureimage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView1,imageView2;
    Resources resources;
    //request code
    final int CAPTURE_IMG_REQ_CODE=0;
    final int PICK_IMG_FROM_GALLERY=1;

    final String TAG="CAPTURE_IMG";
    int fromImageView=0;

    private Uri imgUri;
    private static int PReqCode = 1;
    private static int REQUESTCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] permission={Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this,permission,CAPTURE_IMG_REQ_CODE);
        //check permission
       /* if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,permission,CAPTURE_IMG_REQ_CODE);
        }*/

        resources=getResources();

        imageView1=findViewById(R.id.imageView1);
        imageView2=findViewById(R.id.imageView2);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show Dialog
                fromImageView=1;
                showPickerOption();

            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromImageView=2;
                showPickerOption();
            }
        });
    }
    //method for open camera
    public void openCamera(){
        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent,CAPTURE_IMG_REQ_CODE);

    }
    //method to open gallery
    public void openGallery(){

        Intent galleryIntent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(galleryIntent,PICK_IMG_FROM_GALLERY);

    }

    //method to show picker option

    public void showPickerOption(){
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(resources.getString(R.string.dialog_tt1));
        final String[] options={resources.getString(R.string.Cam),resources.getString(R.string.Can),resources.getString(R.string.Gal)};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (options[which]==resources.getString(R.string.Cam)){
                    //Clicked then open camera
                    openCamera();
                } else if (options[which]==resources.getString(R.string.Gal)){
                    //click then open gallery
                    openGallery();
                }else if (options[which]==resources.getString(R.string.Can)){

                }
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null){
            imgUri = data.getData();
            imageView2.setImageURI(imgUri);
            //Glide.with(MainActivity.this).load(imgUri).into(imageView2);
        }
    }

}


