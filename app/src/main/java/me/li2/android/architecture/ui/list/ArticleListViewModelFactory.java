package me.li2.android.architecture.ui.list;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import me.li2.android.architecture.data.repository.DemoRepository;

/**
 * The constructor that is automatically called by ViewModelProvider is the default one -
 * it takes no arguments. If you want to create a different constructor for a view model,
 * you'll need to make a view model provider factory.
 *
 * Created by weiyi on 04/02/2018.
 * https://github.com/li2
 */

public class ArticleListViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final DemoRepository mRepository;

    public ArticleListViewModelFactory(DemoRepository repository) {
        mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new ArticleListFragmentViewModel(mRepository);
    }
}