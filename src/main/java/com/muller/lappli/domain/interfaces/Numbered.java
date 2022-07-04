package com.muller.lappli.domain.interfaces;

/**
 * For entities which have an article number in Muller Database
 */
@FunctionalInterface
public interface Numbered {
    public Long getArticleNumber();
}
