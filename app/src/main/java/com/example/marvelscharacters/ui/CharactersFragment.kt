    package com.example.marvelscharacters.ui

    import android.os.Bundle
    import android.util.Log
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.Toast
    import androidx.fragment.app.Fragment
    import androidx.navigation.fragment.findNavController
    import androidx.recyclerview.widget.GridLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import com.example.marvelscharacters.adapter.CharactersAdapter
    import com.example.marvelscharacters.databinding.FragmentCharactersBinding
    import com.example.marvelscharacters.repository.ApiRepository
    import com.example.marvelscharacters.response.CharacterDataWrapper
    import com.example.marvelscharacters.utils.Constants.PRIVATE_API_KEY
    import com.example.marvelscharacters.utils.Constants.PUBLIC_API_KEY
    import dagger.hilt.android.AndroidEntryPoint
    import org.apache.commons.codec.digest.DigestUtils
    import retrofit2.Call
    import retrofit2.Callback
    import retrofit2.Response
    import java.util.Date
    import javax.inject.Inject


    @AndroidEntryPoint
    class CharactersFragment : Fragment() {

        private lateinit var binding: FragmentCharactersBinding
        private var limit = 30
        private var offset = 0
        private var isLoading = false

        @Inject
        lateinit var apiRepository: ApiRepository

        @Inject
        lateinit var characterAdapter: CharactersAdapter

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
        ): View {
            binding = FragmentCharactersBinding.inflate(layoutInflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            setupRecyclerView()
            loadCharacters()
        }

        private fun setupRecyclerView() {
            binding.rvCharacters.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = characterAdapter
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)

                        if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE && !isLoading) {
                            loadCharacters()
                        }
                    }
                })
            }
        }

        // The function loads the first batch of characters.
        // A click listener is set on each character item to navigate to the character details fragment.
        private fun loadCharacters() {
            isLoading = true
            val timestamp = Date().time.toString()
            val hash = md5(timestamp, PRIVATE_API_KEY, PUBLIC_API_KEY)
            binding.charactersProgressBar.visibility = View.VISIBLE

            apiRepository.getCharactersList(timestamp, PUBLIC_API_KEY, hash, limit, offset)
                ?.enqueue(object : Callback<CharacterDataWrapper> {
                    override fun onResponse(
                        p0: Call<CharacterDataWrapper>, p1: Response<CharacterDataWrapper>
                    ) {
                        binding.charactersProgressBar.visibility = View.GONE

                        when (p1.code()) {
                            200 -> {
                                isLoading = false
                                p1.body()?.let { itBody ->
                                    val characters = itBody.data.results
                                    // If list is empty, no more characters to load.
                                    if (characters.isEmpty()) {
                                        showToast("No more characters to load")
                                        return
                                    }
                                    characterAdapter.differ.submitList(characterAdapter.differ.currentList + characters)
                                    offset += limit
                                    characterAdapter.setOnItemClickListener { character ->
                                        val direction =
                                            CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailsFragment(
                                                character.id
                                            )
                                        findNavController().navigate(direction)
                                    }
                                }
                            }

                            409 -> showToast("Error 409. ${p1.message()}")
                            401 -> showToast("Error 401. ${p1.message()}")
                            403 -> showToast("Error 403. ${p1.message()}")
                            405 -> showToast("Error 405. ${p1.message()}")
                        }
                    }

                    override fun onFailure(p0: Call<CharacterDataWrapper>, p1: Throwable) {
                        binding.charactersProgressBar.visibility = View.GONE
                        isLoading = false
                        showToast("onFailure. Error: ${p1.message}")
                        Log.d("CharactersFragment", "Failed to load characters: ${p1.message}")

                    }
                })
        }

        private fun showToast(message: String) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        // Generates an MD5 hash from the timestamp, private key, and public key.
        private fun md5(ts: String, privateKey: String, publicKey: String): String {
            val input = "$ts$privateKey$publicKey"
            return DigestUtils.md5Hex(input)
        }
    }
