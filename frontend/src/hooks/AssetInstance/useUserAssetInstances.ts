// useAssetInstances.js
import { useState } from 'react';

const useUserAssetInstances = (initialSort = { column: 'title', order: 'asc' }) => {
    const [filter, setFilter] = useState('all');
    const [sort, setSort] = useState(initialSort);
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage, setItemsPerPage] = useState(10); // Or whatever default you prefer
    const [totalPages, setTotalPages] = useState(20); // To be set when data is fetched
    const [books, setBooks] = useState([])

    const applyFilterAndSort = (newPage: number, newSort: any, newFilter: string, books: any) => {
        console.log(newSort, newFilter, newPage)
    };

    // Function to change the page
    const changePage = (newPage: number) => {
        setCurrentPage(newPage);
        // Re-apply filter and sort whenever page changes
        applyFilterAndSort(newPage, sort, filter, books);
    };


    return { setFilter, filter, applyFilterAndSort, sort, setSort, currentPage, changePage, totalPages, books, setBooks};
};

export default useUserAssetInstances;
