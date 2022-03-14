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
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.trackinggoals.R
import com.example.trackinggoals.databinding.FragmentGoalsStepFirstBinding
import com.example.trackinggoals.model.Repositories
import com.example.trackinggoals.navigator
import com.example.trackinggoals.screens.GoalsConstructorFragment
import com.example.trackinggoals.viewModelCreator
import java.util.*

class GoalsStepFirstFragment : Fragment() {
    private lateinit var binding: FragmentGoalsStepFirstBinding
    private lateinit var selectedImage: Uri
    private lateinit var selectedBitmap: Bitmap
    private val viewModel by viewModelCreator { GoalsStepFirstViewModel(Repositories.goalsRepository) }

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
        binding = FragmentGoalsStepFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.textGoals.observe(viewLifecycleOwner) {
            binding.editTextInputGoalsStepFirst.setText(it)
        }
        viewModel.photo.observe(viewLifecycleOwner) {
            if (it !== "") {
                binding.imageViewGoalsStepFirst.setImageResource(R.drawable.ic_photo_default)
            }
        }

        viewModel.dataExecution.observe(viewLifecycleOwner) {
            binding.buttonGoalsStepSecondChooseDat.text = it

        }

        binding.framePicGoalsStepFirst.setOnClickListener {
            loadImage()
            binding.buttonGoalsStepFirstChoosePic.visibility = View.INVISIBLE
//            val photoBottomSheetDialogFragment = PhotoBottomSheetDialogFragment()
//            photoBottomSheetDialogFragment.show(childFragmentManager, PhotoBottomSheetDialogFragment.TAG)
        }

        binding.buttonGoalsStepSecondChooseDat.setOnClickListener {
            val cal = Calendar.getInstance()
            val y = cal.get(Calendar.YEAR)
            val m = cal.get(Calendar.MONTH)
            val d = cal.get(Calendar.DAY_OF_MONTH)
            val datepickerdialog = DatePickerDialog(requireActivity(),
                { _, year, month, day ->
                    binding.buttonGoalsStepSecondChooseDat.text = "$day $month, $year"
                }, y, m, d)
            datepickerdialog.show()
        }



        binding.buttonGoalsStepFirstNext.setOnClickListener {
            val goalsId = requireArguments().getInt(ARG_GOALS_ID)
            viewModel.updateTextGoals(binding.editTextInputGoalsStepFirst.text.toString(), goalsId)
            viewModel.updateDataGoals(binding.buttonGoalsStepSecondChooseDat.text.toString(),goalsId)
//            viewModel.updatePhototGoals()
            navigator().showGoalsStepSecond(goalsId)
        }


        binding.imageButtomBackStepFirst.setOnClickListener {
            when (binding.editTextInputGoalsStepFirst.text.toString()) {
                "" -> showAlertDialogStepFirstBack()
                else -> {
                    //возврат на fragment_list_goals
                }
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
                    binding.imageViewGoalsStepFirst.setImageBitmap(selectedBitmap)
                } else {
                    selectedBitmap =
                        MediaStore.Images.Media.getBitmap(it.contentResolver, selectedImage)
                    binding.imageViewGoalsStepFirst.setImageBitmap(selectedBitmap)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun showAlertDialogStepFirstBack() {

        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> navigator().goBaseMenu()
                DialogInterface.BUTTON_NEGATIVE -> Log.d("dialog", "Dialog dismissed")
            }
        }
        val dialog = AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setTitle(R.string.alert_dialog_exit_title)
            .setMessage(R.string.alert_dialog_exit_text)
            .setPositiveButton(R.string.alert_dialog_action_exit, listener)
            .setNegativeButton(R.string.alert_dialog_action_return, listener)
            .create()
        dialog.show()
    }


    companion object {
        private const val ARG_GOALS_ID = "ARG_GOALS_ID"

        fun newInstance(goalsId: Int): GoalsStepFirstFragment {
            val fragment = GoalsStepFirstFragment()
            fragment.arguments = bundleOf(ARG_GOALS_ID to goalsId)
            return fragment
        }
    }
}