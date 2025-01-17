package com.devonfw.demoquarkus.logic;

import com.devonfw.demoquarkus.domain.model.AnimalEntity;
import com.devonfw.demoquarkus.domain.repo.AnimalRepository;
import com.devonfw.demoquarkus.service.v1.mapper.AnimalMapper;
import com.devonfw.demoquarkus.service.v1.model.AnimalDto;
import com.devonfw.demoquarkus.service.v1.model.AnimalSearchCriteriaDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Named
@Transactional
@Slf4j
public class UcFindAnimalImpl implements UcFindAnimal {
    @Inject
    AnimalRepository animalRepository;

    @Inject
    AnimalMapper mapper;

    @Override
    public Page<AnimalDto> findAnimals(AnimalSearchCriteriaDto dto) {
        Iterable<AnimalEntity> animalsIterator = this.animalRepository.findAll();
        List<AnimalEntity> animals = new ArrayList<AnimalEntity>();
        animalsIterator.forEach(animals::add);
        List<AnimalDto> animalsDto = this.mapper.map(animals);
        return new PageImpl<>(animalsDto, PageRequest.of(dto.getPageNumber(), dto.getPageSize()), animalsDto.size());
    }

    @Override
    public Page<AnimalDto> findAnimalsByCriteriaApi(AnimalSearchCriteriaDto dto) {
        List<AnimalEntity> animals = this.animalRepository.findAllCriteriaApi(dto).getContent();
        List<AnimalDto> animalsDto = this.mapper.map(animals);
        return new PageImpl<>(animalsDto, PageRequest.of(dto.getPageNumber(), dto.getPageSize()), animalsDto.size());
    }

    @Override
    public Page<AnimalDto> findAnimalsByQueryDsl(AnimalSearchCriteriaDto dto) {
        List<AnimalEntity> animals = this.animalRepository.findAllQueryDsl(dto).getContent();
        List<AnimalDto> animalsDto = this.mapper.map(animals);
        return new PageImpl<>(animalsDto, PageRequest.of(dto.getPageNumber(), dto.getPageSize()), animalsDto.size());
    }

    @Override
    public Page<AnimalDto> findAnimalsByNameQuery(AnimalSearchCriteriaDto dto) {
        List<AnimalEntity> animals = this.animalRepository.findByNameQuery(dto).getContent();
        List<AnimalDto> animalsDto = this.mapper.map(animals);
        return new PageImpl<>(animalsDto, PageRequest.of(dto.getPageNumber(), dto.getPageSize()), animalsDto.size());
    }

    @Override
    public Page<AnimalDto> findAnimalsByNameNativeQuery(AnimalSearchCriteriaDto dto) {
        List<AnimalEntity> animals = this.animalRepository.findByNameNativeQuery(dto).getContent();
        List<AnimalDto> animalsDto = this.mapper.map(animals);
        return new PageImpl<>(animalsDto, PageRequest.of(dto.getPageNumber(), dto.getPageSize()), animalsDto.size());
    }

    @Override
    public Page<AnimalDto> findAnimalsOrderedByName() {
        List<AnimalEntity> animals = this.animalRepository.findAllByOrderByName().getContent();
        List<AnimalDto> animalsDto = this.mapper.map(animals);
        return new PageImpl<>(animalsDto);
    }

    @Override
    public AnimalDto findAnimal(String id) {
        AnimalEntity animal = this.animalRepository.findById(Long.valueOf(id)).get();
        if (animal != null) {
            return this.mapper.map(animal);
        } else {
            return null;
        }
    }

    @Override
    public AnimalDto findAnimalByName(String name) {
        AnimalEntity animal = this.animalRepository.findByName(name);
        if (animal != null) {
            return this.mapper.map(animal);
        } else {
            return null;
        }
    }

}
