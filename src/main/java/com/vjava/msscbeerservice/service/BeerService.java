package com.vjava.msscbeerservice.service;

import com.vjava.msscbeerservice.model.BeerDto;

import java.util.UUID;

public interface BeerService {
    BeerDto getBeerById(UUID beerId);

    BeerDto updateBeerById(UUID beerId, BeerDto beerDto);

    BeerDto saveNewBeer(BeerDto beerDto);
}
