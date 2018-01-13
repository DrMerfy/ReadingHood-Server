package com.readinghood.entity;

import java.time.Instant;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "post", indexes = { @Index(columnList = "post_id"), @Index(columnList = "post_timestamp"), @Index(columnList = "post_text") })

public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "post_timestamp")
    private Instant timestamp;

    @Column(name = "post_text")
    private String text;

    @OneToMany(optional = false, targetEntity = Profile.class)
    private Profile author;

    @ManyToMany(optional = false)
    private Long votes;

    @ManyToOne(optional = false)
    private List<Post> edits;

    public Post() {

    }

    public void setId(Long Id) {
	this.id = id;
    }

    public Long getId() {
	return this.id;
    }

    public void setText(String text) {
	this.text = text;
    }

    public String printText() {
	return this.text;
    }

    public void setAuthor(Profile author) {
	this.author = author;
    }

    public void setVotes(Long votes) {
	this.votes = votes;
    }

    public void getVotes() {
	return this.votes;
    }

    public void edit(Post edit) {
	this.edits.add(edit);
    }

    public List<Post> getEdits() {
	return this.edits;
    }
}