package com.example.trackinggoals.screens

import android.app.Activity
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.trackinggoals.R
import com.example.trackinggoals.databinding.FragmentGoalsConstructorBinding
import com.example.trackinggoals.model.Repositories
import com.example.trackinggoals.navigator
import com.example.trackinggoals.viewModelCreator
import java.text.SimpleDateFormat
import java.util.*

class GoalsConstructorFragment : Fragment() {
    private lateinit var binding: FragmentGoalsConstructorBinding
    private lateinit var selectedImage: Uri
    private lateinit var selectedBitmap: Bitmap
    private val viewModel by viewModelCreator { GoalsConstructorViewModel(Repositories.goalsRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val goalsId = requireArguments().getInt(ARG_GOALS_ID)
        viewModel.loadGoals(goalsId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentGoalsConstructorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.textGoals.observe(viewLifecycleOwner) {
            binding.editTextInputTextGoals.setText(it)
        }
        viewModel.photo.observe(viewLifecycleOwner) {
            if (it !== "") {
                binding.imageViewGoalsPicture.setImageResource(R.drawable.ic_photo_default)
            }
        }

        viewModel.dataExecution.observe(viewLifecycleOwner) {
            binding.buttonGoalsChooseDat.text = it
            binding.textViewDataTitleGoals.text=it

        }
        viewModel.unit.observe(viewLifecycleOwner){
            binding.autoCompleteTextGoalsUnit.clearListSelection()
            binding.autoCompleteTextGoalsUnit.setHint(it)
            binding.textViewGoalsQuantityUnit.text=it
        }
        viewModel.progress.observe(viewLifecycleOwner){
            binding.textViewGoalsQuantityNow.text= it.toString()
        }

        viewModel.quantity.observe(viewLifecycleOwner){
            binding.editTextGoalsWant.setText(it.toString())
            binding.textViewGoalsQuantityTotal.text=it.toString()
        }
        viewModel.criterion.observe(viewLifecycleOwner){
            binding.editTextGoalsCriterion.setText(it)
            binding.textViewTitleGoalsCriteria.text=it.uppercase(Locale.getDefault())
        }

        binding.imageViewGoalsPicture.setOnClickListener {
            loadImage()
            binding.buttonGoalsChoosePic.visibility = View.INVISIBLE
//            val photoBottomSheetDialogFragment = PhotoBottomSheetDialogFragment()
//            photoBottomSheetDialogFragment.show(childFragmentManager, PhotoBottomSheetDialogFragment.TAG)
        }

        binding.buttonGoalsChooseDat.setOnClickListener {
            val cal = Calendar.getInstance()
            val y = cal.get(Calendar.YEAR)
            val m = cal.get(Calendar.MONTH)
            val d = cal.get(Calendar.DAY_OF_MONTH)
            val datepickerdialog = DatePickerDialog(requireActivity(),
                { _, year, month, day ->
                    val calendar = Calendar.getInstance()
                    calendar.set(year, month, day)
                    val data = SimpleDateFormat("dd MMMM, yyyy").format(calendar.time)
                    binding.buttonGoalsChooseDat.text = data
                }, y, m, d
            )
            datepickerdialog.show()
        }
        val items = resources.getStringArray(R.array.unit)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        binding.autoCompleteTextGoalsUnit.setAdapter(adapter)

        binding.autoCompleteTextGoalsUnit.setOnItemClickListener { parent, _, position, _ ->

            val item = parent.getItemAtPosition(position).toString()
            binding.textViewGoalsQuantityUnit.text=item
        }



        binding.buttonGoalsFinishEdit.setOnClickListener {
            val goalsId = requireArguments().getInt(ARG_GOALS_ID)
            if (binding.editTextInputTextGoals.text.toString()==""||binding.editTextInputTextGoals.text.isNullOrEmpty()||binding.editTextGoalsWant.text.toString()=="0"||binding.editTextGoalsWant.text.isNullOrEmpty()){
                showAlertDialogConstructor()
            }else{
                viewModel.updateTextGoals(binding.editTextInputTextGoals.text.toString(), goalsId)
                viewModel.updateDataGoals(binding.buttonGoalsChooseDat.text.toString(), goalsId)
                viewModel.updateQuantityGoals(binding.editTextGoalsWant.text.toString().toInt(), goalsId)
                viewModel.updateUnitGoals(binding.autoCompleteTextGoalsUnit.text.toString(), goalsId)
                viewModel.updateCriterionGoals(binding.editTextGoalsCriterion.text.toString().uppercase(Locale.getDefault()),goalsId)
//            viewModel.updatePhototGoals()
                navigator().goBaseMenu()
            }

        }



        binding.imageButtomBackGoals.setOnClickListener {
            if (binding.editTextInputTextGoals.text.toString()==""||binding.editTextInputTextGoals.text.isNullOrEmpty()){
                showAlertDialogConstructor()
            }else{
                navigator().goBaseMenu()
            }
        }
    }


    fun loadImage() {
        activity?.let {
            if (ContextCompat.checkSelfPermission(
                    it.applicationContext,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            } else {
                val Intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(Intent, 2)

            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            selectedImage = data.data!!
        }
        try {
            context?.let {
                if (Build.VERSION.SDK_INT >= 28) {
                    val source = ImageDecoder.createSource(it.contentResolver, selectedImage!!)
                    selectedBitmap = ImageDecoder.decodeBitmap(source)
                    binding.imageViewGoalsPicture.setImageBitmap(selectedBitmap)
                } else {
                    selectedBitmap =
                        MediaStore.Images.Media.getBitmap(it.contentResolver, selectedImage)
                    binding.imageViewGoalsPicture.setImageBitmap(selectedBitmap)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun showAlertDialogConstructor() {

        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    viewModel.deleteGoals(requireArguments().getInt(ARG_GOALS_ID))
                    navigator().goBaseMenu()}
                DialogInterface.BUTTON_NEGATIVE -> Log.d("dialog", "Dialog dismissed")
            }
        }
        val dialog = AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setTitle(R.string.alert_dialog_complete_title)
            .setMessage(R.string.alert_dialog_complete_text)
            .setPositiveButton(R.string.alert_dialog_complete_action_complete, listener)
            .setNegativeButton(R.string.alert_dialog_complete_action_return, listener)
            .create()
        dialog.show()
    }


    companion object {
        private const val ARG_GOALS_ID = "ARG_GOALS_ID"

        fun newInstance(goalsId: Int): GoalsConstructorFragment {
            val fragment = GoalsConstructorFragment()
            fragment.arguments = bundleOf(ARG_GOALS_ID to goalsId)
            return fragment
        }
    }
}