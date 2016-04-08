
package com.yliec.ewu.api.entity;

import com.yliec.ewu.api.entity.element.User;

import java.util.List;

public class UserEntity {

    public class Users extends BaseEntity<List<User>> {}

    public class AUser extends BaseEntity<User>{}
}
