package com.ikkikingg.pdmemes.service;

import com.ikkikingg.pdmemes.model.Page;
import com.ikkikingg.pdmemes.model.Post;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Service
public class ParserService {
    private final Page page;
    
    @Autowired
    public ParserService(Page page) {
        this.page = page;
    }

    public Set<Post> parsePage() throws IOException {
        Set<Post> postSet = new TreeSet<>();
        Document doc = Jsoup.connect(page.getPageUrl() + page.getPageNum()).get();
        Elements posts = doc.select(".ipsColumn_fluid");

        for (int i = 0; i < posts.size(); i++) {
            Long postPage = Long.valueOf(posts.get(i).select(".ipsComment_tools").select("a[href]").not(".ipsType_blendLinks").last().text().substring(1));

            if (postPage > page.getLastPost()) {
                Set<String> links = new HashSet<>();
                Elements content = posts.get(i).select(".ipsContained");
                for (Element cont : content) {
                    for (Element element : cont.getAllElements()) {
                        if (checkForQuote(element)) {
                            continue;
                        }
                        for (Attribute attribute : element.attributes()) {
                            if (attribute.getKey().equals("data-src") ||
                                    attribute.getKey().equals("data-video-src")) {
                                links.add(attribute.getValue());
                            }
                        }
                    }
                }
                page.setLastPost(postPage);
                postSet.add(new Post(postPage, links));
            }
            if (i == 19) {
                page.setPageNum(page.getPageNum() + 1);
            }
        }
        return postSet;
    }

    private boolean checkForQuote(Element element) {
        return element.parent().parent().attr("class").equals("ipsQuote_contents") ||
                element.parent().attr("class").equals("ipsQuote_contents") ||
                element.attr("class").equals("ipsQuote_contents");
    }
}
