package com.recyclerapplication.webservice

import android.content.Context
import android.content.Intent
import android.os.Build
import android.service.autofill.UserData
import android.util.Log


import com.google.gson.Gson


class SessionManager(private val mCtx: Context) {

    //======================Logout User===================//
    fun logout() {

        val sharedPreferences =
            mCtx.getSharedPreferences(
                SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }



    //======================Firebase Message Token================//
    var firebaseToken: String?
        get() {
            val sharedPreferences =
                mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString(FIREBASE_TOKEN, "")
        }
        set(firebaseToken) {
            val sharedPreferences =
                mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(FIREBASE_TOKEN, firebaseToken)
            editor.apply()
        }


   /* var isWalkThroughCompleted:Boolean
        get() {
            val sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_WALK_THROUGH,Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean(WALK_THROUGH,false)
        }
        set(value) {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_WALK_THROUGH,Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean(WALK_THROUGH, value)
            editor.apply()
        }
*/

    //========================Static Variables==================//

    companion object {
       // private const val SHARED_PREF_WALK_THROUGH = "com.dw.mop.walk.through"
        //private const val WALK_THROUGH = "walk_through"
        const val SHARED_PREF_NAME = "com.dw.hoofboot"
        const val FIREBASE_TOKEN = "firebase_token"
        private const val USER_LOGIN_REM = "user_login_rem"
        private var mInstance: SessionManager? = null
        private const val USER_INFO = "user_info"
        private const val CART_DETAIL = "cart_detail"
        private const val SELECTED_ADDRESS = "selected_address"
        private const val USER_TOKEN = "user_token"
        private const val NOTIFICATION_STATUS = "notification_status"

        @Synchronized
        fun getInstance(context: Context): SessionManager? {
            if (mInstance == null) {
                mInstance =
                    SessionManager(context)
            }
            return mInstance
        }
    }


}

