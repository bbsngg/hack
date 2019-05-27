package com.shimh.service.impl;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.PixelBoundryBackground;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;
import com.shimh.vo.ArticleVo;
import com.shimh.vo.PageVo;
import org.apache.commons.io.IOUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shimh.common.util.UserUtils;
import com.shimh.entity.Article;
import com.shimh.entity.Category;
import com.shimh.entity.Tag;
import com.shimh.entity.User;
import com.shimh.repository.ArticleRepository;
import com.shimh.service.ArticleService;

import static org.apache.tomcat.util.file.ConfigFileLoader.getInputStream;

/**
 * @author CSE
 * <p>
 * 2019年1月25日
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;


    @Override
    public List<Article> listArticles(PageVo page) {

        return articleRepository.listArticles(page);
    }

    @Override
    public List<Article> listArticles(ArticleVo article, PageVo page) {

        return articleRepository.listArticles(article, page);
    }

    @Override
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Article getArticleById(Integer id) {
        return articleRepository.getOne(id);
    }

    @Override
    @Transactional
    public Integer publishArticle(Article article) {

        if(null != article.getId()){
            return this.updateArticle(article);
        }else{
            return this.saveArticle(article);
        }

    }

    @Override
    @Transactional
    public Integer saveArticle(Article article) {

        User currentUser = UserUtils.getCurrentUser();

        if (null != currentUser) {
            article.setAuthor(currentUser);
        }

        article.setCreateDate(new Date());
        article.setWeight(Article.Article_Common);

        return articleRepository.save(article).getId();
    }

    @Override
    @Transactional
    public Integer updateArticle(Article article) {
        Article oldArticle = articleRepository.getOne(article.getId());

        oldArticle.setTitle(article.getTitle());
        oldArticle.setSummary(article.getSummary());
        oldArticle.setBody(article.getBody());
        oldArticle.setCategory(article.getCategory());
        oldArticle.setTags(article.getTags());

        return oldArticle.getId();
    }

    @Override
    @Transactional
    public void deleteArticleById(Integer id) {
        articleRepository.delete(id);
    }

    @Override
    public List<Article> listArticlesByTag(Integer id) {
        Tag t = new Tag();
        t.setId(id);
        return articleRepository.findByTags(t);
    }

    @Override
    public List<Article> listArticlesByCategory(Integer id) {
        Category c = new Category();
        c.setId(id);

        return articleRepository.findByCategory(c);
    }

    @Override
    @Transactional
    public Article getArticleAndAddViews(Integer id) {
        int count = 1;
        Article article = articleRepository.getOne(id);
        article.setViewCounts(article.getViewCounts() + count);
        return article;
    }

    @Override
    public List<Article> listHotArticles(int limit) {

        return articleRepository.findOrderByViewsAndLimit(limit);
    }

    @Override
    public List<Article> listNewArticles(int limit) {

        return articleRepository.findOrderByCreateDateAndLimit(limit);
    }

    @Override
    public List<ArticleVo> listArchives() {

        return articleRepository.listArchives();
    }

    @Override
    public List<String> getLinksById(Integer id) {
        Article article = articleRepository.getOne(id);
        String content = article.getBody().getContent();

        List<String> phraseList = HanLP.extractPhrase(content, 6);
        List<String> linkList=new ArrayList<String>();
        List<String> retLinks=new ArrayList<String>();
//        System.out.println(phraseList);
        for(int i=0;i<phraseList.size();i++){
            String link="https://www.baidu.com/s?word="+phraseList.get(i);
            linkList.add(link);
            System.out.println(link);
        }
//        for(String url:linkList){
//            getNews(url,3);
//        }
        for (int i=0;i <linkList.size();i ++){
            String temp = linkList.get(i);
            retLinks.add(temp+"@-@"+getNews(temp));
        }
        return retLinks;
    }

    private static String getNews(String url){
        try{
            Document doc= Jsoup.connect(url).get();
            Element element=doc.getElementById("content_left");

            Element result=element.getElementById(String.valueOf(1));
            Elements add=result.select("a");
            System.out.println(add.first().text());
            String attr = add.first().attr("href");
            System.out.println(attr);
//                System.out.println(getRealUrlFromBaiduUrl(attr));
            return getRealUrlFromBaiduUrl(attr);

        }catch(IOException e){
            e.printStackTrace();
            return "";
        }
    }

    private static String getRealUrlFromBaiduUrl(String url) {
        Connection.Response res = null;
        int itimeout = 60000;
        try {
            res = Jsoup.connect(url).timeout(itimeout).method(Connection.Method.GET).followRedirects(false).execute();
            return res.header("Location");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

//    @Override
//    public void getWordPic(String content) throws IOException { //whaleImgSmallTest
//        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
//        frequencyAnalyzer.setWordFrequenciesToReturn(300);
//        frequencyAnalyzer.setMinWordLength(5);
//        frequencyAnalyzer.setStopWords(loadStopWords());
//
//        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(new ByteArrayInputStream(content.getBytes()));
//        final Dimension dimension = new Dimension(500, 312);
//        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
//        wordCloud.setPadding(1);
//        wordCloud.setBackground(new PixelBoundryBackground(getInputStream("backgrounds/whale_small.png")));
//        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
//        wordCloud.setFontScalar(new LinearFontScalar(10, 40));
//        wordCloud.build(wordFrequencies);
//        wordCloud.writeToFile("output/wordcloud"++".png");
//    }
//    private static Set<String> loadStopWords() {
//        try {
//            final List<String> lines = IOUtils.readLines(getInputStream("text/stop_words.txt"));
//            return new HashSet<>(lines);
//
//        } catch (final IOException e) {
//            LOGGER.error(e.getMessage(), e);
//        }
//        return Collections.emptySet();
//    }
}
