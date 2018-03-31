package me.li2.android.architecture.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import architecture_components.utils.Resource;
import me.li2.android.architecture.data.model.Article;
import me.li2.android.architecture.data.repository.DemoRepository;

/**
 * Created by weiyi on 04/02/2018.
 * https://github.com/li2
 */

public class ArticleDetailViewModel extends ViewModel {

    private DemoRepository mRepository;

    public ArticleDetailViewModel(DemoRepository repository) {
        mRepository = repository;
    }

    public LiveData<Article> getArticle(int id) {
        return mRepository.loadArticle(id);
    }
}
