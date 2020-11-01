package com.vjava.msscbeerservice.bootstrap;

import com.vjava.msscbeerservice.domain.Beer;
import com.vjava.msscbeerservice.repositories.BeerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class BeerLoader implements CommandLineRunner {
    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeerObjects();
    }

    private void loadBeerObjects() {
        if (beerRepository.count() == 0) {
            beerRepository.save(Beer.builder().beerName("Mango Bobs")
                    .beerStyle("IPA")
                    .quantityToBrew(200)
                    .quantityOnHand(12)
                    .upc(3368686868868L)
                    .price(new BigDecimal(4.95))
                    .build());

            beerRepository.save(Beer.builder().beerName("Galaxy Cat")
                    .beerStyle("Pale Ale")
                    .quantityToBrew(200)
                    .quantityOnHand(12)
                    .upc(3368686869968L)
                    .price(new BigDecimal(3.95))
                    .build());
        }

        //log.info("Beers Loaded: "+beerRepository.count());
    }
}
