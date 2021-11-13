package com.ikkikingg.pdmemes.model;

import lombok.Data;
import java.util.Set;

@Data
public class Page {
    private Set<String> chatIds;
    private String pageUrl;
    private Long pageNum;
    private Long lastPost;
}
