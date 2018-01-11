package com.readinghood.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Table(name = "thread",indexes = {@Index(columnList = "thread_id"),@Index(columnList = "views") })
public class Thread {

    @Id
    @Column (name="thread_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @Column (name="thread_views")
    @NotNull
    private int views;

    @ManyToMany (mappedBy = "threads",cascade = CascadeType.ALL)
    private List<Tag> tags;

    private List <Post> posts;

    public  Thread(){
        this.views=0;
    }


    public long getID(){
        return id;
    }

    public void setId(long id){
        this.id=id;
    }

    public int getViews(){
        return views;
    }

    public void setViews(int views){
        this.views=views;
    }

    public void increamentViews(){
        this.views++;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @ManyToMany
    @JoinTable(name="Thread_Post",
            joinColumns ={@JoinColumn(name="thread_id")},
            inverseJoinColumns = {@JoinColumn(name="tag_id")})
    public List<Tag> getTags(){
        return tags;
    }



    @OneToMany(targetEntity =Post.class,mappedBy = "thread",cascade =CascadeType.ALL)
    public List<Post> getPosts(){
        return posts;
    }

    public void setPosts(List<Post> posts){
        this.posts=posts;
    }
}




