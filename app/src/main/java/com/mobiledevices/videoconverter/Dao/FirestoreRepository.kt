package com.mobiledevices.videoconverter.Dao

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

        // Méthode pour ajouter un nouvel utilisateur
        suspend fun addUser(user: User): User? = withContext(Dispatchers.IO) {
            try {
                val documentReference = db.collection("users").add(user).await()
                Log.i("SUCCESSadd1","${documentReference.id}")
                user.copy(id = documentReference.id)
            } catch (e: Exception) {
                Log.e("ErrorAddUser","ERROR ON addUser()")
                null
            }
        }
        /*********************** COTE MODELE View!!!!!
         * EXEMPLE
         * addUser(newUser) { success, updatedUser ->
         *     if (success) {
         *         // Ici, updatedUser contient l'ID Firestore et peut être utilisé pour mettre à jour CurrentUser
         *         CurrentUser = updatedUser
         *     } else {
         *         // Gestion de l'échec
         *     }
         * }
         *
         ******************************* */

        // Méthode pour obtenir un utilisateur par son ID
        suspend fun getUser(userId: String): User? = withContext(Dispatchers.IO) {
            try {
                val documentReference = db.collection("users").document(userId).get().await()
                if (documentReference.exists()) {
                    documentReference.toObject(User::class.java)?.copy(id = documentReference.id)
                } else {
                    null
                }
            } catch (e: Exception) {
                Log.w("FirestoreGetUser", "Error getting user", e)
                null
            }
        }

        // Méthode pour ajouter une musique
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
                true
            } catch (e: Exception) {
                Log.w("FirestoreAddMusic", "Error adding music to user", e)
                false
            }
        }

        // Méthode pour obtenir des musiques
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
        /* EXEMPLE UTILISATION METHODE!!!!
        /**Exemple pour ajouter un utilisateur**/
        val newMusic = Music("videoId", "videoUrl", "thumbnailUrl", "Title", "Channel")
        val newMusic2 = Music("videoId2", "videoUrl2", "thumbnailUrl2", "Title2", "Channel2")
        val newMusic3 = Music("videoId3", "test", "thumbndddd", "Title3", "Channel2")
        val newUser = User(null, "user@example.com", "password123", listOf(newMusic,newMusic2))
        var currentUser = User()

        //TODO: à mettre dans le view model
        lifecycleScope.launch {
            //Add User
            val updatedUser = FirestoreRepository.addUser(newUser)
            if (updatedUser != null) {
                currentUser = updatedUser.copy()
                Log.i(
                    "SUCCESSSadd",
                    "Success on get info  ${updatedUser.id}, Email: ${updatedUser.mail}"
                )
            }
            //Get User
            val user = FirestoreRepository.getUser("updatedUser.id")
            Log.i("SUCCESSSget","Success on get info  ${currentUser.id}, Email: ${currentUser.mail}")
            //Add Music
            val isMusicAdded = currentUser.id?.let { FirestoreRepository.addMusicToUser(it,newMusic3) }
            Log.i("SUCCESSSaddM","Success on add Music is: ${isMusicAdded}")
            //Get Musics
            val musics = currentUser.id?.let { FirestoreRepository.getMusicsFromUser(it) }
            Log.i("SUCCESSSgetM","Success on add Music is: ${musics.toString()}")
        }
         */



    }

}