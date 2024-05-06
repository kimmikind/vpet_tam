package com.example.vpet_tam.view

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.vpet_tam.databinding.FragmentSettingsBinding
import com.example.vpet_tam.viewmodel.ChooseViewModel
import com.example.vpet_tam.viewmodel.DbViewModel
import com.example.vpet_tam.viewmodel.SettingsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {
    companion object {
        fun newInstance() = SettingsFragment()
    }
    private val settingsViewModel: SettingsViewModel by activityViewModels()
    private val dbViewModel: DbViewModel by activityViewModels()
    private val chooseViewModel: ChooseViewModel by activityViewModels()

    private var _binding: FragmentSettingsBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnFreePet.setOnClickListener {
            chooseViewModel.nameState.observe(viewLifecycleOwner) {
                var name = it
                CoroutineScope(Dispatchers.IO).launch {
                    dbViewModel.deletePet(requireActivity().application, id)
                }
                    if (dbViewModel.getPetDetails(requireActivity().application, id) != null )
                     {
                        Toast.makeText(
                            requireContext().applicationContext,
                            "pet deleted",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else Toast.makeText(
                        requireContext().applicationContext,
                        "error deleting pet",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}