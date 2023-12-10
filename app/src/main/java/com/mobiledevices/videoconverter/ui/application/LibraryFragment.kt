package com.mobiledevices.videoconverter.Ui.application

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobiledevices.videoconverter.Core.Dao.FirestoreRepository
import com.mobiledevices.videoconverter.Model.Music
import com.mobiledevices.videoconverter.Ui.adapter.MusicLibraryAdapter
import com.mobiledevices.videoconverter.databinding.FragmentLibraryBinding
import com.mobiledevices.videoconverter.ViewModel.MusicViewModel
import com.mobiledevices.videoconverter.ViewModel.SharedViewModel
import kotlinx.coroutines.launch

class LibraryFragment : Fragment(),MusicLibraryAdapter.OnMusicRemoveListener {

    companion object {
        private const val TAG = "LibraryFragment"
    }

    /**
     * We use the view binding feature to avoid using findViewById()
     */
    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!

    /**
     * We use the view model to store the music list during the fragment lifecycle
     */
    private val sharedViewModel: SharedViewModel by activityViewModels()

    /**
     * Create the view and the recycler view adapter when the fragment view is created (rotation, ...)
     * @param inflater The layout inflater: XML to view
     * @param container The view group container: parent view
     * @param savedInstanceState The saved instance state: not used
     * @return The view
     */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "Library fragment created")
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        setupRecyclerView()
        observeLibraryMusic()

        return binding.root
    }

    /**
     * Détruit le binding lorsque la vue du fragment est détruite.
     */
    override fun onDestroyView() {
        Log.d(TAG, "Library fragment destroyed")
        super.onDestroyView()
        _binding = null
    }

    /**
     * Configure le RecyclerView et son adaptateur pour afficher la liste de musiques.
     */
    private fun setupRecyclerView() {
        val adapter = MusicLibraryAdapter(mutableListOf(),this)
        Log.d(TAG, "Setup LIB recycler view")
        binding.rvLibrary.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    /**
     * Gère la suppression d'une musique de la bibliothèque utilisateur.
     */
    override fun onMusicRemove(music: Music) {
        sharedViewModel.removeMusicFromLibrary(music)
    }

    /**
     * Observe les modifications de la bibliothèque musicale de l'utilisateur et met à jour l'interface.
     */
    private fun observeLibraryMusic() {
        //new
        sharedViewModel.currentUser.observe(viewLifecycleOwner, Observer { currentUser ->
            currentUser?.librarie?.let { library ->
                (binding.rvLibrary.adapter as? MusicLibraryAdapter)?.updateMusicList(library)
            }
        })
    }

    /**
     * Update the no result image visibility
     * @param list The music list
     */
    private fun updateNoResultImage(list: List<Music>) {
        binding.ivNoResult.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
    }
}