package com.payaman.auctms.kafka;

import com.payaman.auctms.entity.CategoryData;
import com.payaman.auctms.model.Category;
import com.payaman.auctms.repository.CategoryDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CategoryListener {
    Logger logger = LoggerFactory.getLogger("CategoryListener");

    @Autowired
    CategoryDataRepository categoryDataRepository;


    @KafkaListener(topics = "Category", groupId = "Payaman")
    public void consumeCateogory(Category category) {
        CategoryData categoryData = new CategoryData();
        categoryData.setId(category.getId());
        categoryData.setName(category.getName());

        categoryDataRepository.save(categoryData);
        logger.info("Created category from kafka with id: " + category.getId());
    }
}
