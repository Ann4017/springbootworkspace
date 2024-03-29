package org.zerock.mreview.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.repository.search.SearchRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long>, SearchRepository {

//    @Query("select m,avg(coalesce(r.grade,0)),count(distinct r) " +
//            "from Movie m left join Review r " +
//            "on r.movie = m group by m")
//    Page<Object[]> getListPage(Pageable pageable);

    @Query("select m,mi,avg(coalesce(r.grade,0)),count(distinct r) " +
            "from Movie m " +
            "left join MovieImage mi " +
            "on mi.movie = m " +
            "left join Review r " +
            "on r.movie = m group by m")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select m,mi,avg(coalesce(r.grade,0)),count(distinct r) " +
            "from Movie m " +
            "left join MovieImage mi " +
            "on mi.movie = m " +
            "left join Review r " +
            "on r.movie = m " +
            "where m.title = :title " +
            "group by m")
    Page<Object[]> getSearchPage(Pageable pageable, @Param("title") String title);

    @Query("select m,mi,avg(coalesce(r.grade,0)),count(r) " +
            "from Movie m " +
            "left join MovieImage mi " +
            "on mi.movie = m " +
            "left join Review r " +
            "on r.movie = m " +
            "where m.mno = :mno group by mi"
          )
    List<Object[]> getMovieWithAll(Long mno);

}
