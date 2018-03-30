package me.li2.android.wipro_assessment.utils;

import android.content.Context;

import me.li2.android.wipro_assessment.data.source.local.WiproDatabase;
import me.li2.android.wipro_assessment.data.source.remote.WebServiceGenerator;
import me.li2.android.wipro_assessment.data.source.remote.WiproWebService;
import me.li2.android.wipro_assessment.data.repository.WiproRepository;
import me.li2.android.wipro_assessment.ui.list.ArticleListViewModelFactory;

/**
 * Created by weiyi on 16/02/2018.
 * https://github.com/li2
 */

public class InjectorUtils {

    public static WiproRepository provideRepository(Context context) {
        WiproDatabase database = WiproDatabase.getInstance(context);
        AppExecutors executors = AppExecutors.getInstance();
        WiproWebService wiproWebService = WebServiceGenerator.createService(WiproWebService.class);
        return WiproRepository.getInstance(context, database.articleDao(), wiproWebService, executors);
    }

    public static ArticleListViewModelFactory provideArticleListViewModelFactory(Context context) {
        WiproRepository repository = provideRepository(context.getApplicationContext());
        return new ArticleListViewModelFactory(repository);
    }
}
