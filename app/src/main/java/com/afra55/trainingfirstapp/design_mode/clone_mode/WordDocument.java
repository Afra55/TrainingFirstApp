package com.afra55.trainingfirstapp.design_mode.clone_mode;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by yangshuai in the 16:44 of 2015.12.28 .
 */
public class WordDocument implements Cloneable {

    private String mText;
    private ArrayList<String> mImages = new ArrayList<>();

    public ArrayList<String> getmImages() {
        return mImages;
    }

    public WordDocument(){
        /* 需要注意的是：使用 Cloneable 实现拷贝时，并不会执行构造函数。 */
        Log.d("WordDocument", "WordDocument 构造函数");
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        try {
            WordDocument document = (WordDocument) super.clone();
            document.mText = this.mText;

            /* 类似于这样的指向地址的引用，也要采用拷贝形式 */
            document.mImages = (ArrayList<String>) this.mImages.clone();
            return document;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void addImages(String mImages) {
        this.mImages.add(mImages);
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public void use(){
        WordDocument originDoc = new WordDocument();
        originDoc.setmText("doc");
        originDoc.addImages("1");
        originDoc.addImages("2");
        originDoc.addImages("3");

        /* 进行克隆，并进行修改，原始对象并不会被改变 */
        try {
            WordDocument copyDoc = (WordDocument) originDoc.clone();
            copyDoc.setmText("doc copy");
            copyDoc.addImages("1");
            copyDoc.addImages("2");
            copyDoc.addImages("3");
            copyDoc.addImages("4");
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }
}
