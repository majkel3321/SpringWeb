package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TrelloClient {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloAppToken;

    @Value("${trello.app.username}")
    private String trelloAppUsername;

    @Autowired
    private RestTemplate restTemplate;

    private URI generateUrL(){
        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + trelloAppUsername)
                .queryParam("key",trelloAppKey)
                .queryParam("token",trelloAppToken)
                .queryParam("fields","name,id")
                .queryParam("lists","all").build().encode().toUri();
        return url;
    }

    public List<TrelloBoardDto> getTrelloBoards(){

        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(generateUrL(),TrelloBoardDto[].class);

        if (boardsResponse != null){
            return Arrays.asList(boardsResponse);
        }

        return new ArrayList<>();

    }
}
