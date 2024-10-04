package uz.ilmiddin1701.note.utils

import android.content.Context
import android.content.SharedPreferences

object MySharedPreferences {
    private const val NAME = "catch_file_name"
    private const val MODE = Context.MODE_PRIVATE

    private lateinit var preferences: SharedPreferences

    fun init(context: Context){
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation:(SharedPreferences.Editor) -> Unit){
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var statusBarColor: String
        get() = preferences.getString("keyStatusBarColor", "empty")!!
        set(value) = preferences.edit {
            it.putString("keyStatusBarColor", value)
        }

    var bottomNavBarColor:Int
        get() = preferences.getInt("keyBottomNavBar", 0)
        set(value) = preferences.edit{
            it.putInt("keyBottomNavBar", value)
        }

    var backgroundColor: String
        get() = preferences.getString("keyBackgroundColor", "empty")!!
        set(value) = preferences.edit {
            it.putString("keyBackgroundColor", value)
        }
}