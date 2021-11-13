package com.ikkikingg.pdmemes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Set;

@Data
@AllArgsConstructor
public class Post implements Comparable<Post>{
    private Long numPost;
    private Set<String> links;

    @Override
    public int compareTo(Post o) {
        return numPost.compareTo(o.getNumPost());
    }
}
