// IMyAidlService.aidl
package com.davis.ndktest;

// Declare any non-default types here with import statements

interface IMyAidlService {
  void init(String packageName,String slot);
      void registerCallback(String packageName);

}
