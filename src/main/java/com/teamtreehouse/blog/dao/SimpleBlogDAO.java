package com.teamtreehouse.blog.dao;

import com.teamtreehouse.blog.model.BlogEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolasjhampton on 11/5/16.
 */
public class SimpleBlogDAO implements BlogDao {

    private List<BlogEntry> entries;

    public SimpleBlogDAO() {
        this.entries = new ArrayList<>();
    }

    @Override
    public boolean addEntry(BlogEntry blogEntry) {
        return entries.add(blogEntry);
    }

    @Override
    public List<BlogEntry> findAllEntries() {
        return new ArrayList<>(entries);
    }

    @Override
    public BlogEntry findEntryBySlug(String slug) {
        return entries.stream()
                      .filter(entry -> slug.equals(entry.getSlug()))
                      .findFirst()
                      .orElseThrow(NotFoundException::new);
    }
}
