package com.eureka.authenticationservice.api.user.model
enum class Role{ 
    ADMIN, USER;

    companion object {
        fun transform(rolesString: String): ArrayList<Role> {
            val roles = arrayListOf<Role>()
            for (role in rolesString.split(",")) {
                roles.add(valueOf(role.uppercase().trim()))
            }
            return roles
        }
    }
}