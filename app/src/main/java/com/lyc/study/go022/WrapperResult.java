package com.lyc.study.go022;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lyc on 18/4/14.
 */

public class WrapperResult implements Parcelable {

    public String result = "";


    public WrapperResult(String result) {
        this.result = result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(result);
    }

    public static final Parcelable.Creator<WrapperResult> CREATOR = new Parcelable.Creator<WrapperResult>()
    {
        public WrapperResult createFromParcel(Parcel in)
        {
            WrapperResult wrapper = new WrapperResult(in);
            return wrapper;
        }

        public WrapperResult[] newArray(int size)
        {
            return new WrapperResult[size];
        }
    };

    private WrapperResult(Parcel in)
    {
        result = in.readString();
    }

}
