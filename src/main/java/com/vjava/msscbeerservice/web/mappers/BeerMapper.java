package com.vjava.msscbeerservice.web.mappers;

import com.vjava.msscbeerservice.domain.Beer;
import com.vjava.msscbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    public BeerDto beerToBeerDto(Beer beer);

    public Beer beerDtoToBeer(BeerDto beerDto);

}
