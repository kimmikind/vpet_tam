package com.example.vpet_tam.ui.startwindow

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.vpet_tam.R

class StartFragment : Fragment() {

    companion object {
        fun newInstance() = StartFragment()
    }

    private val viewModel: StartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_start, container, false)

        var btn: ImageButton = view.findViewById(R.id.play_btn)
        btn.setOnClickListener { p0 ->
            p0?.findNavController()?.navigate(R.id.action_navigation_start_to_navigation_choose)

        }

        return view

    }
}