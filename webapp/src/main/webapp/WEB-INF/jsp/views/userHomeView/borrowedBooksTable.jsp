<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <div class="table-title">
        <div style="display: flex; align-items: center; justify-content: space-between;">
            <h2><spring:message code="borrowed_books"/></h2>
            <div>
                <jsp:include page="filterButton.jsp">
                    <jsp:param name="table" value="borrowed_books"/>
                    <jsp:param name="filterValue" value="none"/>
                    <jsp:param name="filterAtribuite" value="none"/>
                    <jsp:param name="attribute" value="${attribute}"/>
                    <jsp:param name="sortAttribute" value="${sortAttribute}"/>
                    <jsp:param name="title" value="filterOption.all"/>
                    <jsp:param name="buttonText" value="userHomeView.all"/>
                    <jsp:param name="filter" value="${filter}"/>
                    <jsp:param name="currentPage" value="${currentPage}"/>
                </jsp:include>

                <jsp:include page="filterButton.jsp">
                    <jsp:param name="table" value="borrowed_books"/>
                    <jsp:param name="filterValue" value="pending"/>
                    <jsp:param name="filterAtribuite" value="status"/>
                    <jsp:param name="attribute" value="${attribute}"/>
                    <jsp:param name="sortAttribute" value="${sortAttribute}"/>
                    <jsp:param name="title" value="filterOption.pending"/>
                    <jsp:param name="buttonText" value="userHomeView.pending"/>
                    <jsp:param name="filter" value="${filter}"/>
                    <jsp:param name="currentPage" value="${currentPage}"/>
                </jsp:include>
                <jsp:include page="filterButton.jsp">
                    <jsp:param name="table" value="borrowed_books"/>
                    <jsp:param name="filterValue" value="confirmed"/>
                    <jsp:param name="filterAtribuite" value="status"/>
                    <jsp:param name="attribute" value="${attribute}"/>
                    <jsp:param name="sortAttribute" value="${sortAttribute}"/>
                    <jsp:param name="title" value="filterOption.confirmed"/>
                    <jsp:param name="buttonText" value="userHomeView.inProgress"/>
                    <jsp:param name="filter" value="${filter}"/>
                    <jsp:param name="currentPage" value="${currentPage}"/>
                </jsp:include>

                <jsp:include page="filterButton.jsp">
                    <jsp:param name="table" value="borrowed_books"/>
                    <jsp:param name="filterValue" value="delayed"/>
                    <jsp:param name="filterAtribuite" value="delayed"/>
                    <jsp:param name="attribute" value="${attribute}"/>
                    <jsp:param name="sortAttribute" value="${sortAttribute}"/>
                    <jsp:param name="title" value="filterOption.delayed"/>
                    <jsp:param name="buttonText" value="userHomeView.delayed"/>
                    <jsp:param name="filter" value="${filter}"/>
                    <jsp:param name="currentPage" value="${currentPage}"/>
                </jsp:include>

                <jsp:include page="filterButton.jsp">
                    <jsp:param name="table" value="borrowed_books"/>
                    <jsp:param name="filterValue" value="rejected"/>
                    <jsp:param name="filterAtribuite" value="lendingStatus"/>
                    <jsp:param name="attribute" value="${attribute}"/>
                    <jsp:param name="sortAttribute" value="${sortAttribute}"/>
                    <jsp:param name="title" value="filterOption.rejected"/>
                    <jsp:param name="buttonText" value="userHomeView.rejected"/>
                    <jsp:param name="filter" value="${filter}"/>
                    <jsp:param name="currentPage" value="${currentPage}"/>
                </jsp:include>

                <jsp:include page="filterButton.jsp">
                    <jsp:param name="table" value="borrowed_books"/>
                    <jsp:param name="filterValue" value="finished"/>
                    <jsp:param name="filterAtribuite" value="lendingStatus"/>
                    <jsp:param name="attribute" value="${attribute}"/>
                    <jsp:param name="sortAttribute" value="${sortAttribute}"/>
                    <jsp:param name="title" value="filterOption.finished"/>
                    <jsp:param name="buttonText" value="userHomeView.finished"/>
                    <jsp:param name="filter" value="${filter}"/>
                    <jsp:param name="currentPage" value="${currentPage}"/>
                </jsp:include>
            </div>
        </div>
        <div class="table-container">
            <table class="table">
                <thead>
                <tr>
                    <th style="opacity: 0.7"><spring:message code="image"/></th>
                    <jsp:include page="sortButton.jsp">
                        <jsp:param name="table" value="borrowed_books"/>
                        <jsp:param name="attribute" value="book_name"/>
                        <jsp:param name="sortAttribute" value="${sort_book_name}"/>
                        <jsp:param name="filterValue" value="${filterValue}"/>
                        <jsp:param name="filterAtribuite" value="${filterAtribuite}"/>
                        <jsp:param name="currentPage" value="${currentPage}"/>
                        <jsp:param name="title" value="book_name"/>
                    </jsp:include>

                    <jsp:include page="sortButton.jsp">
                        <jsp:param name="table" value="borrowed_books"/>
                        <jsp:param name="attribute" value="expected_retrieval_date"/>
                        <jsp:param name="sortAttribute" value="${sort_expected_retrieval_date}"/>
                        <jsp:param name="filterValue" value="${filterValue}"/>
                        <jsp:param name="filterAtribuite" value="${filterAtribuite}"/>
                        <jsp:param name="currentPage" value="${currentPage}"/>
                        <jsp:param name="title" value="expected_retrieval_date"/>
                    </jsp:include>

                    <jsp:include page="sortButton.jsp">
                        <jsp:param name="table" value="borrowed_books"/>
                        <jsp:param name="attribute" value="borrower_name"/>
                        <jsp:param name="sortAttribute" value="${sort_borrower_name}"/>
                        <jsp:param name="filterValue" value="${filterValue}"/>
                        <jsp:param name="filterAtribuite" value="${filterAtribuite}"/>
                        <jsp:param name="currentPage" value="${currentPage}"/>
                        <jsp:param name="title" value="borrower_name"/>
                    </jsp:include>

                    <jsp:include page="sortButton.jsp">
                        <jsp:param name="table" value="status"/>
                        <jsp:param name="attribute" value="borrower_name"/>
                        <jsp:param name="sortAttribute" value="${sort_borrower_name}"/>
                        <jsp:param name="filterValue" value="${filterValue}"/>
                        <jsp:param name="filterAtribuite" value="${filterAtribuite}"/>
                        <jsp:param name="currentPage" value="${currentPage}"/>
                        <jsp:param name="title" value="borrower_name"/>
                    </jsp:include>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${userAssets}" var="asset">
                    <spring:message var="lendedBooksName" code='borrowed_books' />
                    <c:url var="userUrl" value="/borrowedBookDetails/${asset.lendingId}"/>
                    <tr class="table-row-clickable" data-href="${userUrl}">
                        <td>
                            <div style="<c:if test='${asset.isBorrowedInstance and (asset.lendingState.isRejected or asset.lendingState.isFinished)}'>
                                    filter: grayscale(100%);
                                    </c:if>">
                                <img style="height: 125px; width: 75px; object-fit: cover" src="<c:url value='/getImage/${asset.imageId}'/>" alt="<c:out value='${asset.book.name}'/>"/>
                            </div>                                  </td>
                        <td><c:out value="${asset.book.name}"/></td>
                        <td><c:out value="${asset.dueDate}"/></td>
                        <td><c:out value="${asset.owner.name}"/></td>
                        <td><c:out value="${asset.assetState}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
        <jsp:include page="paginationButtons.jsp" >
            <jsp:param name="table" value="${table}"/>
            <jsp:param name="direction" value="${direction}"/>
            <jsp:param name="attribute" value="${attribute}"/>
            <jsp:param name="filterValue" value="${filterValue}"/>
            <jsp:param name="filterAtribuite" value="${filterAtribuite}"/>
            <jsp:param name="currentPage" value="${currentPage}"/>
            <jsp:param name="previousPage" value="${previousPage}"/>
            <jsp:param name="totalPages" value="${totalPages}"/>
            <jsp:param name="nextPage" value="${nextPage}"/>
        </jsp:include>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
        const tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
            return new bootstrap.Tooltip(tooltipTriggerEl)
        })
    })
    document.addEventListener('DOMContentLoaded', function () {
        var clickableRows = document.querySelectorAll('.table-row-clickable');
        clickableRows.forEach(function (row) {
            row.addEventListener('click', function () {
                window.location.href = row.getAttribute('data-href');
            });
        });
    });

</script>