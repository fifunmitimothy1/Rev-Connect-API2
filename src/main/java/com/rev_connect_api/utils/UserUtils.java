package com.rev_connect_api.utils;

import com.rev_connect_api.models.User;

public class UserUtils {
  public static String getFullName(User user) {
    return user.getLastName() + ", " + user.getFirstName();
  }
}
