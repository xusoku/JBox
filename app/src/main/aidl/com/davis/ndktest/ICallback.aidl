// IMyAidlInterface.aidl
package com.davis.ndktest;

// Declare any non-default types here with import statements

interface ICallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
   oneway void basicTypes(String aString);
}
