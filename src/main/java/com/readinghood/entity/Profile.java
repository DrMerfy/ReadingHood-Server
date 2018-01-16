package com.readinghood.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "profile", indexes = { @Index(columnList = "profile_id"), @Index(columnList = "profile_handle") })
public class Profile {

    @Id
    @Column(name = "profile_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "profile_handle")
    @NotNull
    private String handle;

    @Column(name = "profile_firstName")
    private String name;

    @Column(name = "profile_lastName")
    private String surname;

    @Column(name = "profile_year")
    private Date dateOfBirth;

    @Column(name = "profile_department")
    private String department; // TODO: Make this an enum and offer a drop down list

    @OneToOne(optional = false)
    private Account account;

    @OneToMany (mappedBy = "post")
    private List<Post> createdPosts;

    @ManyToMany
    @JoinTable(name = "favorites", joinColumns = { @JoinColumn(referencedColumnName = "profile_id") }, inverseJoinColumns = { @JoinColumn(referencedColumnName = "post_id") })
    private List<Post> favoritePosts;

    @ManyToMany
    @JoinTable(name = "upvotes", joinColumns = { @JoinColumn(referencedColumnName = "profile_id") }, inverseJoinColumns = { @JoinColumn(referencedColumnName = "post_id") })
    private List<Post> upvotes;

    @ManyToMany
    @JoinTable(name = "downvotes", joinColumns = { @JoinColumn(referencedColumnName = "profile_id") }, inverseJoinColumns = { @JoinColumn(referencedColumnName = "post_id") })
    private List<Post> downvotes;

    @ManyToMany
    @JoinTable(name = "savedtags", joinColumns = { @JoinColumn(referencedColumnName = "profile_id") }, inverseJoinColumns = { @JoinColumn(referencedColumnName = "tag_id") })
    private List<Tag> savedTags;

    public Profile() {

    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getHandle() {
	return handle;
    }

    public void setHandle(String handle) {
	this.handle = handle;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getSurname() {
	return surname;
    }

    public void setSurname(String surname) {
	this.surname = surname;
    }

    public Date getDateOfBirth() {
	return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
    }

    public String getDepartment() {
	return department;
    }

    public void setDepartment(String department) {
	this.department = department;
    }

    public Account getAccount() {
	return account;
    }

    public void setAccount(Account account) {
	this.account = account;
    }

    public List<Post> getCreatedPosts() {
	return createdPosts;
    }

    public void setCreatedPosts(List<Post> createdPosts) {
	this.createdPosts = createdPosts;
    }

    public List<Post> getFavoritePosts() {
	return favoritePosts;
    }

    public void setFavoritePosts(List<Post> favoritePosts) {
	this.favoritePosts = favoritePosts;
    }

    public List<Post> getUpvotes() {
	return upvotes;
    }

    public void setUpvotes(List<Post> upvotes) {
	this.upvotes = upvotes;
    }

    public List<Post> getDownvotes() {
	return downvotes;
    }

    public void setDownvotes(List<Post> downvotes) {
	this.downvotes = downvotes;
    }

    public List<Tag> getSavedTags() {
	return savedTags;
    }

    public void setSavedTags(List<Tag> savedTags) {
	this.savedTags = savedTags;
    }

}
