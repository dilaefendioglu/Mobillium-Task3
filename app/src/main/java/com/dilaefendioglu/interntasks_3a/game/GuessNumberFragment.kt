package com.dilaefendioglu.interntasks_3a.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dilaefendioglu.interntasks_3a.Constants
import com.dilaefendioglu.interntasks_3a.R
import com.dilaefendioglu.interntasks_3a.databinding.FragmentGuessNumberBinding

class GuessNumberFragment : Fragment() {

    private val viewModel: GuessNumberViewModel by viewModels()

    private var _binding: FragmentGuessNumberBinding? = null
    private val binding get() = _binding!!

    private val numberButtons = mutableListOf<View>()
    private val guessBuilder = StringBuilder()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGuessNumberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val numberButtons = listOf(
            binding.zeroButton, binding.oneButton, binding.twoButton, binding.threeButton,
            binding.fourButton, binding.fiveButton, binding.sixButton, binding.sevenButton,
            binding.eightButton, binding.nineButton
        )

        numberButtons.forEach { button ->
            button.setOnClickListener {
                val number = (it as Button).text.toString()
                onNumberClick(number)
            }
        }

        // Tahmin butonuna tıklama dinleyicisi
        binding.GuessButton.setOnClickListener { onGuessClick() }

        // Clear butonuna tıklama dinleyicisi
        binding.clearButton.setOnClickListener { clearGuessAndRestart() }

        // Ortadaki char'a tıklama dinleyicisi
        binding.randomNumberTv.setOnClickListener {
            viewModel.targetNumber.value?.let { number ->
                navigateToDetailFragment(number)
            }
        }

        // ViewModel'deki durum mesajını ve random karakteri gözlemle
        viewModel.statusMessage.observe(viewLifecycleOwner) { message ->
            binding.infoTv.text = message
        }

        viewModel.randomChar.observe(viewLifecycleOwner) { char ->
            binding.randomNumberTv.text = char.toString()
        }
    }

    private fun onNumberClick(number: String) {
        if (guessBuilder.length < 1) { // Tek haneli tahmin için tahmin olsutuurr
            guessBuilder.append(number)
            // Butonun numarasını TextView'a yaz
            binding.infoTv.text = guessBuilder.toString()
        }
    }

    private fun onGuessClick() {
        // Tahmin butonuna basıldığında tahmini kontrol et
        if (guessBuilder.isNotEmpty()) {
            val guess = guessBuilder.toString().toIntOrNull()
            guess?.let {
                viewModel.makeGuess(it)
            }
            clearGuess() // Tahmin yapıldıktan sonra girdiği temizle
        }
    }

    private fun clearGuess() {
        // Tahmini sıfırla
        guessBuilder.clear()
    }

    private fun clearGuessAndRestart() {
        // Tahmini sıfırla ve oyunu yeniden başlat
        clearGuess() // Tahmini temizle
        viewModel.startGame() // Yeni bir oyun başlat
    }

    private fun navigateToDetailFragment(randomNumber: Int) {
        val bundle = Bundle().apply {
            putInt(Constants.RANDOM_NUMBER, randomNumber)
        }
        findNavController().navigate(R.id.action_guessNumberFragment_to_detailFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}