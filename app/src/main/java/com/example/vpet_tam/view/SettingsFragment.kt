package com.example.vpet_tam.view

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.vpet_tam.R
import com.example.vpet_tam.databinding.FragmentSettingsBinding
import com.example.vpet_tam.viewmodel.ChooseViewModel
import com.example.vpet_tam.viewmodel.DbViewModel
import com.example.vpet_tam.viewmodel.HomeViewModel
import com.example.vpet_tam.viewmodel.SettingsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {
    companion object {
        var id_check:Int = -1
        var save_img : Int =0

        fun newInstance() = SettingsFragment()
    }
    private val settingsViewModel: SettingsViewModel by activityViewModels()
    private val dbViewModel: DbViewModel by activityViewModels()
    private val chooseViewModel: ChooseViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()


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
        homeViewModel.idState.observe(viewLifecycleOwner) {
            id_check = it
        }
        homeViewModel.imgState.observe(viewLifecycleOwner) {
            save_img = it

        }
        binding.btnChangeName.setOnClickListener {
            //var name = ""
            val inputEditTextField = EditText(requireActivity())
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Title")
                .setMessage("Message")
                .setView(inputEditTextField)
                .setPositiveButton("OK") { _, _ ->
                    val editTextInput = inputEditTextField .text.toString()
                    //Timber.d("editext value is: $editTextInput")
                    CoroutineScope(IO).launch {
                        dbViewModel.updatePetName(
                            requireActivity().application,
                            id_check,
                            editTextInput
                        )
                    }
                }
                .setNegativeButton("Cancel", null)
                .create()
            dialog.show()


        }
        binding.btnTurnEvent.setOnClickListener {

        }
        binding.btnFreePet.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                if (it != null)
                    dbViewModel.deletePet(requireActivity().application, id_check)
                }
            runCatching {
                if (id_check != -1) {
                    dbViewModel.getPetDetails(requireActivity().application, id_check)
                    id_check = -1
                    homeViewModel.onSaveId(id_check)
                }
            }.onSuccess {
                Toast.makeText(
                    requireContext().applicationContext,
                    "pet deleted",
                    Toast.LENGTH_SHORT
                ).show()
            }
                .onFailure {
                    Toast.makeText(
                        requireContext().applicationContext,
                        "error deleting pet id:${id_check}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

        }
        binding.btnBack.setOnClickListener {p3 ->
            if (id_check != -1 && id_check != 0) {
                Toast.makeText(
                    requireContext().applicationContext,
                    "back:${id_check}",
                    Toast.LENGTH_SHORT
                ).show()
               // homeViewModel.onSaveId(id_check)
                p3?.findNavController()?.navigate(R.id.action_navigation_settings_to_navigation_home)
            }
            else p3?.findNavController()?.navigate(R.id.action_navigation_settings_to_navigation_start)


        }
        binding.btnQuit.setOnClickListener {
            homeViewModel.idState.observe(viewLifecycleOwner) {
                id_check = it
                //dbViewModel.updatePetStat(requireActivity().application, id_check,"123","134","456")
                Toast.makeText(
                    requireContext().applicationContext,
                    "quit:${id_check},${save_img}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            val activityManager = activity?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            activityManager.appTasks.forEach { task -> task.setExcludeFromRecents(true) }
            activity?.finishAffinity()

        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}