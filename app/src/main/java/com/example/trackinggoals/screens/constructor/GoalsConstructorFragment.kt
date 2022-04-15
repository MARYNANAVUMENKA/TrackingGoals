package com.example.trackinggoals.screens.constructor

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import com.example.trackinggoals.databinding.FragmentGoalsConstructorBinding
import com.example.trackinggoals.model.Repositories
import com.example.trackinggoals.viewModelCreator
import java.text.SimpleDateFormat
import java.util.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.trackinggoals.R
import com.example.trackinggoals.findTopNavController
import com.example.trackinggoals.screens.dialogs.PictureChoiceDialogFragment
import com.example.trackinggoals.screens.constructor.picture.PictureChoiceFragment
import java.io.*
import android.graphics.BitmapFactory

class GoalsConstructorFragment : Fragment() {
    private lateinit var binding: FragmentGoalsConstructorBinding
    private lateinit var selectedImage: Uri
    private var selectedBitmap: Bitmap? = null
    private var pathPicture: String = ""

    private val viewModel by viewModelCreator { GoalsConstructorViewModel(Repositories.goalsRepository) }

    private val args by navArgs<GoalsConstructorFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadGoals(getGoalsId())
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
        setupSingleChoiceDialogFragmentListener()
        setupPictureChoiceFragmentListener()
        if (getGoalsId()==1){
            binding.buttonGoalsFinishEdit.isEnabled = false
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.buttonGoalsFinishEdit.isEnabled = false
                if (binding.editTextInputTextGoals.text.isNotEmpty() &&
                    binding.editTextGoalsWant.editableText.isNotEmpty()
                    && binding.editTextGoalsCriterion.editableText.isNotEmpty() &&
                    binding.autoCompleteTextGoalsUnit.text.isNotEmpty()
                    && binding.buttonGoalsChooseDat.text.isNotEmpty()
                ) {
                    binding.buttonGoalsFinishEdit.isEnabled = true
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        binding.editTextInputTextGoals.addTextChangedListener(textWatcher)
        binding.editTextGoalsWant.addTextChangedListener(textWatcher)
        binding.editTextGoalsCriterion.addTextChangedListener(textWatcher)
        binding.autoCompleteTextGoalsUnit.addTextChangedListener(textWatcher)
        binding.buttonGoalsChooseDat.addTextChangedListener(textWatcher)

        viewModel.photo.observe(viewLifecycleOwner) {
            if (it == "") {
                binding.imageViewGoalsPicture.alpha = 0.7F
                Glide.with(requireContext())
                    .load(DEFAULT_PICTURE)
                    .centerCrop()
                    .into(binding.imageViewGoalsPicture)
            } else {
                binding.buttonGoalsChoosePic.visibility = View.INVISIBLE
                binding.imageViewGoalsPicture.alpha = 1F
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
                    val filePath: String = file.path
                    val bitmap = BitmapFactory.decodeFile(filePath)
                    selectedBitmap = bitmap
                }
            }
        }

        viewModel.textGoals.observe(viewLifecycleOwner) {
            if (it != "") {
                binding.editTextInputTextGoals.setText(it)
            }
        }

        viewModel.dataExecution.observe(viewLifecycleOwner) {
            if (it != "") {
                binding.buttonGoalsChooseDat.text = it
            }
        }
        viewModel.quantity.observe(viewLifecycleOwner) {
            if (it != 0) {
                binding.editTextGoalsWant.setText(it.toString())
            }
        }

        viewModel.unit.observe(viewLifecycleOwner) {
            if (it != "") {
                binding.autoCompleteTextGoalsUnit.clearListSelection()
                binding.autoCompleteTextGoalsUnit.setText(it)
            }
        }

        viewModel.criterion.observe(viewLifecycleOwner) {
            if (it != "") {
                binding.editTextGoalsCriterion.setText(it)
            }
        }

        binding.imageViewGoalsPicture.setOnClickListener {
            binding.buttonGoalsChoosePic.visibility = View.INVISIBLE
            showSingleChoiceDialogFragment()
        }

        binding.buttonGoalsChooseDat.setOnClickListener {
            val cal = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                requireActivity(),
                { _, year, month, day ->
                    val calendar = Calendar.getInstance()
                    calendar.set(year, month, day)
                    val data = SimpleDateFormat("dd MMMM, yyyy").format(calendar.time)
                    binding.buttonGoalsChooseDat.text = data
                }, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
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
            if (getGoalsId() == 1 && pathPicture != "") {
                viewModel.saveGoals(
                    pathPicture, binding.editTextInputTextGoals.text.toString(),
                    binding.buttonGoalsChooseDat.text.toString(), 0,
                    binding.editTextGoalsWant.text.toString().toInt(),
                    binding.autoCompleteTextGoalsUnit.text.toString(),
                    binding.editTextGoalsCriterion.text.toString().uppercase(Locale.getDefault())
                )
            } else if (getGoalsId() == 1) {
                val nameFile = UUID.randomUUID().toString()
                selectedBitmap?.let { it1 -> savePhotoToInternalStorage(nameFile, it1) }
                viewModel.saveGoals(
                    "$nameFile.jpg", binding.editTextInputTextGoals.text.toString(),
                    binding.buttonGoalsChooseDat.text.toString(), 0,
                    binding.editTextGoalsWant.text.toString().toInt(),
                    binding.autoCompleteTextGoalsUnit.text.toString(),
                    binding.editTextGoalsCriterion.text.toString().uppercase(Locale.getDefault())
                )
            } else {
                if (pathPicture != "") {
                    viewModel.updatePhotoGoals(pathPicture, getGoalsId())
                } else {
                    val nameFile = UUID.randomUUID().toString()
                    if (selectedBitmap?.let { it1 ->
                            savePhotoToInternalStorage(
                                nameFile,
                                it1
                            )
                        } == true) {
                        viewModel.updatePhotoGoals("$nameFile.jpg", getGoalsId())
                    }
                }
                viewModel.updateTextGoals(
                    binding.editTextInputTextGoals.text.toString(),
                    getGoalsId()
                )
                viewModel.updateDataGoals(
                    binding.buttonGoalsChooseDat.text.toString(),
                    getGoalsId()
                )
                viewModel.updateQuantityGoals(
                    binding.editTextGoalsWant.text.toString().toInt(),
                    getGoalsId()
                )
                viewModel.updateUnitGoals(
                    binding.autoCompleteTextGoalsUnit.text.toString(),
                    getGoalsId()
                )
                viewModel.updateCriterionGoals(
                    binding.editTextGoalsCriterion.text.toString().uppercase(Locale.getDefault()),
                    getGoalsId()
                )
            }
            findTopNavController().navigate(R.id.tabsFragment, null, navOptions {
                popUpTo(R.id.tabsFragment) {
                    inclusive = true
                }
            })
        }

        binding.imageButtonBackGoals.setOnClickListener {
            findTopNavController().popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        updatePhoto()
    }

    private fun getGoalsId(): Int = args.goalsId

    private fun showSingleChoiceDialogFragment() {
        PictureChoiceDialogFragment.show(parentFragmentManager)
    }

    private fun setupSingleChoiceDialogFragmentListener() {
        PictureChoiceDialogFragment.setupListener(parentFragmentManager, this) {
            when (it) {
                KEY_ACTION_RESPONSE_CHOOSE -> chooseImage()
                KEY_ACTION_RESPONSE_LOAD -> loadImage()
                else -> {}
            }
        }
    }

    private fun chooseImage() {
        findTopNavController().navigate(R.id.action_goalsConstructorFragment_to_pictureChoiceFragment2)
    }

    private fun loadImage() {
        activity?.let {
            if (ContextCompat.checkSelfPermission(
                    it.applicationContext,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_CODE_PERMISSION
                )
            } else {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, REQUEST_CODE_START_ACTIVITY)

            }
        }
    }

    private fun updatePhoto() {
        if (pathPicture.substringBefore(':') == "https") {
            binding.buttonGoalsChoosePic.visibility = View.INVISIBLE
            binding.imageViewGoalsPicture.alpha = 1.0F
            Glide.with(requireContext())
                .load(pathPicture)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.imageViewGoalsPicture)
        }
    }


    private fun setupPictureChoiceFragmentListener() {
        parentFragmentManager.setFragmentResultListener(
            PictureChoiceFragment.REQUEST_CODE_PICTURE,
            viewLifecycleOwner
        ) { _, path ->
            val pathPictureResult = path.getString(PictureChoiceFragment.PATH_PICTURE_RESPONSE)
            if (pathPictureResult != null) {
                pathPicture = pathPictureResult
            }
        }
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, REQUEST_CODE_START_ACTIVITY)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_START_ACTIVITY && resultCode == Activity.RESULT_OK && data != null) {
            selectedImage = data.data!!

        }
        try {
            requireActivity().let {
                if (Build.VERSION.SDK_INT >= 28) {
                    if (requestCode == REQUEST_CODE_START_ACTIVITY) {
                        val source = ImageDecoder.createSource(it.contentResolver, selectedImage!!)
                        selectedBitmap = ImageDecoder.decodeBitmap(source)
                        pathPicture = ""
                        binding.buttonGoalsChoosePic.visibility = View.INVISIBLE
                        binding.imageViewGoalsPicture.setImageBitmap(selectedBitmap)
                    }
                } else {
                    if (requestCode == REQUEST_CODE_START_ACTIVITY) {
                        selectedBitmap =
                            MediaStore.Images.Media.getBitmap(it.contentResolver, selectedImage)
                        pathPicture = ""
                        binding.buttonGoalsChoosePic.visibility = View.INVISIBLE
                        binding.imageViewGoalsPicture.setImageBitmap(selectedBitmap)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    companion object {

        private const val DEFAULT_PICTURE =
            "https://images.unsplash.com/photo-1601359169004-f9663c04c892?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80"

        @JvmStatic
        private val KEY_ACTION_RESPONSE_CHOOSE = "KEY_ACTION_RESPONSE_CHOOSE"

        @JvmStatic
        private val KEY_ACTION_RESPONSE_LOAD = "KEY_ACTION_RESPONSE_LOAD"

        @JvmStatic
        private val REQUEST_CODE_PERMISSION = 1

        @JvmStatic
        private val REQUEST_CODE_START_ACTIVITY = 2

    }
}