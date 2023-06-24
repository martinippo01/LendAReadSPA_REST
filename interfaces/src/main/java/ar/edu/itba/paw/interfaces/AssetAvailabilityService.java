package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.*;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;

import java.time.LocalDate;
import java.util.List;

public interface AssetAvailabilityService {

    void borrowAsset(final int assetId, final String borrower, final LocalDate borrowDate,final LocalDate devolutionDate)  throws AssetInstanceBorrowException, UserNotFoundException, DayOutOfRangeException;

    void setAssetPrivate(final int assetId) throws AssetInstanceNotFoundException;

    void setAssetPublic(final int assetId) throws AssetInstanceNotFoundException;

    void returnAsset(final int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException;

    void confirmAsset(final int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException;
    void rejectAsset(final int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException;
     List<LendingImpl> getActiveLendings(final AssetInstanceImpl ai);
}
