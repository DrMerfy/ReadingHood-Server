package com.readinghood.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "profile", indexes = { @Index(columnList = "profile_id") })
public class Profile {

    @Id
    @Column(name = "profile_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "profile_firstName")
    private String name; 
    
    @Column(name = "profile_lastName")
    private String surname;

    /*
    @Column(name = "profile_year")
    private Date dateOfBirth;
    */

    @Column(name = "profile_department")
    private String department; // TODO: Make this an enum and offer a drop down list

    @OneToOne(mappedBy="profile")
    @JsonBackReference
    private Account account;

    @OneToMany(mappedBy="author")
    @JsonBackReference
    private List<Post> createdPosts;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "favorites", joinColumns = { @JoinColumn(referencedColumnName = "profile_id") }, inverseJoinColumns = { @JoinColumn(referencedColumnName = "thread_id") })
    @JsonBackReference
    private List<Thread> favoriteThreads;

    @ManyToMany(mappedBy="upvoters")
    @JsonBackReference
    private List<Post> upvotes;

    @ManyToMany(mappedBy="downvoters")    
    @JsonBackReference
    private List<Post> downvotes;

    /*
    @ManyToMany
    @JoinTable(name = "savedtags", joinColumns = { @JoinColumn(referencedColumnName = "profile_id") }, inverseJoinColumns = { @JoinColumn(referencedColumnName = "tag_id") })
    private List<Tag> savedTags;
    */



    public Profile() {        
        this.upvotes = new ArrayList<>();
        this.downvotes = new ArrayList<>();
        this.createdPosts = new ArrayList<>();
        this.favoriteThreads = new ArrayList<>(); 
        //this.savedTags = new ArrayList<>();
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
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

    /*
    public Date getDateOfBirth() {
	return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
    } 
    */


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
    
    public void addCreatedPost(Post post) {
	this.createdPosts.add(post);
    }


    public List<Thread> getFavoriteThreads() {
	return favoriteThreads;
    }

    public void setFavoriteThreads(List<Thread> favoriteThreads) {
	this.favoriteThreads = favoriteThreads;
    }
    
    public void addFavoriteThread(Thread thread) {
	this.favoriteThreads.add(thread);
    }

    public List<Post> getUpvotes() {
	return upvotes;
    }

    public void setUpvotes(List<Post> upvotes) {
	this.upvotes = upvotes;
    }

    public void addUpvote(Post post) {
	this.upvotes.add(post);
    }

    public List<Post> getDownvotes() {
	return downvotes;
    }

    public void setDownvotes(List<Post> downvotes) {
	this.downvotes = downvotes;
    }

    public void addDownvote(Post post) {
	this.downvotes.add(post);
    }

    /*
    public List<Tag> getSavedTags() {
	return savedTags;
    }

    public void setSavedTags(List<Tag> savedTags) {
	this.savedTags = savedTags;
    }
    
    public void addSavedTag(Tag tag){
        this.savedTags.add(tag);
    }
    */
    
    public boolean hasUpvoted(Post post) {
        return this.upvotes.contains(post);
    }
    
    public boolean hasDownvoted(Post post) {
        return this.downvotes.contains(post);
    }

}
