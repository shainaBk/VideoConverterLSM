package com.mobiledevices.videoconverter.Core.Dao

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.mobiledevices.videoconverter.Model.Music
import com.mobiledevices.videoconverter.Model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class FirestoreRepository {
    companion object {
        private val db = Firebase.firestore

        /**
         * Ajoute un utilisateur à Firestore.
         * @param user L'utilisateur à ajouter.
         * @return L'utilisateur ajouté ou null en cas d'échec.
         */
        suspend fun addUser(user: User): User? = withContext(Dispatchers.IO) {
            try {
                db.collection("users").document(user.id).set(user).await()
                Log.i("SUCCESSadd1","${user.toString()}")
                user
            } catch (e: Exception) {
                Log.e("ErrorAddUser","ERROR ON addUser()")
                null
            }
        }

        /**
         * Récupère un utilisateur par son ID depuis Firestore.
         * @param userId L'ID de l'utilisateur à récupérer.
         * @return L'utilisateur récupéré ou null si non trouvé ou en cas d'erreur.
         */
        suspend fun getUser(userId: String): User? = withContext(Dispatchers.IO) {
            try {
                val documentReference = db.collection("users").document(userId).get().await()
                if (documentReference.exists()) {
                    documentReference.toObject(User::class.java)
                } else {
                    null
                }
            } catch (e: Exception) {
                Log.w("FirestoreGetUser", "Error getting user", e)
                null
            }
        }

        /**
         * Met à jour le mot de passe d'un utilisateur dans Firestore.
         * @param userId L'ID de l'utilisateur dont le mot de passe doit être mis à jour.
         * @param password Le nouveau mot de passe.
         */
        suspend fun updatePasswordUser(userId: String, password:String){
            try {
                db.collection("users").document(userId).update("password", password).await()
                // Gérer ici le succès de la mise à jour
            } catch (e: Exception) {
                // Gérer ici l'exception
            }
        }

        /**
         * Ajoute une musique à la librairie d'un utilisateur dans Firestore.
         * @param userId L'ID de l'utilisateur.
         * @param music La musique à ajouter.
         * @return True si l'ajout est réussi, False sinon.
         */
        suspend fun addMusicToUser(userId: String, music: Music): Boolean = withContext(Dispatchers.IO) {
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
                true
            } catch (e: Exception) {
                Log.w("FirestoreAddMusic", "Error adding music to user", e)
                false
            }
        }

        /**
         * Récupère la librairie musicale d'un utilisateur depuis Firestore.
         * @param userId L'ID de l'utilisateur dont la librairie est à récupérer.
         * @return La liste des musiques de l'utilisateur.
         */
        suspend fun getMusicsFromUser(userId: String): List<Music> = withContext(Dispatchers.IO) {
            try {
                // Récupérer le document de l'utilisateur
                val userDocumentSnapshot = db.collection("users").document(userId).get().await()

                // Vérifier si le document existe et récupérer la librairie de musiques
                if (userDocumentSnapshot.exists()) {
                    val user = userDocumentSnapshot.toObject(User::class.java)
                    user?.librarie ?: emptyList()
                } else {
                    emptyList<Music>()
                }
            } catch (e: Exception) {
                Log.w("FirestoreGetMusics", "Error getting user's music library", e)
                emptyList<Music>()
            }
        }

        /**
         * Supprime une musique de la librairie d'un utilisateur dans Firestore.
         * @param userId L'ID de l'utilisateur.
         * @param musicToRemove La musique à supprimer.
         * @return True si la suppression est réussie, False sinon.
         */
        suspend fun removeMusicFromUser(userId: String, musicToRemove: Music): Boolean = withContext(Dispatchers.IO) {
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
                true
            } catch (e: Exception) {
                Log.w("FirestoreRemoveMusic", "Error removing music from user", e)
                false
            }
        }

    }

}