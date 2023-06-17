package ar.edu.itba.paw.models.assetExistanceContext.implementations;

import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.miscellaneous.ImageImpl;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;

import javax.persistence.*;

@Entity
@Table(name = "AssetInstance")
public class AssetInstanceImpl{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assetinstance_id_seq")
    @SequenceGenerator(sequenceName = "assetinstance_id_seq", name = "assetinstance_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assetId", referencedColumnName = "uid", nullable = false)
    private  BookImpl book;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner", referencedColumnName = "id", nullable = false)
    private  UserImpl userReference;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "locationId", referencedColumnName = "id")
    private  LocationImpl location;

    @Column(name = "description", length = 300)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "physicalCondition", length = 100)
    private  PhysicalCondition physicalCondition;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 100)
    private  AssetState assetState;

    @Column(name = "maxLendingDays", nullable = false)
    private  int maxLendingDays;

    @Column(name = "isReservable", nullable = false)
    private  boolean isReservable;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photoId", referencedColumnName = "id", nullable = false)
    private ImageImpl image;

    public AssetInstanceImpl(final int id,final BookImpl book,final PhysicalCondition physicalCondition,final UserImpl userReference,final LocationImpl location,final ImageImpl imageId,final AssetState as,final int maxDaysLending,final String description) {
        this.id = (long) id;
        this.book = book;
        this.physicalCondition = physicalCondition;
        this.userReference = userReference;
        this.location = location;
        this.image = imageId;
        this.assetState = as;
        this.maxLendingDays = maxDaysLending;
        this.description = description;
    }
    public AssetInstanceImpl(BookImpl book, PhysicalCondition physicalCondition, UserImpl userReference, LocationImpl location, ImageImpl imageId, AssetState as, int maxDaysLending,String description, boolean isReservable) {
        this.book = book;
        this.physicalCondition = physicalCondition;
        this.userReference = userReference;
        this.location = location;
        this.image = imageId;
        this.assetState = as;
        this.maxLendingDays = maxDaysLending;
        this.description = description;
        this.isReservable = isReservable;
    }


    public AssetInstanceImpl() {

    }
    @Override
    public String toString() {
        return "AssetInstanceImpl{" +
                "book=" + book +
                ", physicalCondition=" + physicalCondition +
                ", userReference=" + userReference +
                ", location=" + location +
                '}';
    }

    public boolean getIsReservable() {
        return isReservable;
    }

    public void setReservable(boolean reservable) {
        isReservable = reservable;
    }

    public BookImpl getBook() {
        return book;
    }

    public UserImpl getOwner() {
        return userReference;
    }

    public LocationImpl getLocation() {
        return location;
    }

    public AssetState getAssetState() {
        return assetState;
    }

    public int getMaxDays() {
        return this.maxLendingDays;
    }

    public ImageImpl getImage() {
        return image;
    }

    public boolean getIsBorrowedInstance() {
        return false;
    }

    public int getId() {
        return Math.toIntExact(id);
    }

    public PhysicalCondition getPhysicalCondition() {
        return physicalCondition;
    }

    public void setBook(BookImpl book) {
        this.book = book;
    }

    public void setUserReference(UserImpl userReference) {
        this.userReference = userReference;
    }

    public void setLocation(LocationImpl location) {
        this.location = location;
    }

    public void setPhysicalCondition(PhysicalCondition physicalCondition) {
        this.physicalCondition = physicalCondition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAssetState(AssetState assetState) {
        this.assetState = assetState;
    }

    public void setMaxLendingDays(int maxLendingDays) {
        this.maxLendingDays = maxLendingDays;
    }

    public void setImage(ImageImpl image) {
        this.image = image;
    }
}
