package me.li2.android.architecture.ui.offerdetail.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import me.li2.android.architecture.data.model.Offer;
import me.li2.android.architecture.data.repository.OffersRepository;

/**
 * Created by weiyi on 04/02/2018.
 * https://github.com/li2
 */

public class OfferDetailViewModel extends ViewModel {

    private OffersRepository mRepository;

    public OfferDetailViewModel(OffersRepository repository) {
        mRepository = repository;
    }

    public LiveData<Offer> getOffer(String id) {
        return mRepository.loadOffer(id);
    }
}
