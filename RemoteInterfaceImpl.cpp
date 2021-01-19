#include "jni.h"       
#include <iostream>    
#include "RemoteInterfaceImpl.h"  
#include <ctype.h>
#include <string.h>
#include <chrono>
#include <ctime>
#include <sys/utsname.h>


using namespace std;

JNIEXPORT jstring JNICALL Java_RemoteInterfaceImpl_CppLocalTime
(JNIEnv* env, jobject obj, jstring string) {
    const char* str = env->GetStringUTFChars(string, 0);
    char cap[128];
    strcpy(cap, str);
    env->ReleaseStringUTFChars(string, str);

  auto timenow = chrono::system_clock::to_time_t(chrono::system_clock::now()); 
        srand(time(NULL));
        
        char* temp= ctime(&timenow);
        temp[strlen(temp)-1]= '\0';

    return env->NewStringUTF(temp);
}


JNIEXPORT jstring JNICALL Java_RemoteInterfaceImpl_CppLocalOS
(JNIEnv* env, jobject obj, jstring string) {
    const char* str = env->GetStringUTFChars(string, 0);
    char cap[128];
    char os[16];
    strcpy(cap, str);
    env->ReleaseStringUTFChars(string, str);


               struct utsname details;
               int ret = uname(&details);
                strcat(details.sysname, " ");
                strcat (details.sysname,details.release);
                strcat(os,details.sysname);

 

    //uppercase(cap);
    return env->NewStringUTF(os);
}
