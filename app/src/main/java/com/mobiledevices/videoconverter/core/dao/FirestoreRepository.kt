package com.mobiledevices.videoconverter.core.dao

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.mobiledevices.videoconverter.model.Music
import com.mobiledevices.videoconverter.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class FirestoreRepository {
    companion object {
        private const val TAG = "FirestoreRepository"

        /**
         * Ajoute un utilisateur à Firestore.
         * @param user L'utilisateur à ajouter.
         * @return L'utilisateur ajouté ou null en cas d'échec.
         */
        suspend fun addUser(user: User): User? = withContext(Dispatchers.IO) {
            val db = Firebase.firestore
            try {
                db.collection("users").document(user.id).set(user).await()
                Log.i(TAG, "User added to Firestore: $user")
                user
            } catch (e: Exception) {
                Log.e(TAG, "Error adding user to Firestore", e)
                null
            }
        }

        /**
         * Récupère un utilisateur par son ID depuis Firestore.
         * @param userId L'ID de l'utilisateur à récupérer.
         * @return L'utilisateur récupéré ou null si non trouvé ou en cas d'erreur.
         */
        suspend fun getUser(userId: String): User? = withContext(Dispatchers.IO) {
            val db = Firebase.firestore
            try {
                val documentReference = db.collection("users").document(userId).get().await()
                if (documentReference.exists()) {
                    Log.i(TAG, "User found in Firestore: $userId")
                    documentReference.toObject(User::class.java)
                } else {
                    null
                }
            } catch (e: Exception) {
                Log.w(TAG, "Error getting user from Firestore", e)
                null
            }
        }

        /**
         * Met à jour le mot de passe d'un utilisateur dans Firestore.
         * @param userId L'ID de l'utilisateur dont le mot de passe doit être mis à jour.
         * @param password Le nouveau mot de passe.
         */
        suspend fun updatePasswordUser(userId: String, password: String) {
            val db = Firebase.firestore
            try {
                db.collection("users").document(userId).update("password", password).await()
                Log.i(TAG, "User password updated in Firestore: $userId")
            } catch (e: Exception) {
                Log.e(TAG, "Error updating user password in Firestore", e)
            }
        }

        /**
         * Ajoute une musique à la librairie d'un utilisateur dans Firestore.
         * @param userId L'ID de l'utilisateur.
         * @param music La musique à ajouter.
         * @return True si l'ajout est réussi, False sinon.
         */
        suspend fun addMusicToUser(userId: String, music: Music): Boolean =
            withContext(Dispatchers.IO) {
                val db = Firebase.firestore
                try {
                    // Étape 1 : Récupérer l'utilisateur et sa librairie musicale actuelle
                    val userDocumentRef = db.collection("users").document(userId)
                    val userSnapshot = userDocumentRef.get().await()
                    val user = userSnapshot.toObject(User::class.java)
                    val currentLibrary = user?.librarie?.toMutableList() ?: mutableListOf()

                    // Étape 2 : Ajouter la nouvelle musique à la librairie
                    currentLibrary.add(music)

                    // Étape 3 : Mettre à jour le document de l'utilisateur
                    userDocumentRef.update("librarie", currentLibrary).await()
                    Log.i(TAG, "Music added to user library in Firestore: $userId")
                    true
                } catch (e: Exception) {
                    Log.w(TAG, "Error adding music to user library in Firestore", e)
                    false
                }
            }

        /**
         * Supprime une musique de la librairie d'un utilisateur dans Firestore.
         * @param userId L'ID de l'utilisateur.
         * @param musicToRemove La musique à supprimer.
         * @return True si la suppression est réussie, False sinon.
         */
        suspend fun removeMusicFromUser(userId: String, musicToRemove: Music): Boolean =
            withContext(Dispatchers.IO) {
                val db = Firebase.firestore
                try {
                    // Étape 1 : Récupérer l'utilisateur et sa librairie musicale actuelle
                    val userDocumentRef = db.collection("users").document(userId)
                    val userSnapshot = userDocumentRef.get().await()
                    val user = userSnapshot.toObject(User::class.java)
                    val currentLibrary = user?.librarie?.toMutableList() ?: mutableListOf()

                    // Étape 2 : Retirer la musique spécifique de la librairie
                    currentLibrary.removeAll { it.videoId == musicToRemove.videoId }

                    // Étape 3 : Mettre à jour le document de l'utilisateur
                    userDocumentRef.update("librarie", currentLibrary).await()
                    Log.i(TAG, "Music removed from user library in Firestore: $userId")
                    true
                } catch (e: Exception) {
                    Log.w(TAG, "Error removing music from user library in Firestore", e)
                    false
                }
            }
    }
}