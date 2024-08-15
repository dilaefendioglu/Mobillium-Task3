package com.dilaefendioglu.interntasks_3a.counter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.dilaefendioglu.interntasks_3a.databinding.FragmentSimpleBinding

class SimpleFragment : Fragment() {
    // ViewModel'i viewModels() ile başlat
    private val viewModel: SimpleViewModel by viewModels()
    private var globalCounter = 0 // Global sayaç değişkeni
    private var useViewModel = false

    // Binding değişkenini tanımladık
    private var _binding: FragmentSimpleBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Ekran döndürme sırasında globalCounter'ı sıfırlamak için kontrol ekleyelim
        savedInstanceState?.let {
            useViewModel = it.getBoolean("useViewModel", false)
            // Switch moduna göre globalCounter'ı sıfırla
            if (!useViewModel) {
                globalCounter = 0
            } else {
                globalCounter = it.getInt("globalCounter", 0)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // ViewBinding kullanarak düzen sagladık
        _binding = FragmentSimpleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Layout elemanlarına binding aracılığıyla erişmek
        binding.apply {
            viewModel.counter.observe(viewLifecycleOwner, Observer { count ->
                if (useViewModel) {
                    counterTextView.text = count.toString()
                }
            })

            incrementButton.setOnClickListener {
                if (useViewModel) {
                    viewModel.incrementButton()
                } else {
                    globalCounter++
                    counterTextView.text = globalCounter.toString()
                }
            }

            switchMode.setOnCheckedChangeListener { _, isChecked ->
                useViewModel = isChecked
                // Switch değiştiğinde metni güncelle
                counterTextView.text = if (useViewModel) {
                    viewModel.counter.value.toString()
                } else {
                    globalCounter.toString()
                }
            }

            // Rotasyon sonrası sayaç metnini geri yükleme
            if (useViewModel) {
                counterTextView.text = viewModel.counter.value.toString()
            } else {
                counterTextView.text = globalCounter.toString()
            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // GlobalCounter ve useViewModel değerlerini kaydetme
        outState.putBoolean("useViewModel", useViewModel)
        outState.putInt("globalCounter", globalCounter)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
