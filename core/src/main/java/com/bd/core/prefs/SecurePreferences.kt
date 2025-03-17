package com.bd.core.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.io.IOException
import java.security.GeneralSecurityException

class SecurePreferences(context: Context) {

    private val masterKey: MasterKey =
        MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

    val sharedPreferences: SharedPreferences = try {
        EncryptedSharedPreferences.create(
            context,
            "encrypted_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    } catch (e: GeneralSecurityException) {
        throw RuntimeException("Could not create EncryptedSharedPreferences", e)
    } catch (e: IOException) {
        throw RuntimeException("Could not create EncryptedSharedPreferences", e)
    }

    fun saveString(key: String, value: String) {
        sharedPreferences.edit() { putString(key, value) }
    }

    fun getString(key: String, defaultValue: String): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun saveBoolean(key: String, value: Boolean) {
        sharedPreferences.edit() { putBoolean(key, value) }
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun saveInt(key: String, value: Int) {
        sharedPreferences.edit() { putInt(key, value) }
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }
}