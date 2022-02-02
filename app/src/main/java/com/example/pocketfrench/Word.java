package com.example.pocketfrench;

public class Word {

    private String mDefaultTranslation;

    private String mFrenchTranslation;

    private int mSoundResourceID;

    private int mImageResourceID = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    public Word(String defaultTranslation, String frenchTranslation, int imageResourceId, int soundResourceId){
        mDefaultTranslation = defaultTranslation;
        mFrenchTranslation = frenchTranslation;
        mImageResourceID = imageResourceId;
        mSoundResourceID = soundResourceId;
    }

    public Word(String defaultTranslation, String frenchTranslation, int soundResourceId){
        mDefaultTranslation = defaultTranslation;
        mFrenchTranslation = frenchTranslation;
        mSoundResourceID = soundResourceId;
    }

    public String getmDefaultTranslation(){
        return mDefaultTranslation;
    }

    public String getmFrenchTranslation(){
        return mFrenchTranslation;
    }

    public int getmImageResourceID(){
        return mImageResourceID;
    }

    public int getmSoundResourceID(){
        return mSoundResourceID;
    }

    public boolean hasImage(){
        return mImageResourceID != NO_IMAGE_PROVIDED;
    }

}

