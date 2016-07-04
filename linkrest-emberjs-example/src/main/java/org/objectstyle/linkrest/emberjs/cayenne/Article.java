package org.objectstyle.linkrest.emberjs.cayenne;

import org.objectstyle.linkrest.emberjs.cayenne.auto._Article;

import java.util.Date;

public class Article extends _Article {

    private static final long serialVersionUID = 1L; 

    @Override
    protected void onPostAdd() {
        setPublishedOn(new Date());
    }

}
