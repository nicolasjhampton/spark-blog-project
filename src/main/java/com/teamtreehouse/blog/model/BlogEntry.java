package com.teamtreehouse.blog.model;

import com.github.slugify.Slugify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BlogEntry {
    private String slug;
    private String title;
    private LocalDateTime createdAt;
    private String entry;
    private List<Comment> comments;

    public BlogEntry(String title, String entry) {
        this.title = title;
        this.entry = entry;
        this.createdAt = LocalDateTime.now();
        Slugify slugify = new Slugify();
        slug = slugify.slugify(title);
        this.comments = new ArrayList<Comment>();
    }

    public String getTitle() {
        return title;
    }

    public String getEntry() {
        return entry;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getSlug() {
        return slug;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public boolean addComment(Comment comment) {
        // Store these comments!
        return comments.add(comment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlogEntry blogEntry = (BlogEntry) o;

        if (title != null ? !title.equals(blogEntry.title) : blogEntry.title != null) return false;
        return createdAt != null ? createdAt.equals(blogEntry.createdAt) : blogEntry.createdAt == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BlogEntry{" +
                "title='" + title + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
