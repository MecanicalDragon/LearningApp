package net.medrag.microservices.jpa.spec;

import net.medrag.microservices.jpa.spec.dto.SpecUserDto;
import net.medrag.microservices.jpa.spec.entity.SpecUser;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpecConfiguration {

    @Bean
    public Converter<SpecUser, SpecUserDto> mMapperConverter() {
        return new Converter<SpecUser, SpecUserDto>() {
            @Override
            public SpecUserDto convert(MappingContext<SpecUser, SpecUserDto> mappingContext) {
                final SpecUser source = mappingContext.getSource();
                return new SpecUserDto(
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
        TypeMap<SpecUserDto, SpecUser> propertyMapper = modelMapper.createTypeMap(SpecUserDto.class, SpecUser.class);
        propertyMapper.addMapping(SpecUserDto::getDate, SpecUser::setBirthDate);
        modelMapper.addConverter(mMapperConverter());
        return modelMapper;
    }
}
