package com.example.marvelscharacters.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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

    @Inject
    lateinit var apiRepository: ApiRepository

    @Inject
    lateinit var characterAdapter: CharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        val timestamp = Date().time.toString()
        val hash = md5(timestamp, PRIVATE_API_KEY, PUBLIC_API_KEY)
        binding.progressBar.visibility = View.VISIBLE

        apiRepository.getCharactersList(timestamp, PUBLIC_API_KEY, hash, 1)
            ?.enqueue(object : Callback<CharacterDataWrapper> {
                override fun onResponse(
                    p0: Call<CharacterDataWrapper>,
                    p1: Response<CharacterDataWrapper>
                ) {
                    binding.progressBar.visibility = View.GONE
                    when (p1.code()) {
                        200 -> {
                            p1.body()?.let { itBody ->
                                characterAdapter.differ.submitList(itBody.data.results)
                            }
                        }
                        409 -> showToast("Error 409. ${p1.message()}")
                        401 -> showToast("Error 401. ${p1.message()}")
                        403 -> showToast("Error 403. ${p1.message()}")
                        405 -> showToast("Error 405. ${p1.message()}")
                    }
                }

                override fun onFailure(p0: Call<CharacterDataWrapper>, p1: Throwable) {
                    binding.progressBar.visibility = View.GONE
                    showToast("onFailure. Error: ${p1.message}")
                }
            })
    }

    private fun setupRecyclerView() {
        binding.rvCharacters.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = characterAdapter
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun md5(ts: String, privateKey: String, publicKey: String): String {
        val input = "$ts$privateKey$publicKey"
        return DigestUtils.md5Hex(input)
    }
}
