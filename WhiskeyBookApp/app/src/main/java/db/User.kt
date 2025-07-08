package db
import android.content.Context
import android.util.Log
import com.whiskeybook.Whiskey
import kotlinx.serialization.*
import java.io.File
import kotlinx.serialization.json.Json


@Serializable
data class User(
    val userId: String,
    val password: String,
    val userName: String,
    val userMail: String,
    val whiskeyList: List<Whiskey> = emptyList()
)

object UserDataManager {
    private const val FILENAME :String = "UsersDataBase.json"
    private var usersList : List<User> = emptyList()
    fun createUser(
        context: Context,
        userId: String,
        userName: String,
        password: String,
        userMail: String,
        whiskeyList: List<Whiskey> = emptyList()
    ): User {
        val userList = loadUsersFromStorage(context).toMutableList()
        val newUser = User(
            userId = userId,
            password = password,
            userName = userName,
            userMail = userMail,
            whiskeyList = whiskeyList
        )
        userList.add(newUser)
        saveDatabase(context, userList)
        return newUser
    }

    fun userExist(
        context: Context,
        userId: String): Boolean
    {
        val userList = loadUsersFromStorage(context).toMutableList()
        // Verify if the User Id already exist
        if (userList.any { it.userId == userId }) {
            Log.w("createUser", "UserID: '$userId' already exist.")
            return true
        }
        return false
    }
    fun authenticateUser(context: Context, inputId: String, inputPassword: String): User? {
        val users = loadUsersFromStorage(context).toMutableList()
        val user = users.find { it.userId == inputId}
        if(user != null)
        {
            if(user.userId == inputId && user.password == inputPassword)
                return  user
        }
        return null
    }


    /*
    fun deleteUser(userId: String): Boolean {
        val result = userList.removeIf { it.userId == userId }
        if (result) {
            saveDatabase()
        }
        return result
    }
    */

    //------------------TODO:  refactor when connected to database ---------------//
    private fun copyJsonFromAssetsToStorage(context: Context) {
        try {
            val inputStream = context.assets.open(FILENAME)
            val outFile = File(context.filesDir, FILENAME)

            inputStream.use { input ->
                outFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        } catch (e: Exception) {
            Log.e("UserDataManager", "Error  to copy JSON from assets to storage", e)
        }
    }
    //---------------------------------//

    // load users in storage. read and write options
    private fun loadUsersFromStorage(context: Context): List<User> {
        val json = Json { ignoreUnknownKeys = true }
        val file = File(context.filesDir, FILENAME)

        if (!file.exists()) {
            copyJsonFromAssetsToStorage(context)
        }
        return try {
            val jsonString = file.readText()
            usersList =json.decodeFromString(jsonString)
            usersList
        } catch (e: Exception) {
            Log.e("UserDataManager", "Error to read JSON file", e)
            emptyList()
        }
    }

    private fun saveDatabase(context: Context, userList: List<User>) {
        val json = Json { prettyPrint = true }
        val file = File(context.filesDir, FILENAME)

        try {
            val jsonString = json.encodeToString(userList)
            file.writeText(jsonString)

        } catch (e: Exception) {
            Log.e("UserDataManager", "Error to storage JSON file", e)
        }
    }

}
