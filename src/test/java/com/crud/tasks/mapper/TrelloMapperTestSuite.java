package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTestSuite {

    @Autowired
    TrelloMapper trelloMapper;

    @Test
    public void testMapToBoards(){
        //Given
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("1","testList1",true));
        trelloListDto.add(new TrelloListDto("2","testList2",false));
        trelloListDto.add(new TrelloListDto("3","testList3",true));

        List<TrelloBoardDto> trelloBoardDto = new ArrayList<>();
        trelloBoardDto.add(new TrelloBoardDto("1","Board1",trelloListDto));
        trelloBoardDto.add(new TrelloBoardDto("2","Board2",trelloListDto));
        trelloBoardDto.add(new TrelloBoardDto("3","Board3",trelloListDto));

        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDto);

        //Then
        assertEquals(3,trelloBoards.size());
        assertEquals("Board2",trelloBoards.get(1).getName());
        assertEquals("testList1",trelloBoards.get(0).getLists().get(0).getName());
    }

    @Test
    public void testMapToBoardsDto(){
        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("1","List1",true));
        trelloLists.add(new TrelloList("2","List2",false));
        trelloLists.add(new TrelloList("3","List3",true));

        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("1","Board1",trelloLists));
        trelloBoards.add(new TrelloBoard("2","Board2",trelloLists));
        trelloBoards.add(new TrelloBoard("3","Board3",trelloLists));

        //When
        List<TrelloBoardDto> trelloBoardDto = trelloMapper.mapToBoardsDto(trelloBoards);

        //Then
        assertEquals(3,trelloBoardDto.size());
        assertEquals("Board1",trelloBoardDto.get(0).getName());
        assertEquals("List3",trelloBoardDto.get(0).getList().get(2).getName());
    }

    @Test
    public void testMapToList(){
        //Given
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("1","testList1",true));
        trelloListDto.add(new TrelloListDto("2","testList2",false));
        trelloListDto.add(new TrelloListDto("3","testList3",true));

        //When
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDto);

        //Then
        assertEquals(3,trelloLists.size());
        assertEquals("1",trelloLists.get(0).getId());
    }

    @Test
    public void testMapToListDto(){
        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("1","List1",true));
        trelloLists.add(new TrelloList("2","List2",false));
        trelloLists.add(new TrelloList("3","List3",true));

        //When
        List<TrelloListDto> trelloListDto = trelloMapper.mapToListDto(trelloLists);

        //Then
        assertEquals(3,trelloListDto.size());
        assertEquals("List1",trelloListDto.get(0).getName());
    }

    @Test
    public void testMapToCard(){
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("TrelloCard","Description1","Center","1");

        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertEquals("TrelloCard",trelloCard.getName());
        assertEquals("Description1",trelloCard.getDescription());
    }

    @Test
    public void testMapToCardDto(){
        //Given
        TrelloCard trelloCard = new TrelloCard("TrelloCard","Description","Center","1");

        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertEquals("TrelloCard",trelloCardDto.getName());
        assertEquals("Center",trelloCardDto.getPos());

    }
}
