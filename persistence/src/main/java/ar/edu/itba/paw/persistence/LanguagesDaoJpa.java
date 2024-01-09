package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.Language;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.edu.itba.paw.models.viewsContext.interfaces.AbstractPage;
import ar.itba.edu.paw.persistenceinterfaces.LanguageDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class LanguagesDaoJpa implements LanguageDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public AbstractPage<Language>getLanguages(final int page, final int itemsPerPage,final Boolean isUsed) {
        StringBuilder sb = new StringBuilder("SELECT l.id FROM languages l ");
        boolean first = true;
        if (isUsed != null) {
            sb.append("join book a on l.id = a.lang ");
            first = false;
        }
        final int offset = (page - 1) * itemsPerPage;
        String pagination = " LIMIT :limit OFFSET :offset ";
        final Query queryCount = em.createNativeQuery(sb.toString());
        sb.append(pagination);
        final Query queryNative  = em.createNativeQuery(sb.toString());

        queryNative.setParameter("limit", itemsPerPage);
        queryNative.setParameter("offset", offset);
        @SuppressWarnings("unchecked")
        final List<String> ids = (List<String>) queryCount.getResultList().stream().map(
                n -> (String) ((String) n)).collect(Collectors.toList());
        final int totalPages = (int) Math.ceil((double) ((Number) ids.size()).longValue() / itemsPerPage);

        @SuppressWarnings("unchecked")
        List<String> list = (List<String>) queryNative.getResultList().stream().map(
                n -> (String) ((String) n)).collect(Collectors.toList());

        // In case of empty result -> Return a Page with empty lists
        if (list.isEmpty())
            return new PagingImpl<>(new ArrayList<>(), 0, 0);

        // Get the AssetInstances that match those IDs for given page
        final TypedQuery<Language> query = em.createQuery("FROM Language as l  WHERE l.code IN (:ids) ORDER BY l.code" , Language.class);
        query.setParameter("ids", list);
        List<Language> assets = query.getResultList();

        return new PagingImpl<>(assets, page, totalPages);
    }

    @Override
    public Optional<Language> getLanguage(String code) {
        return Optional.ofNullable(em.find(Language.class, code));
    }

}
