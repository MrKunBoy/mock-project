package com.VM.MockProject.Repository;

import com.VM.MockProject.Entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPublisherRepository extends JpaRepository<Publisher, Integer> {
}
