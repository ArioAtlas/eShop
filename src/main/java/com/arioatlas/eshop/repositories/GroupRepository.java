package com.arioatlas.eshop.repositories;


import com.arioatlas.eshop.models.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("groupRepository")
public interface GroupRepository extends JpaRepository<ProductGroup, Long> { }