package com.example.marvelscharacters.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelscharacters.GlideApp
import com.example.marvelscharacters.adapter.ComicsAdapter
import com.example.marvelscharacters.databinding.FragmentCharacterDetailsBinding
import com.example.marvelscharacters.repository.ApiRepository
import com.example.marvelscharacters.response.CharacterDetailDataWrapper
import com.example.marvelscharacters.response.CharacterDetail
import com.example.marvelscharacters.response.ComicDataWrapper
import com.example.marvelscharacters.utils.Constants.PRIVATE_API_KEY
import com.example.marvelscharacters.utils.Constants.PUBLIC_API_KEY
import dagger.hilt.android.AndroidEntryPoint
import org.apache.commons.codec.digest.DigestUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {

    lateinit var binding: FragmentCharacterDetailsBinding

    @Inject
    lateinit var apiRepository: ApiRepository

    @Inject
    lateinit var comicAdapter: ComicsAdapter

    private val args: CharacterDetailsFragmentArgs by navArgs()

    private val timestamp = Date().time.toString()

    private val hash = md5(timestamp, PRIVATE_API_KEY, PUBLIC_API_KEY)

    private var characterRequest: Call<CharacterDetailDataWrapper>? = null
    private var comicsRequest: Call<ComicDataWrapper>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        val todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val characterId = args.characterid

        fetchCharacterDetails(characterId)
        fetchCharacterComics(characterId, todayDate)

    }

    private fun fetchCharacterDetails(characterId: Int) {
        showFragmentProgressBar(true)

        characterRequest = apiRepository.getCharacter(characterId, PUBLIC_API_KEY, timestamp, hash)
        characterRequest?.enqueue(object : Callback<CharacterDetailDataWrapper> {
            override fun onResponse(
                p0: Call<CharacterDetailDataWrapper>, p1: Response<CharacterDetailDataWrapper>
            ) {
                showFragmentProgressBar(false)
                showComicsProgressBar(true)
                binding.comicsLabel.visibility = View.VISIBLE

                characterRequest = null
                p1.body()?.let { itBody ->
                    val character = itBody.data?.results?.get(0)
                    character?.let { bindCharacterDetails(character) }
                }
            }

            override fun onFailure(p0: Call<CharacterDetailDataWrapper>, p1: Throwable) {
                showFragmentProgressBar(false)
                characterRequest = null
                Toast.makeText(context, "Error loading character details", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun fetchCharacterComics(characterId: Int, todayDate: String) {
        comicsRequest = apiRepository.getCharacterComics(
            characterId,
            timestamp,
            PUBLIC_API_KEY,
            hash,
            "comic",
            "comic",
            "2005-01-01,${todayDate}",
            "-onsaleDate",
            10
        )
        comicsRequest?.enqueue(object : Callback<ComicDataWrapper> {
            override fun onResponse(
                p0: Call<ComicDataWrapper>, p1: Response<ComicDataWrapper>
            ) {
                showComicsProgressBar(false)
                comicsRequest = null
                if (p1.code() == 200) {
                    p1.body()?.let { itBody ->
                        val comics = itBody.data?.results
                        comicAdapter.differ.submitList(comics)
                    }
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(p0: Call<ComicDataWrapper>, p1: Throwable) {
                showComicsProgressBar(false)
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun bindCharacterDetails(character: CharacterDetail) {
        binding.characterName.text = character.name
        binding.characterDescription.text = character.description
        GlideApp.with(this)
            .load(character.thumbnail?.fullPath)
            .fitCenter()
            .into(binding.characterImageView)
    }


    private fun setupRecyclerView() {
        binding.comicsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = comicAdapter
        }
    }

    private fun md5(ts: String, privateKey: String, publicKey: String): String {
        val input = "$ts$privateKey$publicKey"
        return DigestUtils.md5Hex(input)
    }

    // Cancel the requests if the user navigates away from this fragment
    override fun onDestroyView() {
        super.onDestroyView()
        characterRequest?.cancel()
        comicsRequest?.cancel()
    }

    private fun showFragmentProgressBar(show: Boolean) {
        binding.characterDetailsProgressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showComicsProgressBar(show: Boolean) {
        binding.comicsProgressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

}