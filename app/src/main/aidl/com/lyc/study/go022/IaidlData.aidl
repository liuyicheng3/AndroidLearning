// IaidlData.aidl
package com.lyc.study.go022;
import com.lyc.study.go022.ICallback;

// Declare any non-default types here with import statements

interface IaidlData {
     String calResult(int input);
     void init(ICallback callBack);

}
