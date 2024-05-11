package com.example.vpet_tam.view

import android.app.ActivityManager
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.vpet_tam.R
import com.example.vpet_tam.databinding.FragmentSettingsBinding
import com.example.vpet_tam.genrandom.DataHelper
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
        var flag = 1
        var gen = 0
        var flag_event = 0

        fun newInstance() = SettingsFragment()
    }
    private val dbViewModel: DbViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()

    private var _binding: FragmentSettingsBinding? = null
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
            val dialog = Dialog(activity as AppCompatActivity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.alert_editor)
            val body: EditText? = dialog.findViewById(R.id.ch_name)
            val okBtn1: Button? = dialog.findViewById(R.id.ok_button_name)
            okBtn1?.setOnClickListener {
                val name = body?.text.toString()
                dialog.dismiss()
                CoroutineScope(IO).launch {
                    dbViewModel.updatePetName(
                        requireActivity().application,
                        id_check,
                        name
                    )
                }
            }
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }
        binding.btnTurnEvent.setOnClickListener {
            if (flag == 1) {
                flag = 0
                Toast.makeText(
                    requireContext().applicationContext,
                    "event set off",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                flag = 1
                Toast.makeText(
                    requireContext().applicationContext,
                    "event set on",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        binding.btnFreePet.setOnClickListener {
            CoroutineScope(IO).launch {
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
            }.onFailure {
                    Toast.makeText(
                        requireContext().applicationContext,
                        "error deleting pet id:${id_check}",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }
        binding.btnBack.setOnClickListener {p3 ->
            if (id_check != -1 && id_check != 0) {
                /*Toast.makeText(
                    requireContext().applicationContext,
                    "back:${id_check}",
                    Toast.LENGTH_SHORT
                ).show()*/
                p3?.findNavController()?.navigate(R.id.action_navigation_settings_to_navigation_home)
            }
            else p3?.findNavController()?.navigate(R.id.action_navigation_settings_to_navigation_start)


        }
        binding.btnQuit.setOnClickListener {
            homeViewModel.idState.observe(viewLifecycleOwner) {
                id_check = it
                /*Toast.makeText(
                    requireContext().applicationContext,
                    "quit:${id_check},${save_img}",
                    Toast.LENGTH_SHORT
                ).show()*/
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