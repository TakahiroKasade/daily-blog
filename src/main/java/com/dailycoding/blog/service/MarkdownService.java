package com.dailycoding.blog.service;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MarkdownService {

    private final Parser parser;
    private final HtmlRenderer renderer;
    private final org.commonmark.renderer.text.TextContentRenderer textRenderer;

    public MarkdownService() {
        // 設定擴充功能 (例如：支援 GitHub Flavored Markdown 表格)
        List<Extension> extensions = Arrays.asList(TablesExtension.create());
        
        this.parser = Parser.builder()
                .extensions(extensions)
                .build();
        
        this.renderer = HtmlRenderer.builder()
                .extensions(extensions)
                .build();
                
        this.textRenderer = org.commonmark.renderer.text.TextContentRenderer.builder()
                .extensions(extensions)
                .build();
    }

    /**
     * 將 Markdown 文字轉換為 HTML
     * @param markdown Markdown 格式的字串
     * @return HTML 格式的字串
     */
    public String renderToHtml(String markdown) {
        if (markdown == null || markdown.isEmpty()) {
            return "";
        }
        return renderer.render(parser.parse(markdown));
    }

    /**
     * 取得文章純文字摘要 (去除 Markdown 符號)
     * @param markdown Markdown 內容
     * @param maxLength 最大長度
     * @return 純文字摘要
     */
    public String getExcerpt(String markdown, int maxLength) {
        if (markdown == null || markdown.isEmpty()) {
            return "";
        }
        // 1. 轉為純文字 (去除 Markdown 符號)
        String plainText = textRenderer.render(parser.parse(markdown));
        
        // 2. 去除多餘空白與換行
        plainText = plainText.replaceAll("\\s+", " ").trim();
        
        // 3. 截斷
        if (plainText.length() > maxLength) {
            return plainText.substring(0, maxLength) + "...";
        }
        return plainText;
    }
}
