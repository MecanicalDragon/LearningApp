package net.medrag.microservices.jpa.spec.service;

import net.medrag.microservices.jpa.spec.dto.SpecUserDoesntExistException;
import net.medrag.microservices.jpa.spec.dto.SpecUserDto;
import net.medrag.microservices.jpa.spec.dto.SpecUserSearchParams;
import net.medrag.microservices.jpa.spec.entity.SpecUser;
import net.medrag.microservices.jpa.spec.repositry.SpecUserRepository;
import net.medrag.microservices.jpa.spec.repositry.SpecUserSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SpecUserService {

    private final SpecUserRepository specUserRepository;
    private final ModelMapper modelMapper;

    public SpecUserService(SpecUserRepository specUserRepository, ModelMapper modelMapper) {
        this.specUserRepository = specUserRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<SpecUserDto> extract(Long id) {
        final Optional<SpecUser> byId = specUserRepository.findById(id);
        return byId.map(it -> modelMapper.map(it, SpecUserDto.class));
    }

    public SpecUserDto addOne(SpecUserDto specUserDto) {
        final SpecUser mapped = modelMapper.map(specUserDto, SpecUser.class);
        mapped.setId(0L);
        final SpecUser saved = specUserRepository.save(mapped);
        return modelMapper.map(saved, SpecUserDto.class);
    }

    public Page<SpecUserDto> getPage(SpecUserSearchParams specUserSearchParams, Pageable pageable) {
        if (isSearchParamsEmpty(specUserSearchParams)) {
            return specUserRepository.findAll(pageable).map(it -> modelMapper.map(it, SpecUserDto.class));
        } else {
            final SpecUserSpecification specUserSpecification = new SpecUserSpecification(specUserSearchParams);
            return specUserRepository.findAll(specUserSpecification, pageable).map(it -> modelMapper.map(it, SpecUserDto.class));
        }
    }

    private boolean isSearchParamsEmpty(SpecUserSearchParams params) {
        return params.getId().isEmpty() && params.getPhoneNumber().isEmpty() && params.getDate().isEmpty()
                && params.getFirstName().isEmpty() && params.getLastName().isEmpty();
    }

    public void deleteById(Long id) {
        final int i = specUserRepository.deleteEntryById(id);
        if (i != 1) throw new SpecUserDoesntExistException(id);
    }
}
