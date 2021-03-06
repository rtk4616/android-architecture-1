package me.li2.android.architecture.ui.articles.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import arch.NoNetworkException;
import arch.Resource;
import arch.Status;
import io.reactivex.Completable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.li2.android.architecture.R;
import me.li2.android.architecture.data.model.Article;
import me.li2.android.architecture.data.repository.ArticlesRepository;
import me.li2.android.architecture.ui.articles.view.ArticlesNavigator;
import me.li2.android.architecture.utils.BaseResourceProvider;

/**
 * ViewModel to expose states for the list of articles view, and handle all user actions.
 *
 * - Ask data from repository {@link ArticlesViewModel#mRepository}, and then
 *     construct the source data to view data which contains all UI state.
 *
 * - Expose streams for view to subscribe the UI state,
 *     such as {@link ArticlesViewModel#getUiModel()}, {@link ArticlesViewModel#getLoadingIndicatorVisibility()}
 *
 * - Expose public methods for view to handle user actions,
 *     such as force pull-refresh {@link ArticlesViewModel#forceUpdateArticles()}
 *
 * - For user action in the sub-view, such as click or check in the list view item,
 *      implement {@link Action} or {@link Consumer} in the ViewModel
 *      {@link ArticlesViewModel#handleArticleTaped(Article, View)}, and then
 *      pass it as parameter to ViewHolder constructor {@link ArticleItem#ArticleItem(Article, Consumer)}.
 *
 * @author Weiyi Li on 13/7/18 | https://github.com/li2
 */
public class ArticlesViewModel extends ViewModel {

    private static final String TAG = ArticlesViewModel.class.getSimpleName();

    @NonNull
    private ArticlesRepository mRepository;

    @NonNull
    private BaseResourceProvider mResourceProvider;

    @NonNull
    private ArticlesNavigator mNavigator;

    private MutableLiveData<Boolean> mLoadingIndicator = new MutableLiveData<>();

    private MutableLiveData<String> mSnackbarText = new MutableLiveData<>();

    public ArticlesViewModel(@NonNull ArticlesRepository repository,
                             @NonNull BaseResourceProvider resourceProvider,
                             @NonNull ArticlesNavigator navigator
                             ) {
        mRepository = repository;
        mResourceProvider = resourceProvider;
        mNavigator = navigator;
    }

    /**
     * @return the model for the articles list screen.
     */
    @NonNull
    public LiveData<ArticlesUiModel> getUiModel() {
        return Transformations.map(getArticleItems(),
                resource -> {
                    mLoadingIndicator.setValue(resource.status == Status.LOADING);

                    if (resource.status == Status.ERROR) {
                        if (resource.throwable instanceof NoNetworkException) {
                            mSnackbarText.setValue(mResourceProvider.getString(R.string.status_no_connect));
                        } else {
                            mSnackbarText.setValue(resource.errorMessage);
                        }
                    }

                    if (resource.data != null) {
                        return constructArticlesUiModel(resource.data);
                    } else {
                        return null;
                    }
                });
    }

    /**
     * @return a stream of string ids that should be displayed in the snackbar.
     */
    @NonNull
    public LiveData<String> getSnackbarMessage() {
        return mSnackbarText;
    }

    /**
     * @return a stream that emits true if the progress indicator should be displayed, false otherwise.
     */
    @NonNull
    public LiveData<Boolean> getLoadingIndicatorVisibility() {
        return mLoadingIndicator;
    }

    /**
     * Convert {@link Resource<List<>>} of {@link Article} (data model) to {@link ArticleItem} (view data model)
     * @return
     */
    private LiveData<Resource<List<ArticleItem>>> getArticleItems() {
        return Transformations.map(mRepository.loadArticles(),
                resource -> {
                    List<ArticleItem> articleItems = null;
                    if (resource.data != null) {
                        articleItems = new ArrayList<>();
                        for (Article article : resource.data) {
                            articleItems.add(constructArticleItem(article));
                        }
                    }
                    return new Resource<>(resource.status, articleItems, resource.errorMessage, resource.code, resource.throwable);
                });
    }

    private ArticlesUiModel constructArticlesUiModel(List<ArticleItem> articleItems) {
        boolean isArticlesListVisible = !articleItems.isEmpty();
        boolean isNoArticlesViewVisible = !isArticlesListVisible;
        return new ArticlesUiModel(isArticlesListVisible, articleItems,
                isNoArticlesViewVisible, mResourceProvider.getString(R.string.no_articles_all));
    }

    private ArticleItem constructArticleItem(final Article article) {
        return new ArticleItem(article, view -> handleArticleTaped(article, view));
    }

    private void handleArticleTaped(Article article, View sharedElement) {
        mNavigator.openArticleDetails(article.getId(), sharedElement);
    }

    /**
     * Trigger a force update of the articles.
     */
    public Completable forceUpdateArticles() {
        mLoadingIndicator.setValue(true);
        // TODO
        return null;
    }
}
