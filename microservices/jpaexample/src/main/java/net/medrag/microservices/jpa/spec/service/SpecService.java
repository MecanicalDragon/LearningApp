package net.medrag.microservices.jpa.spec.service;

import net.medrag.microservices.jpa.spec.dto.EntityDoesntExistException;
import net.medrag.microservices.jpa.spec.dto.SpecDto;
import net.medrag.microservices.jpa.spec.dto.SpecSearchParams;
import net.medrag.microservices.jpa.spec.entity.SpecEntity;
import net.medrag.microservices.jpa.spec.repositry.SpecEntitySpecification;
import net.medrag.microservices.jpa.spec.repositry.SpecRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SpecService {

    private final SpecRepository specRepository;
    private final ModelMapper modelMapper;

    public SpecService(SpecRepository specRepository, ModelMapper modelMapper) {
        this.specRepository = specRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<SpecDto> extract(Long id) {
        final Optional<SpecEntity> byId = specRepository.findById(id);
        return byId.map(it -> modelMapper.map(it, SpecDto.class));
    }

    public SpecDto addOne(SpecDto specDto) {
        final SpecEntity mapped = modelMapper.map(specDto, SpecEntity.class);
        mapped.setId(0L);
        final SpecEntity saved = specRepository.save(mapped);
        return modelMapper.map(saved, SpecDto.class);
    }

    public Page<SpecDto> getPage(SpecSearchParams specSearchParams, Pageable pageable) {
        if (isSearchParamsEmpty(specSearchParams)) {
            return specRepository.findAll(pageable).map(it -> modelMapper.map(it, SpecDto.class));
        } else {
            final SpecEntitySpecification specEntitySpecification = new SpecEntitySpecification(specSearchParams);
            return specRepository.findAll(specEntitySpecification, pageable).map(it -> modelMapper.map(it, SpecDto.class));
        }
    }

    private boolean isSearchParamsEmpty(SpecSearchParams params) {
        return params.getId().isEmpty() && params.getPhoneNumber().isEmpty() && params.getDate().isEmpty()
                && params.getFirstName().isEmpty() && params.getLastName().isEmpty();
    }

    public void deleteById(Long id) {
        final int i = specRepository.deleteEntryById(id);
        if (i != 1) throw new EntityDoesntExistException(id);
    }
}
