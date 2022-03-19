package net.medrag.microservices.jpa.spec.config;

import net.medrag.microservices.jpa.spec.dto.SpecDto;
import net.medrag.microservices.jpa.spec.entity.SpecEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * logging
 * @NotNull
 * metrics
 * customInfo
 * actuator
 * checkstyle
 * jacoco
 * javadoc
 * openapi
 */
@Configuration
public class SpecConfiguration {

    @Bean
    public Converter<SpecEntity, SpecDto> mMapperConverter() {
        return new Converter<SpecEntity, SpecDto>() {
            @Override
            public SpecDto convert(MappingContext<SpecEntity, SpecDto> mappingContext) {
                final SpecEntity source = mappingContext.getSource();
                return new SpecDto(
                        source.getId(),
                        source.getBirthDate(),
                        source.getFirstName(),
                        source.getLastName(),
                        source.getPhoneNumber()
                );
            }
        };
    }

    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper modelMapper = new ModelMapper();
        TypeMap<SpecDto, SpecEntity> propertyMapper = modelMapper.createTypeMap(SpecDto.class, SpecEntity.class);
        propertyMapper.addMapping(SpecDto::getDate, SpecEntity::setBirthDate);
        modelMapper.addConverter(mMapperConverter());
        return modelMapper;
    }
}
