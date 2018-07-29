package me.li2.android.architecture.ui.offerdetail.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import me.li2.android.architecture.data.repository.OffersRepository;
import me.li2.android.architecture.ui.offers.view.OffersNavigator;
import me.li2.android.architecture.utils.ResourceProvider;

/**
 * The constructor that is automatically called by ViewModelProvider is the default one -
 * it takes no arguments. If you want to create a different constructor for a view model,
 * you'll need to make a view model provider factory.
 *
 * Created by weiyi on 04/02/2018.
 * https://github.com/li2
 */

public class OfferDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    @Inject
    OffersRepository mRepository;

    @NonNull
    @Inject
    OffersNavigator mNavigator;

    @NonNull
    @Inject
    ResourceProvider mResourceProvider;

    @Inject
    public OfferDetailViewModelFactory() {
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new OfferDetailViewModel(mRepository, mResourceProvider, mNavigator);
    }
}
