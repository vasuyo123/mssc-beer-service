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

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234200019";
    public static final String BEER_3_UPC = "0631234200028";

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
                    .upc(BEER_1_UPC)
                    .price(new BigDecimal(4.95))
                    .build());

            beerRepository.save(Beer.builder().beerName("Galaxy Cat")
                    .beerStyle("Pale Ale")
                    .quantityToBrew(200)
                    .quantityOnHand(12)
                    .upc(BEER_2_UPC)
                    .price(new BigDecimal(3.95))
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("Super Charger")
                    .beerStyle("Truly Strong")
                    .quantityToBrew(200)
                    .quantityOnHand(12)
                    .upc(BEER_3_UPC)
                    .price(new BigDecimal(2.95))
                    .build());
        }

        //log.info("Beers Loaded: "+beerRepository.count());
    }
}
