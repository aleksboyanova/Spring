package com.uni.project.repository;

import com.uni.project.entities.Listing;
import org.springframework.data.repository.CrudRepository;

public interface ListingRepository extends CrudRepository<Listing, Integer> {
}
