package com.alwihbsyi.simplemodularization.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alwihbsyi.simplemodularization.R
import com.alwihbsyi.simplemodularization.core.adapter.UsersAdapter
import com.alwihbsyi.simplemodularization.core.data.model.Users
import com.alwihbsyi.simplemodularization.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
    private val adapter = UsersAdapter()
    var usernameStudent = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this
        binding.lifecycleOwner = this

        initData()
        observe()
    }

    private fun initData() {
        adapter.setOnClickItem { name ->
            Toast.makeText(this, "Ini namanya ${name.username}", Toast.LENGTH_SHORT).show()
        }
        binding.adapter = adapter
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    mainViewModel.users.collect { username ->
                        adapter.submitList(username)
                    }

                    mainViewModel.responseSave.collect { success ->
                        if(success){
                            Toast.makeText(this@MainActivity, "Berhasil menyimpan data", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    fun saveUsername() {
        if(usernameStudent.isEmpty()){
            Toast.makeText(this@MainActivity, "Harap isi form", Toast.LENGTH_SHORT).show()
            return
        }

        val newUsername = Users(username = usernameStudent)
        mainViewModel.addUsers(newUsername)
    }
}