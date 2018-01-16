/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.readinghood.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "account", indexes = { @Index(columnList = "account_id"), @Index(columnList = "account_username"), @Index(columnList = "account_email") })
public class Account {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "account_email")
    @Email
    @NotNull
    private String email;

    @Column(name = "account_username")
    @NotNull
    private String username;

    @Column(name = "account_password")
    @NotNull
    private String password;

    @Column(name = "account_role")
    @Enumerated(EnumType.STRING)
    private Role accountRole;

    @OneToOne(targetEntity = Profile.class)
    private Profile profile;

    public Account(String email, String password) {
	this.email = email;
	this.password = password;
    }

    public Account() {
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }
    /*
     * public Profile getProfile() { return profile; } public void setProfile(Profile profile) { this.profile = profile;
     * }
     */

    public Role getAccountRole() {
	return accountRole;
    }

    public void setAccountRole(Role accountRole) {
	this.accountRole = accountRole;
    }
}