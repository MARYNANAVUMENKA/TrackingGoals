package com.example.trackinggoals.screens.goals.constructor

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
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
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.trackinggoals.databinding.FragmentGoalsConstructorBinding
import com.example.trackinggoals.model.Repositories
import com.example.trackinggoals.navigator
import com.example.trackinggoals.viewModelCreator
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*
import com.bumptech.glide.Glide
import com.example.trackinggoals.R
import com.example.trackinggoals.screens.goals.dialogs.PictureChoiceDialogFragment
import com.example.trackinggoals.screens.goals.picture.PictureChoiceFragment
import com.example.trackinggoals.screens.goals.picture.PictureChoiceFragmentListener
import java.io.*


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
        binding = FragmentGoalsConstructorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editTextInputTextGoals.requestFocus()
        showKeyboard(binding.editTextInputTextGoals)
        setupSingleChoiceDialogFragmentListener()
        setupPictureChoiceFragmentListeners()

        viewModel.textGoals.observe(viewLifecycleOwner) {
            binding.editTextInputTextGoals.setText(it)
        }
        viewModel.photo.observe(viewLifecycleOwner) {
            if (it != "") {
                binding.buttonGoalsChoosePic.visibility = View.INVISIBLE
                if (it.substringBefore(':') == "https") {
                    Glide.with(requireContext())
                        .load(it)
                        .centerCrop()
                        .into(binding.imageViewGoalsPicture)
                } else {
                    val file = readFile(requireContext(), it)
                    Glide.with(requireContext())
                        .asBitmap()
                        .load(file)
                        .centerCrop()
                        .into(binding.imageViewGoalsPicture)
                }
            }
        }

        viewModel.dataExecution.observe(viewLifecycleOwner) {
            binding.buttonGoalsChooseDat.text = it
        }
        viewModel.unit.observe(viewLifecycleOwner) {
            binding.autoCompleteTextGoalsUnit.clearListSelection()
            binding.autoCompleteTextGoalsUnit.setText(it)
        }
        viewModel.progress.observe(viewLifecycleOwner) {
        }

        viewModel.quantity.observe(viewLifecycleOwner) {
            if (it != 0) {
                binding.editTextGoalsWant.setText(it.toString())
            }
        }
        viewModel.criterion.observe(viewLifecycleOwner) {
            binding.editTextGoalsCriterion.setText(it)
        }

        binding.imageViewGoalsPicture.setOnClickListener {
            binding.buttonGoalsChoosePic.visibility = View.INVISIBLE
            showSingleChoiceDialogFragment()
        }

        binding.buttonGoalsChooseDat.setOnClickListener {
            val cal = Calendar.getInstance()
            val y = cal.get(Calendar.YEAR)
            val m = cal.get(Calendar.MONTH)
            val d = cal.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireActivity(),
                { _, year, month, day ->
                    val calendar = Calendar.getInstance()
                    calendar.set(year, month, day)
                    val data = SimpleDateFormat("dd MMMM, yyyy").format(calendar.time)
                    binding.buttonGoalsChooseDat.text = data
                }, y, m, d
            )
            datePickerDialog.show()
        }
        val items = resources.getStringArray(R.array.unit)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        binding.autoCompleteTextGoalsUnit.setAdapter(adapter)

        binding.autoCompleteTextGoalsUnit.setOnItemClickListener { parent, _, position, _ ->
            val item = parent.getItemAtPosition(position).toString()
        }

        binding.buttonGoalsFinishEdit.setOnClickListener {
            val goalsId = requireArguments().getInt(ARG_GOALS_ID)
            if (binding.editTextInputTextGoals.text.toString() == "" || binding.editTextInputTextGoals.text.isNullOrEmpty() || binding.editTextGoalsWant.text.toString() == "0" || binding.editTextGoalsWant.text.isNullOrEmpty()) {
                showAlertDialogConstructor()
            } else {
                viewModel.updateTextGoals(binding.editTextInputTextGoals.text.toString(), goalsId)
                viewModel.updateDataGoals(binding.buttonGoalsChooseDat.text.toString(), goalsId)
                viewModel.updateQuantityGoals(
                    binding.editTextGoalsWant.text.toString().toInt(),
                    goalsId
                )
                viewModel.updateUnitGoals(
                    binding.autoCompleteTextGoalsUnit.text.toString(),
                    goalsId
                )
                viewModel.updateCriterionGoals(
                    binding.editTextGoalsCriterion.text.toString().uppercase(Locale.getDefault()),
                    goalsId
                )
                navigator().goBaseMenu()
            }

        }

        binding.imageButtonBackGoals.setOnClickListener {
            if (binding.editTextInputTextGoals.text.toString() == "" || binding.editTextInputTextGoals.text.isNullOrEmpty()) {
                showAlertDialogConstructor()
            } else {
                navigator().goBaseMenu()
            }
        }
    }

    private fun showSingleChoiceDialogFragment() {
        PictureChoiceDialogFragment.show(parentFragmentManager)
    }

    private fun setupSingleChoiceDialogFragmentListener() {
        PictureChoiceDialogFragment.setupListener(parentFragmentManager, this) {
            when (it) {
                KEY_ACTION_RESPONSE_CHOOSE -> chooseImage()
                KEY_ACTION_RESPONSE_LOAD -> loadImage()
                KEY_ACTION_RESPONSE_CREATE -> createImage()
                else -> {}
            }
        }
    }

    private fun chooseImage() {
        showPictureChoiceFragment(KEY_FIRST_PICTURE_REQUEST_KEY)
    }

    private fun showPictureChoiceFragment(requestKey: String) {
        parentFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .add(
                R.id.fragmentContainer,
                PictureChoiceFragment.newInstance(
                    requestKey,
                    requireArguments().getInt(ARG_GOALS_ID)
                )
            )
            .commit()
    }

    private fun setupPictureChoiceFragmentListeners() {
        val goalsId = requireArguments().getInt(ARG_GOALS_ID)
        val listener: PictureChoiceFragmentListener = { requestKey, pathImage ->
            when (requestKey) {
                KEY_FIRST_PICTURE_REQUEST_KEY -> {
                    if (pathImage.isNotEmpty() && pathImage != "") {
                        viewModel.updatePhotoGoals(pathImage, goalsId)
                        binding.buttonGoalsChoosePic.visibility = View.INVISIBLE
                        Glide.with(requireContext())
                            .load(pathImage)
                            .centerCrop()
                            .into(binding.imageViewGoalsPicture)
                    }
                }
            }
        }
        PictureChoiceFragment.setupListener(
            parentFragmentManager, this,
            KEY_FIRST_PICTURE_REQUEST_KEY, listener
        )
    }

    private fun readFile(context: Context, fileName: String): File {
        return File(context.filesDir, fileName)
    }

    private fun savePhotoToInternalStorage(filename: String, bmp: Bitmap): Boolean {
        return try {
            requireActivity().openFileOutput("$filename.jpg", MODE_PRIVATE).use { stream ->
                if (!bmp.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw IOException("Couldn't save bitmap.")
                }
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }


    private fun loadImage() {
        activity?.let {
            if (ContextCompat.checkSelfPermission(
                    it.applicationContext,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            } else {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 2)

            }
        }
    }

    private fun createImage() {
        activity?.let {
            if (ContextCompat.checkSelfPermission(
                    it.applicationContext,
                    android.Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(android.Manifest.permission.CAMERA), 3)
            } else {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(takePictureIntent, 4)

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
        } else if (requestCode == 3) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(takePictureIntent, 4)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val goalsId = requireArguments().getInt(ARG_GOALS_ID)
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            selectedImage = data.data!!

        }
        try {
            requireActivity().let {
                if (Build.VERSION.SDK_INT >= 28) {
                    if (requestCode == 2) {
                        val source = ImageDecoder.createSource(it.contentResolver, selectedImage!!)
                        selectedBitmap = ImageDecoder.decodeBitmap(source)
                        savePhotoToInternalStorage(UUID.randomUUID().toString(), selectedBitmap)
                        val nameFile = UUID.randomUUID().toString()
                        if (savePhotoToInternalStorage(nameFile, selectedBitmap)) {
                            viewModel.updatePhotoGoals("$nameFile.jpg", goalsId)
                        }
                        binding.buttonGoalsChoosePic.visibility = View.INVISIBLE
                        binding.imageViewGoalsPicture.setImageBitmap(selectedBitmap)
                    } else if (requestCode == 4) {
                        val selectedBtm = data?.extras?.get("data") as Bitmap
                        savePhotoToInternalStorage(UUID.randomUUID().toString(), selectedBtm)
                        val nameFile = UUID.randomUUID().toString()
                        if (savePhotoToInternalStorage(nameFile, selectedBtm)) {
                            viewModel.updatePhotoGoals("$nameFile.jpg", goalsId)
                        }
                        binding.buttonGoalsChoosePic.visibility = View.INVISIBLE
                        binding.imageViewGoalsPicture.setImageBitmap(selectedBtm)
                    }
                } else {
                    if (requestCode == 2) {
                        selectedBitmap =
                            MediaStore.Images.Media.getBitmap(it.contentResolver, selectedImage)
                        savePhotoToInternalStorage(UUID.randomUUID().toString(), selectedBitmap)
                        val nameFile = UUID.randomUUID().toString()
                        if (savePhotoToInternalStorage(nameFile, selectedBitmap)) {
                            viewModel.updatePhotoGoals("$nameFile.jpg", goalsId)
                        }
                        binding.buttonGoalsChoosePic.visibility = View.INVISIBLE
                        binding.imageViewGoalsPicture.setImageBitmap(selectedBitmap)
                    } else if (requestCode == 4) {
                        val selectedBtm = data?.extras?.get("data") as Bitmap
                        savePhotoToInternalStorage(UUID.randomUUID().toString(), selectedBtm)
                        val nameFile = UUID.randomUUID().toString()
                        if (savePhotoToInternalStorage(nameFile, selectedBtm)) {
                            viewModel.updatePhotoGoals("$nameFile.jpg", goalsId)
                        }
                        binding.buttonGoalsChoosePic.visibility = View.INVISIBLE
                        binding.imageViewGoalsPicture.setImageBitmap(selectedBtm)
                    }
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
                    navigator().goBaseMenu()
                }
                DialogInterface.BUTTON_NEGATIVE -> Log.d("dialog", "Dialog dismissed")
            }
        }
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setCancelable(true)
            .setTitle(R.string.alert_dialog_complete_title)
            .setMessage(R.string.alert_dialog_complete_text)
            .setPositiveButton(R.string.alert_dialog_complete_action_complete, listener)
            .setNegativeButton(R.string.alert_dialog_complete_action_return, listener)
            .create()
        dialog.show()
    }

    private fun showKeyboard(view: View) {
        view.post {
            getInputMethodManager(view).showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun hideKeyboard(view: View) {
        getInputMethodManager(view).hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun getInputMethodManager(view: View): InputMethodManager {
        val context = view.context
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }


    companion object {
        private const val ARG_GOALS_ID = "ARG_GOALS_ID"

        @JvmStatic
        private val KEY_ACTION_RESPONSE_CHOOSE = "KEY_ACTION_RESPONSE_CHOOSE"

        @JvmStatic
        private val KEY_ACTION_RESPONSE_LOAD = "KEY_ACTION_RESPONSE_LOAD"

        @JvmStatic
        private val KEY_ACTION_RESPONSE_CREATE = "KEY_ACTION_RESPONSE_CREATE"

        @JvmStatic
        private val KEY_FIRST_PICTURE_REQUEST_KEY = "KEY_FIRST_PICTURE_REQUEST_KEY"

        fun newInstance(goalsId: Int): GoalsConstructorFragment {
            val fragment = GoalsConstructorFragment()
            fragment.arguments = bundleOf(ARG_GOALS_ID to goalsId)
            return fragment
        }
    }
}