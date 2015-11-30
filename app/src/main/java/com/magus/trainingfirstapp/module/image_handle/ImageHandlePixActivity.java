package com.magus.trainingfirstapp.module.image_handle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.magus.trainingfirstapp.R;
import com.magus.trainingfirstapp.base.BaseActivity;
import com.magus.trainingfirstapp.module.image_handle.handle_method.ImageHandleMethod;
import com.magus.trainingfirstapp.module.image_handle.handle_method.NegativeImage;
import com.magus.trainingfirstapp.module.image_handle.handle_method.OldImage;

public class ImageHandlePixActivity extends BaseActivity {

    private ImageView imageView;
    private Bitmap bitmap;
    private final int START = 1001;
    private final int END = 1002;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle(getString(R.string.handle_img));
        setContentLayout(R.layout.activity_image_handle_pix);
        imageView = (ImageView) findViewById(R.id.image_handle_img);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.loading);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.image_negative_btn:
                imageView.setImageBitmap(handleImagePix(bitmap, new NegativeImage()));
                break;
            case R.id.image_old_btn:
                imageView.setImageBitmap(handleImagePix(bitmap, new OldImage()));
                break;
            case R.id.image_relief_btn:
                imageView.setImageBitmap(handleImagePix(bitmap, null));

                break;
        }
    }

    private Bitmap handleImagePix(Bitmap bitmap, ImageHandleMethod method) {

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int color;
        int a, r, g, b;

        Bitmap bitmapResult = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        int[] oldPix = new int[width * height];
        int[] newPix = new int[width * height];

        bitmap.getPixels(oldPix, 0, width, 0, 0, width, height);
        if (method != null) {
            useCommonMethod(method, width, height, oldPix, newPix);
        } else {
            useReliefMethod(width, height, oldPix, newPix);
        }
        bitmapResult.setPixels(newPix, 0, width, 0, 0, width, height);

        return bitmapResult;
    }

    private void useReliefMethod(int width, int height, int[] oldPix, int[] newPix) {
        int color;
        int a;
        int r;
        int g;
        int b;
        int r1, g1, b1;
        for (int i = 1; i < width * height; i++) {
            color = oldPix[i - 1];
            a = Color.alpha(color);
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);

            color = oldPix[i];
            r1 = Color.red(color);
            g1 = Color.green(color);
            b1 = Color.blue(color);

            r = (r - r1 + 127);
            g = (g - g1 + 127);
            b = (b - b1 + 127);

            EndRGB endRGB = new EndRGB(r, g, b).invoke();
            r = endRGB.getR();
            g = endRGB.getG();
            b = endRGB.getB();

            newPix[i] = Color.argb(a, r, g, b);
        }
    }

    private void useCommonMethod(ImageHandleMethod method, int width, int height, int[] oldPix, int[] newPix) {
        int color;
        int r;
        int g;
        int b;
        int a;
        for (int i = 0; i < width * height; i++) {
            color = oldPix[i];
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            a = Color.alpha(color);

            /* 处理方法 */
            r = method.r(r, g, b);
            g = method.g(r, g, b);
            b = method.b(r, g, b);

            EndRGB endRGB = new EndRGB(r, g, b).invoke();
            r = endRGB.getR();
            g = endRGB.getG();
            b = endRGB.getB();
            newPix[i] = Color.argb(a, r, g, b);
        }
    }

    private class EndRGB {
        private int r;
        private int g;
        private int b;

        public EndRGB(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public int getR() {
            return r;
        }

        public int getG() {
            return g;
        }

        public int getB() {
            return b;
        }

        public EndRGB invoke() {
            if (r > 255) {
                r = 255;
            } else if (r < 0) {
                r = 0;
            }
            if (g > 255) {
                g = 255;
            } else if (g < 0) {
                g = 0;
            }
            if (b > 255) {
                b = 255;
            } else if (b < 0) {
                b = 0;
            }
            return this;
        }
    }
}
