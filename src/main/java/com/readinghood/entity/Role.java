package com.readinghood.entity;

public enum Role {
    ADMIN(Values.ADMIN), MODERATOR(Values.MODERATOR), USER(Values.USER);

    private Role(String value) {
	if (!this.name().equals(value))
	    throw new IllegalArgumentException("Incorrect use of PriceSource");
    }

    public static class Values {
	public static final String ADMIN = "ADMIN";
	public static final String MODERATOR = "MODERATOR";
	public static final String USER = "USER";
    }

}
