package com.readinghood.entity;

public enum Role {
    ADMIN(Values.ADMIN), MODERATOR(Values.MODERATOR), USER(Values.USER);

    private Role(String value) {
	if (!this.name().equals(value))
	    throw new IllegalArgumentException("Incorrect use of PriceSource");
    }

    public static Role newRole(String role) throws IllegalArgumentException{
        switch (role){
            case Values.ADMIN:
                return Role.ADMIN;
            case Values.MODERATOR:
                return Role.MODERATOR;
            case Values.USER:
                return Role.USER;
            default:
                return null;
        }
    }
    
    public static class Values {
	public static final String ADMIN = "ADMIN";
	public static final String MODERATOR = "MODERATOR";
	public static final String USER = "USER";
    }

}
