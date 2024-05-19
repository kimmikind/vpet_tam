package com.example.vpet_tam.view


import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.vpet_tam.R
import com.example.vpet_tam.databinding.FragmentHomeBinding
import com.example.vpet_tam.genrandom.DataHelper
import com.example.vpet_tam.view.SettingsFragment.Companion.flag
import com.example.vpet_tam.view.SettingsFragment.Companion.flag_event
import com.example.vpet_tam.view.SettingsFragment.Companion.gen
import com.example.vpet_tam.view.SettingsFragment.Companion.id_check
import com.example.vpet_tam.view.SettingsFragment.Companion.measure_start
import com.example.vpet_tam.view.SettingsFragment.Companion.save_img
import com.example.vpet_tam.viewmodel.DbViewModel
import com.example.vpet_tam.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.*
import java.util.TimerTask

class HomeFragment : Fragment() {
    companion object {
        fun newInstance() = HomeFragment()
    }

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val dbViewModel: DbViewModel by activityViewModels()

    lateinit var dataHelper: DataHelper
    private val timer = Timer()

    private var hunger: Double = 0.0
    private var health: Double = 0.0
    private var energy: Double = 0.0

    private var type_event : String = ""
    private var x_number : Double = 1.0
    private var event_time : Int = 0
    private var type_stat : String = ""
    //private var flag_event = 0

    //private var measure_start : Int = 0
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    var dropDownMenuIconItem: LinearLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // установка нижней главной панели меню
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)

        // флаг, что не наступит новое событие по завершению предыдущего
        if (flag == 1) binding.btnEvent.isEnabled = true else if (flag == 0) binding.btnEvent.isEnabled = false


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //возврат к текущему питомцу

        dataHelper = DataHelper(requireContext().applicationContext)
        if (id_check == 0 || id_check == -1) {
            resetAction()
        }
        homeViewModel.idState.observe(viewLifecycleOwner) {
            id_check = it
        }
        homeViewModel.imgState.observe(viewLifecycleOwner) {
            save_img = it
        }
        startStopAction()

        binding.btnSettings.setOnClickListener{p2 ->
            CoroutineScope(IO).launch {
                dbViewModel.updatePetStat(
                    requireActivity().application, id_check,
                    binding.statHunger.text.toString(),  binding.statHealth.text.toString(),  binding.statEnergy.text.toString()
                )
            }
            p2.findNavController().navigate(R.id.action_navigation_home_to_navigation_settings)
        }

        //таймер
        if(dataHelper.timerCounting())
        { startTimer() }
        else {
            stopTimer()
            if(dataHelper.startTime() != null &&  dataHelper.stopTime() != null)
            {
                val time = Date().time - calcRestartTime().time
                activity?.runOnUiThread {
                    /*binding.time.text = homeViewModel.timeStringFromLong(time).toString()
                    homeViewModel.timeStringFromLong(time)
                    homeViewModel.timeStr.observe(viewLifecycleOwner){
                        binding.time.text = it
                    }*/
                    binding.time.text = timeStringFromLong(time)
                }

            }
        }
        timer.schedule(TimeTask(), 0, 500)

        if (save_img != 0) binding.petMain.setImageResource(save_img)

        binding.meatBtn.setOnClickListener {
            hunger += 1
            addFoodStat(hunger)


        }
        binding.fishBtn.setOnClickListener {
            if (hunger < 99) hunger += 2 else hunger += 1
            addFoodStat(hunger)

        }
        binding.milkBtn.setOnClickListener {
            if (hunger < 98) hunger += 3 else if (hunger < 99) hunger += 2 else hunger += 1
            //hunger += 3
            addFoodStat(hunger)

        }
        binding.moonBtn.setOnClickListener {
             energy += 1
            if (energy > 99.0) {
                binding.moonBtn.isEnabled =false
                binding.statEnergy.text = energy.toInt().toString()
            }
            else {
                binding.statEnergy.text = energy.toInt().toString()
                CoroutineScope(IO).launch {
                    dbViewModel.updatePetStat(
                        requireActivity().application,
                        id_check,
                        hunger.toInt().toString(),
                        health.toInt().toString(),
                        energy.toInt().toString()
                    )
                }
            }
        }
        binding.syringeBtn.setOnClickListener {
            health += 1
            addCureStat(health)

        }
        binding.pillBtn.setOnClickListener {
            if (health < 99) health += 2 else health += 1
            //health += 2
            addCureStat(health)

        }
        binding.btnEvent.setOnClickListener {
                if (flag_event == 0) {
                //получить строку из бд
                dbViewModel.getEvent(requireActivity().application)
                dbViewModel.liveDataEvent?.observe(viewLifecycleOwner, Observer {
                    showDialog(it.Event, "EVENT")
                    type_event = it.Type
                    x_number = it.XNumber
                    event_time = it.EventTime
                    if (event_time == 60) event_time -= 1
                    type_stat = it.StatType

                })
                flag_event = 1
                gen = 1
                    binding.btnEvent.isEnabled = false
                }

        }

        // получение ид текущего питомца
        if (id_check != 0 && id_check != -1 ) {
            dbViewModel.getPetDetails(requireActivity().application, id_check)
            with(binding) {
                textId.text = id_check.toString()
            }
        }
        // отображаем на экране текущую информацию из liveDataPet
        dbViewModel.liveDataPet?.observe(viewLifecycleOwner, Observer {
            with(binding) {
                homeViewModel.onSaveId(it.Id!!)
                textId.text = it.Id.toString()
                setName.text = it.Petname
                setAge.text = it.Petage
                hunger = it.Pethunger.toDoubleOrNull()!!
                checkStat(hunger,"food", id_check)
                statHunger.text = hunger.toInt().toString()

                health = it.Pethealth.toDoubleOrNull()!!
                checkStat(health,"health", id_check)
                statHealth.text = health.toInt().toString()

                energy = it.Petenergy.toDoubleOrNull()!!
                checkStat(energy,"energy", id_check)
                statEnergy.text = energy.toInt().toString()
            }
        })

    }
    //проверка, что значение не 0, иначе питомец погибает
    private fun checkStat(stat: Double, st: String, id: Int) {
        if (stat > 99) {
            binding.pillBtn.isEnabled = false
            binding.syringeBtn.isEnabled = false
            binding.meatBtn.isEnabled = false
            binding.fishBtn.isEnabled = false
            binding.milkBtn.isEnabled = false
            binding.moonBtn.isEnabled = false
        }
        else {
            binding.pillBtn.isEnabled = true
            binding.syringeBtn.isEnabled = true
            binding.meatBtn.isEnabled = true
            binding.fishBtn.isEnabled = true
            binding.milkBtn.isEnabled = true
            binding.moonBtn.isEnabled = true
        }
        if (stat < 1) {
            showDialog("Lack of ${st}: GAME OVER", "END")
            CoroutineScope(IO).launch {
                dbViewModel.deletePet(requireActivity().application, id)
            }
            id_check = -1
            save_img = 0
            findNavController(this).navigate(R.id.action_navigation_home_to_navigation_start)
        }
    }
    private fun addCureStat(stat: Double) {
        if (stat > 99.0) {
            binding.pillBtn.isEnabled = false
            binding.syringeBtn.isEnabled = false
            binding.statHealth.text = stat.toInt().toString()
        }
        else {
            binding.statHealth.text = stat.toInt().toString()
            CoroutineScope(IO).launch {
                dbViewModel.updatePetStat(
                    requireActivity().application, id_check,
                    hunger.toInt().toString(), health.toInt().toString(), energy.toInt().toString()
                )
            }
        }
    }

    private fun addFoodStat(stat: Double) {
        if (stat > 99.0) {
            binding.meatBtn.isEnabled = false
            binding.fishBtn.isEnabled = false
            binding.milkBtn.isEnabled = false
            binding.statHunger.text = stat.toInt().toString()
        }
        else {
            binding.statHunger.text = stat.toInt().toString()
            CoroutineScope(IO).launch {
                dbViewModel.updatePetStat(
                    requireActivity().application, id_check,
                    hunger.toInt().toString(), health.toInt().toString(), energy.toInt().toString()
                )
            }
       }
    }
    private fun getSeconds(time: Long, flag_event: Int) {
        val seconds = (time / 1000) % 60
        val measure= String.format("%02d", seconds)
        //забираем 1 пункт по прошествию каждых 20 сек

        removeStat(measure,flag_event)
    }
    private fun removeStat(measure: String, fl: Int)  {
        var event_time = measure_start.toString()
        //если время события 60 секунд
        if (measure_start == 0 || measure_start < 10) event_time = "0$event_time"
        Log.d("errr",fl.toString()+" "+event_time+"!!!!")
        if (fl == 0) {
            when (measure) {
                "59", "20", "40" -> {
                    hunger -= 0.5
                    health -= 0.5
                    energy -= 0.5
                    binding.statHunger.text = hunger.toInt().toString()
                    binding.statHealth.text = health.toInt().toString()
                    binding.statEnergy.text = energy.toInt().toString()

                    CoroutineScope(IO).launch {
                        dbViewModel.updatePetStat(
                            requireActivity().application, id_check,
                            hunger.toString(), health.toString(), energy.toString()
                        )
                    }

                }
            }

        } else if (fl == 1) {
            if(measure == event_time) {
                Log.d("errr",measure+"!")
                measure_start = 0
                flag_event = 0
                binding.btnEvent.isEnabled = true
                //return flag_event
            }
            else {
                when (measure) {
                    "59", "20", "40" -> {
                        measureEvent()


                        CoroutineScope(IO).launch {
                            dbViewModel.updatePetStat(
                                    requireActivity().application, id_check,
                                    hunger.toString(), health.toString(), energy.toString()
                            )
                        }
                    }
                }

            }
        }
       //return flag_event
    }

    private fun measureEvent() {
        if (type_event == "+") {
            if (type_stat == "Hunger" && hunger < 100.0) {
                hunger += 0.5 * x_number; health -= 0.5; energy -= 0.5
            }
            else if (type_stat == "Health" && health < 100.0) {
                hunger -= 0.5; health += 0.5 * x_number; energy -= 0.5
            }
            else if (type_stat == "Energy" && energy < 100.0) {
                hunger -= 0.5; health -= 0.5; energy += 0.5 * x_number
            }
            else if (type_stat == "All" && hunger <= 99.0 && health <= 99.0 && energy <= 99.0) {
                hunger += 0.5 * x_number; health += 0.5 * x_number; energy += 0.5 * x_number
            }
        }
        else if (type_event == "-" && hunger >= 1.0 && health >= 1.0 && energy >= 1.0) {
            if (type_stat == "Hunger") {
                hunger -= 0.5 * x_number; health -= 0.5; energy -= 0.5
            }
            else if (type_stat == "Health") {
                hunger -= 0.5; health -= 0.5 * x_number; energy -= 0.5
            }
            else if (type_stat == "Energy") {
                hunger -= 0.5; health -= 0.5; energy -= 0.5 * x_number
            }
            else if (type_stat == "All") {
                hunger -= 0.5 * x_number; health -= 0.5 * x_number; energy -= 0.5 * x_number
            }
        }
        else if (type_event == "0") {
            if (type_stat == "Hunger") {
                hunger = hunger ; health -= 0.5; energy -= 0.5
            }
            else if (type_stat == "Health") {
                hunger -= 0.5; health += 0; energy -= 0.5
            }
            else if (type_stat == "Energy") {
                hunger -= 0.5; health -= 0.5; energy += 0
            }
            else if (type_stat == "All") {
                hunger += 0; health += 0; energy += 0
            }

        }
        binding.statHunger.text = hunger.toInt().toString()
        binding.statHealth.text = health.toInt().toString()
        binding.statEnergy.text = energy.toInt().toString()
    }

    override fun onResume() {
        super.onResume()
        if (save_img != 0) binding.petMain.setImageResource(save_img)
        startTimer()
        if (flag_event == 0 && flag == 1) binding.btnEvent.isEnabled = true
        else if ( flag == 0 ) binding.btnEvent.isEnabled = false
        dbViewModel.getPetDetails(requireActivity().application,id_check)
        /*Toast.makeText(requireContext().applicationContext,"resume$flag_event",Toast.LENGTH_SHORT).show()*/
    }
    private inner class TimeTask(): TimerTask() {
       override fun run() {
            if (dataHelper.timerCounting()) {
                val time = Date().time - dataHelper.startTime()!!.time
                activity?.runOnUiThread {
                    binding.time.text = timeStringFromLong(time)

                    if (flag_event == 1 && gen == 1 && flag == 1) {
                        val seconds = (time / 1000) % 60
                        //вычисление продолжительности события
                        measure_start = String.format("%02d", seconds).toIntOrNull()!! + event_time
                        if (measure_start > 59) measure_start -= 60
                        gen = 0
                        Log.d("errr", measure_start.toString())
                        //binding.btnEvent.isEnabled = false
                    }
                    getSeconds(time, flag_event)
                }
            }
       }
    }
    private fun resetAction() {
        dataHelper.setStopTime(null)
        dataHelper.setStartTime(null)
        stopTimer()
        activity?.runOnUiThread {
            binding.time.text = timeStringFromLong(0)
        }
    }
    private fun timeStringFromLong(ms: Long): String {
            val seconds = (ms / 1000) % 60
            val minutes = (ms / (1000 * 60) % 60)
            val hours = (ms / (1000 * 60 * 60) % 24)
            return makeTimeString(hours, minutes, seconds)

    }
    private fun makeTimeString(hours: Long, minutes: Long, seconds: Long): String {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
    private fun stopTimer() {
            dataHelper.setTimerCounting(false)
    }
    private fun startTimer() {
            dataHelper.setTimerCounting(true)
    }
    private fun startStopAction() {
        if (dataHelper.timerCounting()) {
                dataHelper.setStopTime(Date())
                stopTimer()
        } else {
            if (dataHelper.stopTime() != null) {
                dataHelper.setStartTime(calcRestartTime())
                dataHelper.setStopTime(null)
            } else {
                dataHelper.setStartTime(Date())
                }
            startTimer()
        }
    }
    private fun calcRestartTime(): Date {
        val diff = dataHelper.startTime()!!.time - dataHelper.stopTime()!!.time
        return Date(System.currentTimeMillis() + diff)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
            inflater.inflate(R.menu.menu_items, menu);
            val item = menu.findItem(R.id.button_item)
            val btn1 = item.actionView!!.findViewById<Button>(R.id.button1)
            btn1.setOnClickListener {
                dropDownMenuIconItem = binding.dropdownIconFood
                homeViewModel.onButtonClicked(dropDownMenuIconItem)
            }
            val btn2 = item.actionView!!.findViewById<Button>(R.id.button2)
            btn2.setOnClickListener {
                dropDownMenuIconItem = binding.dropdownIconCure
                homeViewModel.onButtonClicked(dropDownMenuIconItem)
            }
            val btn3 = item.actionView!!.findViewById<Button>(R.id.button3)
            btn3.setOnClickListener {
                dropDownMenuIconItem = binding.dropdownIconSleep
                homeViewModel.onButtonClicked(dropDownMenuIconItem)
            }

        }
    fun showDialog(body1: String, title1:String) {
        val dialog = Dialog(activity as AppCompatActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_event)
        val title: TextView? = dialog.findViewById(R.id.event_head)
        title?.text = title1
        val body: TextView? = dialog.findViewById(R.id.input_event)
        body?.text = body1
        val okBtn: Button? = dialog.findViewById(R.id.ok_button)
        okBtn?.setOnClickListener {
            dialog.dismiss()
        }
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }
    }


