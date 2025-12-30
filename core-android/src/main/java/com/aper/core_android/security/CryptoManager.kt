package com.aper.core_android.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoManager @Inject constructor() {

    companion object {
        private const val KEY_ALIAS = "app_crypto_key"

        private const val ANDROID_KEYSTORE = "AndroidKeyStore"

        private const val AES_MODE =
            "${KeyProperties.KEY_ALGORITHM_AES}/" +
                    "${KeyProperties.BLOCK_MODE_GCM}/" +
                    KeyProperties.ENCRYPTION_PADDING_NONE

        private const val IV_SIZE_BYTES = 12
        private const val AUTH_TAG_SIZE_BITS = 128
        private const val KEY_SIZE_BITS = 256
    }

    private val keyStore: KeyStore =
        KeyStore.getInstance(ANDROID_KEYSTORE).apply {
            load(null)
        }

    private fun getOrCreateKey(): SecretKey {
        val existingKey =
            keyStore.getEntry(KEY_ALIAS, null) as? KeyStore.SecretKeyEntry

        if (existingKey != null) return existingKey.secretKey

        val generator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            ANDROID_KEYSTORE
        )

        generator.init(
            KeyGenParameterSpec.Builder(
                KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(KEY_SIZE_BITS)
                .build()
        )

        return generator.generateKey()
    }

    fun encrypt(text: String): String {
        val cipher = Cipher.getInstance(AES_MODE)
        cipher.init(Cipher.ENCRYPT_MODE, getOrCreateKey())

        val iv = cipher.iv
        val encrypted = cipher.doFinal(text.toByteArray())

        return Base64.encodeToString(iv + encrypted, Base64.NO_WRAP)
    }

    fun decrypt(text: String): String {
        val bytes = Base64.decode(text, Base64.NO_WRAP)

        val iv = bytes.copyOfRange(0, IV_SIZE_BYTES)
        val encrypted = bytes.copyOfRange(IV_SIZE_BYTES, bytes.size)

        val cipher = Cipher.getInstance(AES_MODE)
        cipher.init(
            Cipher.DECRYPT_MODE,
            getOrCreateKey(),
            GCMParameterSpec(AUTH_TAG_SIZE_BITS, iv)
        )

        return String(cipher.doFinal(encrypted))
    }
}
