package com.rev_connect_api.repositories;


import com.rev_connect_api.models.Lists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ListRepository extends JpaRepository<Lists, Long> {
    List<Lists> findByUserUserId(Long userId);
}
