package com.VM.MockProject.Service;

import com.VM.MockProject.Repository.IPublisherRepository;
import com.VM.MockProject.Service.Interface.IPublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherService implements IPublisherService {
    @Autowired
    IPublisherRepository publisherRepository;

    @Override
    public boolean isPublisherExistsByID(Integer id) {
        return publisherRepository.existsById(id);
    }
}
