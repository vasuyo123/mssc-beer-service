package com.vjava.msscbeerservice.mappers;

import com.vjava.msscbeerservice.domain.Beer;
import com.vjava.msscbeerservice.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    public BeerDto beerToBeerDto(Beer beer);

    public Beer beerDtoToBeer(BeerDto beerDto);

}
