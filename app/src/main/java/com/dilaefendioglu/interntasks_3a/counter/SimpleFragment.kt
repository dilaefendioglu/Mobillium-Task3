package com.dilaefendioglu.interntasks_3a.counter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.dilaefendioglu.interntasks_3a.R

class SimpleFragment : Fragment() {
    // ViewModel'i viewModels() ile başlat
    private val viewModel: SimpleViewModel by viewModels()
    private var globalCounter = 0 // Global sayaç değişkeni
    private var useViewModel = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            globalCounter = it.getInt("globalCounter", 0)
            useViewModel = it.getBoolean("useViewModel", false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_simple, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Layout öğelerine erişim
        val textView: TextView = view.findViewById(R.id.counterTextView)
        val button: Button = view.findViewById(R.id.incrementButton)
        val switchView: Switch = view.findViewById(R.id.switchMode)

        viewModel.counter.observe(viewLifecycleOwner, Observer { count ->
            if (useViewModel) {
                textView.text = count.toString()
            }
        })

        button.setOnClickListener {
            if (useViewModel) {
                viewModel.incrementButton()
            } else {
                globalCounter++
                textView.text = globalCounter.toString()
            }
        }

        switchView.setOnCheckedChangeListener { _, isChecked ->
            useViewModel = isChecked
            if (useViewModel) {
                textView.text = viewModel.counter.value.toString()
            } else {
                textView.text = globalCounter.toString()
            }
        }

        // Rotasyon sonrası veri koruma testleri
        if (useViewModel) {
            textView.text = viewModel.counter.value.toString()
        } else {
            textView.text = globalCounter.toString()
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // GlobalCounter ve useViewModel değerlerini kaydetme
        outState.putInt("globalCounter", globalCounter)
        outState.putBoolean("useViewModel", useViewModel)
    }
}
