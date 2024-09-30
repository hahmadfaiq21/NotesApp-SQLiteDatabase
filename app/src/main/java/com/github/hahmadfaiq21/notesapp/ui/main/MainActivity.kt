package com.github.hahmadfaiq21.notesapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.hahmadfaiq21.notesapp.data.Note
import com.github.hahmadfaiq21.notesapp.database.NoteHelper
import com.github.hahmadfaiq21.notesapp.databinding.ActivityMainBinding
import com.github.hahmadfaiq21.notesapp.helper.MappingHelper
import com.github.hahmadfaiq21.notesapp.ui.adapter.NoteAdapter
import com.github.hahmadfaiq21.notesapp.ui.add.AddUpdateNoteActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NoteAdapter

    val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.data != null) {
            when (result.resultCode) {
                AddUpdateNoteActivity.RESULT_ADD -> {
                    val note =
                        result.data?.getParcelableExtra<Note>(AddUpdateNoteActivity.EXTRA_NOTE) as Note
                    adapter.addItem(note)
                    binding.rvNotes.smoothScrollToPosition(adapter.itemCount - 1)
                    showSnackBarMessage("Add note complete")
                }

                AddUpdateNoteActivity.RESULT_UPDATE -> {
                    val note =
                        result.data?.getParcelableExtra<Note>(AddUpdateNoteActivity.EXTRA_NOTE) as Note
                    val position =
                        result.data?.getIntExtra(AddUpdateNoteActivity.EXTRA_POSITION, 0) as Int
                    adapter.updateItem(position, note)
                    binding.rvNotes.smoothScrollToPosition(position)
                    showSnackBarMessage("Update note complete")
                }

                AddUpdateNoteActivity.RESULT_DELETE -> {
                    val position =
                        result.data?.getIntExtra(AddUpdateNoteActivity.EXTRA_POSITION, 0) as Int
                    adapter.removeItem(position)
                    showSnackBarMessage("Delete note complete")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Notes"

        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        binding.rvNotes.setHasFixedSize(true)

        adapter = NoteAdapter(object : NoteAdapter.OnItemClickCallback {
            override fun onItemClicked(selectedNote: Note?, position: Int?) {
                val intent = Intent(this@MainActivity, AddUpdateNoteActivity::class.java)
                intent.putExtra(AddUpdateNoteActivity.EXTRA_NOTE, selectedNote)
                intent.putExtra(AddUpdateNoteActivity.EXTRA_POSITION, position)
                resultLauncher.launch(intent)
            }
        })
        binding.rvNotes.adapter = adapter
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, AddUpdateNoteActivity::class.java)
            resultLauncher.launch(intent)
        }

        if (savedInstanceState == null) {
            loadNotesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Note>(EXTRA_STATE)
            if (list != null) {
                adapter.listNotes = list
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listNotes)
    }

    private fun loadNotesAsync() {
        lifecycleScope.launch {
            binding.progressbar.visibility = View.VISIBLE
            val noteHelper = NoteHelper.getInstance(applicationContext)
            noteHelper.open()
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = noteHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            binding.progressbar.visibility = View.INVISIBLE
            val notes = deferredNotes.await()
            if (notes.size > 0) {
                adapter.listNotes = notes
            } else {
                adapter.listNotes = ArrayList()
                showSnackBarMessage("No data")
            }
            noteHelper.close()
        }
    }

    private fun showSnackBarMessage(message: String) {
        Snackbar.make(binding.rvNotes, message, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }
}