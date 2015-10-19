package com.magus.trainingfirstapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.magus.trainingfirstapp.BaseActivity;
import com.magus.trainingfirstapp.G;
import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.activity.images.DisplayingBitmapsActivity;
import com.magus.trainingfirstapp.activity.myanim.MyAnimActivity;
import com.magus.trainingfirstapp.fragments.FragmentMainActivity;
import com.magus.trainingfirstapp.photobyintent.PhotoIntentActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.magus.trainingfirstapp.R.string.intent_test;

public class TrainingFirstActivity extends BaseActivity {


    private static final int REQUEST_IMAGE_CAPTURE = 1;

    /**
     * 拍照flag
     */
    private static final int REQUEST_IMAGE_CAPTURE_O = 2;
    private ImageView takePhotoThenToShowImg;

    private LinearLayout llt_funbtn;
    private int[] ids = new int[]{R.id.fragment_btn, R.id.intent_text_btn,R.id.intent_map_btn,
            R.id.intent_web_btn,
            R.id.intent_email,
            R.id.first_open_new_activity_btn,
            R.id.first_share_btn,
            R.id.first_take_phote_demo,
            R.id.first_take_phote_btn,
            R.id.first_take_photo_o_btn,
            R.id.open_swipe_menu_btn
    };
       private String[] btnStrs = new String[]{"fragment","intentTest","map","webView","email","OpenNewActivityBtn","ShareButton",
//            getResources().getString(R.string.intent_test),
//            getResources().getString(R.string.map).toString(),
//            getResources().getString(R.string.web).toString(),
//            getResources().getString(R.string.email).toString(),
//            getResources().getString(R.string.opennew).toString(),
//            getResources().getString(R.string.sharebutton).toString(),
            "TakePhoto_压缩","TakePhotoDemo","TakePhoto_原图","OpenSwipeMenuDemo"
//            getResources().getString(R.string.open_swipe_demo).toString()
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_training_first);
        final int first_photo_show_img = R.id.first_photo_show_img;
        takePhotoThenToShowImg = (ImageView) findViewById(first_photo_show_img);

        llt_funbtn = (LinearLayout) findViewById(R.id.llt_funbtn);

        for(int i=0;i<ids.length;i++){
            addButton(ids[i],btnStrs[i]);
        }
        getBitmapBounds();

    }

    private void addButton(int id, String btnStr) {
        Button button = new Button(this);
        button.setText(btnStr);
        button.setId(id);
        button.setOnClickListener(this);
        llt_funbtn.addView(button);
    }

    /**
     * 为了避免java.lang.OutOfMemory 的异常，在真正解析图片之前检查它的尺寸
     */
    private void getBitmapBounds() {

        BitmapFactory.Options options = new BitmapFactory.Options();

        // 这个设置会返回一个null的Bitmap，但是可以获取到 outWidth, outHeight 与 outMimeType
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.zhizhuxia, options);
        String textContent = "imageHeigth = " + options.outHeight + " \nimageWidth = " + options.outWidth + " \nmimeType = " + options.outMimeType;
        ((TextView) findViewById(R.id.first_bitmap_decode_options_tv)).setText(textContent);
    }


    public void sendMessage(View view) {
        Intent intent = new Intent(TrainingFirstActivity.this, OtherActivity.class);
        EditText editText = (EditText) findViewById(R.id.message_et);
        String message = editText.getText().toString();
        intent.putExtra(G.MessageConst.MESSAGE, message);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_training_fisrt_acitivity, menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            ShareActionProvider shareActionProvider = (ShareActionProvider) (menu.findItem(R.id.menu_item_share)).getActionProvider();
            shareActionProvider.setShareIntent(shareText());
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                showToast("action_settings");
                break;
            case R.id.menu_item_share:
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    startActivity(shareText());
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_first_btn:
                showToast("大家好");
                startActivity(new Intent(TrainingFirstActivity.this, ActivityA.class));
                break;
            case R.id.fragment_btn:
                startActivity(new Intent(this, FragmentMainActivity.class));
                break;
            case R.id.intent_text_btn:
                Uri number = Uri.parse("tel:110");
                startActivity(new Intent(Intent.ACTION_DIAL, number));
                break;
            case R.id.intent_map_btn:
                try {
                    Uri location = Uri.parse("geo:37.422219,-122.08364?z=14"); // z param is zoom level
                    startActivity(new Intent(Intent.ACTION_VIEW, location));

                } catch (Exception e) {
                    showToast("没有地图");
                    return;
                }
                break;
            case R.id.intent_web_btn:
                Uri webPage = Uri.parse("http://www.baidu.com");
                startActivity(new Intent(Intent.ACTION_VIEW, webPage));
                break;
            case R.id.intent_email:
                sendMsg("ssss");
                break;
            case R.id.first_open_new_activity_btn:
                Intent otherintent = new Intent(TrainingFirstActivity.this, OtherActivity.class);
                otherintent.putExtra("key", "key i come on");
                startActivity(otherintent);
                break;
            case R.id.first_share_btn:
                startActivity(Intent.createChooser(shareText(), "选啊你"));
                break;
            case R.id.open_swipe_menu_btn:
                startActivity(new Intent(TrainingFirstActivity.this, SwipeMenuDemoActvity.class));
                break;

            case R.id.first_take_phote_demo:
                startActivity(new Intent(TrainingFirstActivity.this, PhotoIntentActivity.class));
                break;

            case R.id.first_take_phote_btn:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
                break;
            case R.id.first_take_photo_o_btn:   //保存拍摄到的原图
                Intent takePictureOintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureOintent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (photoFile != null) {
                        takePictureOintent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                        startActivityForResult(takePictureOintent, REQUEST_IMAGE_CAPTURE_O);
                    }
                }
                break;
            case R.id.first_open_bitmap_and_anim_activity:
                startActivity(new Intent(TrainingFirstActivity.this, DisplayingBitmapsActivity.class));
                break;
            case R.id.first_my_anim_demo_btn:
                startActivity(new Intent(TrainingFirstActivity.this, MyAnimActivity.class));
                break;
        }
    }

    private Intent shareText() {

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.hellowShuaiYang));
        shareIntent.setType("text/plain");
        return shareIntent;
    }

    public void sendMsg(String content) {
        SmsManager smsManager = SmsManager.getDefault();
        List<String> divideContents = smsManager.divideMessage(content);
        for (String text : divideContents) {
            smsManager.sendTextMessage("1000", null, text, null, null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE_O && resultCode == RESULT_OK) {
            int targetW = takePhotoThenToShowImg.getWidth();
            int targetH = takePhotoThenToShowImg.getHeight();

		/* Get the size of the image */
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
            int scaleFactor = 1;
            if ((targetW > 0) || (targetH > 0)) {
                scaleFactor = Math.min(photoW / targetW, photoH / targetH);
            }

		/* Set bitmap options to scale the image decode target */
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

		/* Associate the Bitmap to the ImageView */
            takePhotoThenToShowImg.setImageBitmap(bitmap);
            galleryAddPic();
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {//展示图片
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            takePhotoThenToShowImg.setImageBitmap(imageBitmap);
        }
    }

    private String mCurrentPhotoPath;

    /**
     * 创建拍摄的图片的存储路径及文件名
     *
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Log.d("TrainingFirstActivity", "storageDir:" + storageDir);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.d("image.getAbsolutePath()", image.getAbsolutePath() + "");
        return image;
    }

    /**
     * 将拍摄到的照片添加到Media Provider的数据库中
     */
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
}
