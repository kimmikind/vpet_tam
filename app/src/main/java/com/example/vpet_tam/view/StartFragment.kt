package com.example.vpet_tam.view

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.findNavController
import com.example.vpet_tam.R
import com.example.vpet_tam.viewmodel.StartViewModel

class StartFragment : Fragment() {

    companion object {
        fun newInstance() = StartFragment()
    }

    private val startViewModel: StartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_start, container, false)

        var btn: ImageButton = view.findViewById(R.id.play_btn)
        btn.setOnClickListener { p0 ->
            if (SettingsFragment.id_check == -1 || SettingsFragment.id_check == 0) {
                p0?.findNavController()?.navigate(R.id.action_navigation_start_to_navigation_choose)
            }
            else {
                p0?.findNavController()?.navigate(R.id.action_navigation_start_to_navigation_home)
            }
        }
        return view

    }
}

