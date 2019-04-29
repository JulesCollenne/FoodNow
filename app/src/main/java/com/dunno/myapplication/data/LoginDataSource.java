package com.dunno.myapplication.data;

import android.content.Context;

import com.dunno.myapplication.data.model.LoggedInUser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {

            LoggedInUser fakeUser = new LoggedInUser("0000", "TestUser");
            return new Result.Success<>(fakeUser);

        } catch (Exception e) {
            return new Result.Error(new IOException("Erreur lors de la connection", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }





}
