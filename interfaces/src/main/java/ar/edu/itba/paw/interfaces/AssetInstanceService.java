package ar.edu.itba.paw.interfaces;
//import models.assetExistanceContext.interfaces.AssetInstance;
//import models.assetExistanceContext.interfaces.Book;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.exceptions.LocationNotFoundException;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.viewsContext.interfaces.AbstractPage;
import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;

import java.util.Optional;

public interface AssetInstanceService {
    AssetInstance getAssetInstance(final int id) throws AssetInstanceNotFoundException;


    AbstractPage<AssetInstance> getAllAssetsInstances(final int pageNum, final int itemPerPage, final SearchQuery searchQuery);

    void removeAssetInstance(final int id) throws AssetInstanceNotFoundException;

    boolean isOwner(final int id, final String email) throws AssetInstanceNotFoundException;

    void changeAssetInstance(final int id, final Optional<PhysicalCondition> physicalCondition, final Optional<Integer> maxLendingDays, final Optional<Integer> location, final byte[] image, final Optional<String> description,final Optional<Boolean> isReservable,final Optional<String> state) throws AssetInstanceNotFoundException, LocationNotFoundException, ImageNotFoundException;

}
