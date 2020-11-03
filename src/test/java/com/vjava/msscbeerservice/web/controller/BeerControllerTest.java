package com.vjava.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vjava.msscbeerservice.controller.BeerController;
import com.vjava.msscbeerservice.model.BeerDto;
import com.vjava.msscbeerservice.model.BeerStyleEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dev.springframework.guru", uriPort = 80)
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getBeerById() {
        try {
            ResultActions resultActions = mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
                    .param("isCold", "yes")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(document("v1/beer-get",
                            pathParameters(
                                    parameterWithName("beerId").description("UUID of desired beer to get.")
                            ),
                            requestParameters(
                                    parameterWithName("isCold").description("Is Beer Cold Query Parameter")
                            ),
                            responseFields(
                                    fieldWithPath("id").description("Id of Beer"),
                                    fieldWithPath("version").description("Version number"),
                                    fieldWithPath("createdDate").description("Date Created"),
                                    fieldWithPath("lastModifiedDate").description("Date Modified"),
                                    fieldWithPath("beerName").description("Name of the Beer"),
                                    fieldWithPath("beerStyle").description("Stle of the Beer"),
                                    fieldWithPath("upc").description("UPC of Beer"),
                                    fieldWithPath("price").description("Price of Beer"),
                                    fieldWithPath("quantityOnHand").description("Quantity on Hand")
                            )));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    void saveNewBeer() throws Exception {
        String beerDtoJson = objectMapper.writeValueAsString(getValidBeerDto());

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        mockMvc.perform(post("/api/v1/beer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated())
                .andDo(document("v1/beer-post/",
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("version").ignored(),
                                fields.withPath("createdDate").ignored(),
                                fields.withPath("lastModifiedDate").ignored(),
                                fields.withPath("beerName").description("Name of the Beer"),
                                fields.withPath("beerStyle").description("Stle of the Beer"),
                                fields.withPath("upc").description("UPC of Beer"),
                                fields.withPath("price").description("Price of Beer"),
                                fields.withPath("quantityOnHand").description("Quantity on Hand")
                        )));
    }

    @Test
    void updateBeerById() throws Exception {
        String beerDtoJson = objectMapper.writeValueAsString(getValidBeerDto());

        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());
    }

    BeerDto getValidBeerDto() {
        return BeerDto.builder()
                .beerName("My Beer")
                .beerStyle(BeerStyleEnum.ALE)
                .price(new BigDecimal("2.99"))
                .upc(1292929299299L)
                .quantityOnHand(20)
                .build();
    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }
}