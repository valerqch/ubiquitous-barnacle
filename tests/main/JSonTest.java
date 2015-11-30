package main;

import static org.mockito.Mockito.*;
import org.junit.Test;
import org.mockito.Mockito;
import java.net.URL;
import java.util.Optional;

public class JSonTest {

    @Test
    public void MainTest(){
        try{
            JSon mockedJson = Mockito.spy(JSon.class);


            mockedJson.getData();
            URL commentsDataUrl = new URL("http://jsonplaceholder.typicode.com/comments");
            URL todoDataUrl = new URL("http://jsonplaceholder.typicode.com/todos");
            verify(mockedJson).openStreamFromUrl(commentsDataUrl);
            verify(mockedJson).openStreamFromUrl(todoDataUrl);
            verify(mockedJson).readDataFromUrl(commentsDataUrl);
            verify(mockedJson).readDataFromUrl(todoDataUrl);

            verify(mockedJson).getCommentsArraySize();
            verify(mockedJson).getTodosArraySize();

            when(mockedJson.getCommentsArraySize()).thenReturn(500);
            when(mockedJson.getTodosArraySize()).thenReturn(300);

            when(mockedJson.calculateData()).thenReturn(Optional.of(200));

        }catch(Exception e){
            e.printStackTrace();
        }
    }



}