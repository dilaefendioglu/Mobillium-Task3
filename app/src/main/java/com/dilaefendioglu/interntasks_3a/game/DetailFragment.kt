package com.dilaefendioglu.interntasks_3a.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dilaefendioglu.interntasks_3a.Constants
import com.dilaefendioglu.interntasks_3a.R

class DetailFragment : Fragment() {

    private lateinit var randomNumberTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        randomNumberTextView = view.findViewById(R.id.randomNumberTv)

        // Argumentleri al ve kullan
        val randomNumber = arguments?.getInt(Constants.RANDOM_NUMBER)
        randomNumberTextView.text = randomNumber?.toString() ?: "?"

        return view
    }
}