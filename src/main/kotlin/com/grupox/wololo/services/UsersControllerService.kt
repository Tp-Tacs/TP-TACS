package com.grupox.wololo.services

import arrow.core.getOrHandle
import com.grupox.wololo.errors.CustomException
import com.grupox.wololo.model.Stats
import com.grupox.wololo.model.User
import com.grupox.wololo.model.helpers.LoginForm
import com.grupox.wololo.model.helpers.UserForm
import com.grupox.wololo.model.helpers.SHA512Hash
import com.grupox.wololo.configs.properties.SHA512Properties
import com.grupox.wololo.model.repos.RepoUsers
import com.grupox.wololo.model.helpers.UserPublicInfoWithoutStats
import com.grupox.wololo.model.helpers.getOrThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UsersControllerService {

    @Autowired
    private lateinit var sha512: SHA512Hash

    fun createUser(newUser: UserForm) {
        if(RepoUsers.getUserByName(newUser.mail).isRight())
            throw CustomException.BadRequest.IllegalUserException("There is already an user under that email")

        val user = User(3, newUser.username, newUser.mail, sha512.getSHA512(newUser.password), false, Stats(0,0)) // TODO el id se tiene que autoincrementar

        RepoUsers.insert(user)
    }

    fun checkUserCredentials(user: LoginForm): User {
        return RepoUsers.getUserByLogin(user, sha512.getSHA512(user.password)).getOrThrow()
    }

    fun getUsers(_username: String?): List<UserPublicInfoWithoutStats> {
        val username = _username ?: return RepoUsers.getUsersWithoutStats()
        val user = ArrayList<UserPublicInfoWithoutStats>()
        user.add(RepoUsers.getUserByName(username).getOrThrow().publicInfoWithoutStats())
        return user
    }
/*
    fun getHash(password: String): String {
        SHA512Hash().getSHA512()
        return sha512.getSHA512(password)
    }
  */
    fun hashPassword(password: String): String {
        return sha512.getSHA512(password)
    }
}